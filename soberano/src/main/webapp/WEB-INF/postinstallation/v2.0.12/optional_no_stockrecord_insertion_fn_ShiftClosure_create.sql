CREATE OR REPLACE FUNCTION soberano."fn_ShiftClosure_create"(
	lang character,
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE
	itemId integer;
	previousClosureTime timestamp with time zone;
	previousClosureDay date;
	closureHour smallint;
	closureMinutes smallint;
	entityTypeInstanceId integer;
	currentTime timestamp with time zone;
	currentDay date;
	dayToClose date;
	nextDay date;
	closureTime timestamp with time zone;
	dayMayBeClosed boolean;
	
	previousPreviousClosureTime timestamp with time zone;
	
	lastClosureForThatDay integer;
BEGIN
	itemId := -1;
	currentTime := NOW();
	currentDay := make_date(CAST(date_part('year', currentTime) AS integer),
						CAST(date_part('month', currentTime) AS integer),
						CAST(date_part('day', currentTime) AS integer));
	dayMayBeClosed := false;

	--the user has rights to record a closure,
	IF (soberano."fn_ShiftClosure_closureAllowed"(loginname)) THEN
	
		--date and time of the the last not canceled closure
		SELECT "This_has_ClosureTime", 
			"This_is_of_Shift" 
			FROM soberano."ShiftClosure" cierre 
				INNER JOIN "metamodel"."EntityTypeInstance" instancia
					ON cierre."This_is_identified_by_EntityTypeInstance_id" = instancia."EntityTypeInstanceHasEntityTypeInstanceId"

			--not canceled condition
			WHERE "This_is_in_Stage_with_StageHasStageId" != 5
			INTO previousClosureTime, previousClosureDay
			ORDER BY "ShiftClosureHasShiftClosureId" DESC LIMIT 1;
			
		SELECT "This_has_ShiftClosureHours", "This_has_ShiftClosureMinutes"	FROM soberano."Configuration"
			INTO closureHour, closureMinutes;
	
		--no recorded not canceled closure exists
		IF previousClosureDay IS NULL THEN

			previousClosureDay := currentDay - make_interval(days := 2);

			previousClosureTime := make_timestamp(CAST(date_part('year', currentTime) AS integer), 
							CAST(date_part('month', currentTime) AS integer), 
							CAST(date_part('day', currentTime) AS integer), 
							closureHour, 
							closureMinutes, 
							0)  - make_interval(days := 1);
							
			--create entity type instance	
			SELECT metamodel."fn_EntityTypeInstance_create"('ShiftClosure', loginname)
				INTO entityTypeInstanceId;
				
			INSERT INTO soberano."ShiftClosure"("This_is_of_Shift", 
												"This_is_identified_by_EntityTypeInstance_id", 
												"Report_is_of_This", 
												"This_has_ClosureTime")
				VALUES (previousClosureDay,
						entityTypeInstanceId,
						'',
					   previousClosureTime) RETURNING "ShiftClosureHasShiftClosureId" INTO itemId;
		END IF;
	
		--current day already closed
		IF currentDay = previousClosureDay THEN

			--no closure possible. it cannot be closed tomorrow day before 00:00
			dayMayBeClosed := false;

		ELSE
			--it can be closed the day of today, or a previous day
			dayMayBeClosed := true;

			--the day of yesterday has been already closed
			IF currentDay - make_interval(days := 1) = previousClosureDay THEN

				--only to close the day of today is possible. 
				dayToClose := currentDay;
				closureTime := currentTime;

			ELSE
				--it must be closed the day of yesterday
				dayToClose := currentDay - make_interval(days := 1);

				--closure time already passed
				IF make_timestamp(CAST(date_part('year', currentTime) AS integer), 
							CAST(date_part('month', currentTime) AS integer), 
							CAST(date_part('day', currentTime) AS integer), 
							closureHour, 
							closureMinutes, 
							0) <= currentTime THEN

					closureTime := make_timestamp(CAST(date_part('year', currentTime) AS integer), 
							CAST(date_part('month', currentTime) AS integer), 
							CAST(date_part('day', currentTime) AS integer), 
							closureHour, 
							closureMinutes, 
							0);
				ELSE				
					closureTime := currentTime;
				END IF;			
			END IF;
		END IF;

		IF dayMayBeClosed THEN

			nextDay := previousClosureDay + make_interval(days := 1);

			LOOP
				EXIT WHEN dayToClose = nextDay;
				previousPreviousClosureTime := make_timestamp(CAST(date_part('year', nextDay) AS integer), 
																CAST(date_part('month', nextDay) AS integer), 
																CAST(date_part('day', nextDay) AS integer), 
																closureHour, 
																closureMinutes, 
																0);
				previousClosureTime := previousPreviousClosureTime + make_interval(days := 1);
								
				--se registran los cierres intermedios
				SELECT metamodel."fn_EntityTypeInstance_create"('ShiftClosure', loginname)
					INTO entityTypeInstanceId;
					
				--se cierran las corridas de proceso (actualiza el inventario) de los pedidos 
				--cerrados durante el turno, que aún tienen corridas de proceso en curso.
				PERFORM soberano."fn_Order_closeProcessRuns"(oid, 'soberano.user.top')
					FROM (SELECT DISTINCT ord."OrderHasOrderId" oid
								FROM soberano."Order" ord
									INNER JOIN metamodel."EntityTypeInstance" eti
										ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = ord."This_is_identified_by_EntityTypeInstance_id"

											--only closed orders
											AND eti."This_is_in_Stage_with_StageHasStageId" = 6
									INNER JOIN metamodel."Vote" orderCollectingVotet
										ON ord."This_is_identified_by_EntityTypeInstance_id" = orderCollectingVotet."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"

											--orders closed (collected) within the passed time interval
											AND orderCollectingVotet."This_favors_Decision_with_DecisionHasDecisionId" = 3005
											AND orderCollectingVotet."This_is_on_Timestamp" > previousPreviousClosureTime 
											AND orderCollectingVotet."This_is_on_Timestamp" <= previousClosureTime

									--only orders with ongoing process runs.
									INNER JOIN soberano."OrderProcessRun" opr
										ON ord."OrderHasOrderId" = opr."OrderHasOrderId"
									INNER JOIN soberano."ProcessRun" pr
										ON pr."ProcessRunHasProcessRunId" = opr."ProcessRunHasProcessRunId"
									INNER JOIN metamodel."EntityTypeInstance" preti
										ON preti."EntityTypeInstanceHasEntityTypeInstanceId" = pr."This_is_identified_by_EntityTypeInstance_id"

											--only ongoing process runs
											AND preti."This_is_in_Stage_with_StageHasStageId" = 3) sq;
				
				--registrar balances de caja para cierre intermedio.
				--se toman balances del último cierre registrado para ese día
				--tomar id del cierre antes de registrar cierre intermedio.
				--si no, se termina con los balances de caja actuales.
				SELECT "ShiftClosureHasShiftClosureId" 
					FROM soberano."ShiftClosure"
					WHERE "This_is_of_Shift" = nextDay
					ORDER BY "ShiftClosureHasShiftClosureId" DESC LIMIT 1
					INTO lastClosureForThatDay;
				
				INSERT INTO soberano."ShiftClosure"("This_is_of_Shift", 
													"This_is_identified_by_EntityTypeInstance_id", 
													"Report_is_of_This", 
													"This_has_ClosureTime")
					VALUES (nextDay,
							entityTypeInstanceId,
							'',
						   previousClosureTime) RETURNING "ShiftClosureHasShiftClosureId" INTO itemId;
						   
				--los balances en 0 si nunca se registró un cierre para ese día
				IF lastClosureForThatDay IS NULL THEN
					INSERT INTO soberano."ShiftClosureRecordsBalanceInCurrency"(
							"ShiftClosureHasShiftClosureId", "Balance", "CurrencyHasCurrencyId")
						SELECT itemId, CAST(0.0 AS numeric), "CurrencyHasCurrencyId"
							FROM soberano."Currency"; 
				ELSE
					INSERT INTO soberano."ShiftClosureRecordsBalanceInCurrency"(
							"ShiftClosureHasShiftClosureId", 
							"Balance", 
							"CurrencyHasCurrencyId")
						SELECT itemId, 
								"Balance", 
								"CurrencyHasCurrencyId"
							FROM soberano."ShiftClosureRecordsBalanceInCurrency"
							WHERE "ShiftClosureHasShiftClosureId" = lastClosureForThatDay;
				END IF;
				
				----------
/* CUSTOMIZATION: No insertion of stock records upon closure. soberano."StockRecord"
	relation is not used anywhere anyway. Even with a small inventory, it can become
	the largest table of the database. It aims fully traceability of inventory for
	advanced reports.
	
				INSERT INTO soberano."StockRecord"("Quantity", 
											   "This_has_Value", 
											   "WarehouseHasWarehouseId", 
											   "InventoryItemHasInventoryItemCode", 
											   "ShiftClosureHasShiftClosureId")
				SELECT "Quantity", 
						"This_has_Value", 
						"WarehouseHasWarehouseId", 
						"InventoryItemHasInventoryItemCode",
						itemId
					FROM soberano."Stock";
*/
				--------------------------------------
								
				nextDay := nextDay + make_interval(days := 1);
			END LOOP;
			
			--se registra el cierre
			SELECT metamodel."fn_EntityTypeInstance_create"('ShiftClosure', loginname)
				INTO entityTypeInstanceId;
				
			--se toma el id del último cierre (cancelado) para el día a cerrar (ayer u hoy)
			lastClosureForThatDay := NULL;
			
			SELECT "ShiftClosureHasShiftClosureId" 
				FROM soberano."ShiftClosure"
				WHERE "This_is_of_Shift" = dayToClose
				ORDER BY "ShiftClosureHasShiftClosureId" DESC LIMIT 1
				INTO lastClosureForThatDay;
				
			INSERT INTO soberano."ShiftClosure"("This_is_of_Shift", 
												"This_is_identified_by_EntityTypeInstance_id", 
												"Report_is_of_This", 
												"This_has_ClosureTime")
				VALUES (dayToClose,
						entityTypeInstanceId,
						'',
					   closureTime) RETURNING "ShiftClosureHasShiftClosureId" INTO itemId;
					   
			--se cierran las corridas de proceso (actualiza el inventario) de los pedidos 
			--cerrados durante el turno, que aún tienen corridas de proceso en curso.
			PERFORM soberano."fn_Order_closeProcessRuns"(oid, 'soberano.user.top')
				FROM (SELECT DISTINCT ord."OrderHasOrderId" oid
							FROM soberano."Order" ord
								INNER JOIN metamodel."EntityTypeInstance" eti
									ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = ord."This_is_identified_by_EntityTypeInstance_id"

										--only closed orders
										AND eti."This_is_in_Stage_with_StageHasStageId" = 6
								INNER JOIN metamodel."Vote" orderCollectingVotet
									ON ord."This_is_identified_by_EntityTypeInstance_id" = orderCollectingVotet."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"

										--orders closed (collected) within the passed time interval
										AND orderCollectingVotet."This_favors_Decision_with_DecisionHasDecisionId" = 3005
										AND orderCollectingVotet."This_is_on_Timestamp" > previousClosureTime 
										AND orderCollectingVotet."This_is_on_Timestamp" <= closureTime

								--only orders with ongoing process runs.
								INNER JOIN soberano."OrderProcessRun" opr
									ON ord."OrderHasOrderId" = opr."OrderHasOrderId"
								INNER JOIN soberano."ProcessRun" pr
									ON pr."ProcessRunHasProcessRunId" = opr."ProcessRunHasProcessRunId"
								INNER JOIN metamodel."EntityTypeInstance" preti
									ON preti."EntityTypeInstanceHasEntityTypeInstanceId" = pr."This_is_identified_by_EntityTypeInstance_id"

										--only ongoing process runs
										AND preti."This_is_in_Stage_with_StageHasStageId" = 3) sq;

/* CUSTOMIZATION: No insertion of stock records upon closure. soberano."StockRecord"
	relation is not used anywhere anyway. Even with a small inventory, it can become
	the largest table of the database. It aims fully traceability of inventory for
	advanced reports.

			INSERT INTO soberano."StockRecord"("Quantity", 
											   "This_has_Value", 
											   "WarehouseHasWarehouseId", 
											   "InventoryItemHasInventoryItemCode", 
											   "ShiftClosureHasShiftClosureId")
				SELECT "Quantity", 
						"This_has_Value", 
						"WarehouseHasWarehouseId", 
						"InventoryItemHasInventoryItemCode",
						itemId
					FROM soberano."Stock";
*/
					
			--se toman los balances actuales para turno que toca,
			--aún cuando un cierre de ese turno se haya cancelado.
			--o para turno pasado, solo en el caso de que nunca se haya cerrado.
			--closureTime = currentTime es true solo para el turno que toca cerrar.
			--por ejemplo, ayer, toma la hora de cierre predeterminada y no la actual.
			IF closureTime = currentTime OR lastClosureForThatDay IS NULL THEN
			
				--se registran los balances en caja
				INSERT INTO soberano."ShiftClosureRecordsBalanceInCurrency"(
					"ShiftClosureHasShiftClosureId", "Balance", "CurrencyHasCurrencyId")
					SELECT itemId, "Balance", "CurrencyHasCurrencyId"
						FROM soberano."CashRegisterHasBalanceInCurrency"
						WHERE "CashRegisterHasCashRegisterId" = 1; 
			ELSE
				--se toman los mismos balances de caja de cuando se cerró el día de ayer
				INSERT INTO soberano."ShiftClosureRecordsBalanceInCurrency"(
							"ShiftClosureHasShiftClosureId", 
							"Balance", 
							"CurrencyHasCurrencyId")
						SELECT itemId, 
								"Balance", 
								"CurrencyHasCurrencyId"
							FROM soberano."ShiftClosureRecordsBalanceInCurrency"
							WHERE "ShiftClosureHasShiftClosureId" = lastClosureForThatDay;
			END IF;
			
			--se eliminan todas las operaciones de inventario solicitadas 
			--desde un formulario de IPV, que no llegaron a confirmarse
			DELETE FROM metamodel."EntityTypeInstance" eti
				USING soberano."InventoryOperation" invop
				WHERE eti."EntityTypeInstanceHasEntityTypeInstanceId" = invop."This_is_identified_by_EntityTypeInstance_id"
					AND eti."This_is_in_Stage_with_StageHasStageId" = 10;
		ELSE
			--todavía no es posible registrar un cierre contable o el día ya cerró
			itemId := -2;
		END IF;
	END IF;
	RETURN itemId;
END;
$BODY$;