CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport2_dailyPayments"(
	lang character,
	fromd date,
	untild date,
	loginname character varying)
    RETURNS TABLE(orderdate date, curr character varying, amount numeric) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 100

AS $BODY$
DECLARE
	pTime timestamp with time zone;
	cTime timestamp with time zone;
	fromClosureId integer;
	untilClosureId integer;
BEGIN
	--the user has rights to record a closure,
	IF ('Manager' IN (SELECT * from metamodel."fn_User_getResponsibilities"(loginname)))
	
		--or make decisions on an existing closure
		OR EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(20, 1, loginname)) THEN

			SELECT soberano."fn_ShiftClosure_getIdFromDate"(to_char(COALESCE(fromd, now()::date), 'YYYY-MM-DD')) 
				INTO fromClosureId;
	
			SELECT soberano."fn_ShiftClosure_getIdFromDate"(to_char(COALESCE(untild, now()::date), 'YYYY-MM-DD')) 
				INTO untilClosureId;
	
			SELECT previousclosuretime 
				FROM soberano."fn_ShiftClosure_getTimes"(fromClosureId)
				INTO pTime;
	
			SELECT closuretime 
				FROM soberano."fn_ShiftClosure_getTimes"(untilClosureId)
				INTO cTime;

			DROP TABLE IF EXISTS shiftDateTimes;
			CREATE TEMPORARY TABLE shiftDateTimes(shift date, datetime timestamp with time zone);

			--calc the shifts within which orders were closed
			INSERT INTO shiftDateTimes 
				SELECT DISTINCT soberano."fn_ShiftClosure_getShiftFromDateTime"(orderCollectingVotet."This_is_on_Timestamp") oDate,
								orderCollectingVotet."This_is_on_Timestamp"
					FROM soberano."Order" ord
						INNER JOIN metamodel."EntityTypeInstance" eti
							ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = ord."This_is_identified_by_EntityTypeInstance_id"
									
								--only closed orders
								AND eti."This_is_in_Stage_with_StageHasStageId" = 6
						INNER JOIN metamodel."Vote" orderCollectingVotet
							ON ord."This_is_identified_by_EntityTypeInstance_id" = orderCollectingVotet."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"
			
								--orders closed (collected) within the passed time interval
								AND orderCollectingVotet."This_favors_Decision_with_DecisionHasDecisionId" = 3005
								AND orderCollectingVotet."This_is_on_Timestamp" > pTime
								AND orderCollectingVotet."This_is_on_Timestamp" <= cTime;

			RETURN QUERY SELECT shift,
								"This_has_Currency_code",
								SUM("Amount")
							FROM soberano."Order" ord
								INNER JOIN metamodel."EntityTypeInstance" eti
									ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = ord."This_is_identified_by_EntityTypeInstance_id"
					
										--only closed orders
										AND eti."This_is_in_Stage_with_StageHasStageId" = 6
								INNER JOIN metamodel."Vote" orderCollectingVotet
									ON ord."This_is_identified_by_EntityTypeInstance_id" = orderCollectingVotet."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"
					
										--orders closed (collected) within the passed time interval
										AND orderCollectingVotet."This_favors_Decision_with_DecisionHasDecisionId" = 3005
										AND orderCollectingVotet."This_is_on_Timestamp" > pTime
										AND orderCollectingVotet."This_is_on_Timestamp" <= cTime
								INNER JOIN shiftDateTimes
									ON shiftDateTimes.datetime = orderCollectingVotet."This_is_on_Timestamp"
								INNER JOIN soberano."Deposit" depo
									ON depo."This_is_for_Order_with_OrderHasOrderId" = ord."OrderHasOrderId"
								INNER JOIN soberano."DepositAmount" depoamnt
									ON depoamnt."DepositHasDepositId" = depo."DepositHasDepositId"
								INNER JOIN soberano."Currency" curr
									ON curr."CurrencyHasCurrencyId" = depoamnt."CurrencyHasCurrencyId"
							GROUP BY shift,
									"This_has_Currency_code"
							ORDER BY shift ASC,
									"This_has_Currency_code" ASC;									
	ELSE
		RETURN QUERY SELECT now()::date,
							''::character varying,
							0.0;
	END IF;
END;
$BODY$;