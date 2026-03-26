CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport2_monthlyTransfers"(
	lang character,
	fromd date,
	untild date,
	loginname character varying)
    RETURNS TABLE(transferdate text, transfernote text, 
				transferorder integer, transferprocessor character varying, 
				transferamount numeric) 
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
				SELECT DISTINCT soberano."fn_ShiftClosure_getShiftFromDateTime"(eti."This_is_created_at_Timestamp") eDate,
								eti."This_is_created_at_Timestamp"
					FROM soberano."Deposit" depo
						INNER JOIN soberano."DepositAmount" depoamnt
							ON depo."DepositHasDepositId" = depoamnt."DepositHasDepositId"
						INNER JOIN metamodel."EntityTypeInstance" eti
							ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = depo."This_is_identified_by_EntityTypeInstance_id"
									
								--with deposit times within the passed time interval
								AND eti."This_is_created_at_Timestamp" > pTime
								AND eti."This_is_created_at_Timestamp" <= cTime

								--payment depos
								AND depo."This_is_for_Order_with_OrderHasOrderId" IS NOT NULL
								AND depoamnt."Amount" > 0;

			RETURN QUERY SELECT DATE_PART('month', shift)::text,
								to_char(eti."This_is_created_at_Timestamp", 'YY-MM-DD HH24:MI') || ' ' || COALESCE("This_has_Note", ''),
								depo."This_is_for_Order_with_OrderHasOrderId",
								curr."This_has_Currency_code",
								depoamnt."Amount"
							FROM soberano."Deposit" depo
								INNER JOIN soberano."DepositAmount" depoamnt
									ON depo."DepositHasDepositId" = depoamnt."DepositHasDepositId"
										AND depo."This_is_for_Order_with_OrderHasOrderId" IS NOT NULL
										AND depoamnt."Amount" > 0
								INNER JOIN metamodel."EntityTypeInstance" eti
									ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = depo."This_is_identified_by_EntityTypeInstance_id"
										AND eti."This_is_created_at_Timestamp" > pTime 
										AND eti."This_is_created_at_Timestamp" <= cTime
								INNER JOIN soberano."Currency" curr
									ON curr."CurrencyHasCurrencyId" = depoamnt."CurrencyHasCurrencyId"
										AND NOT curr."Currency_is_cash"
								INNER JOIN shiftDateTimes
									ON shiftDateTimes.datetime = eti."This_is_created_at_Timestamp"
								ORDER BY shift ASC,
									eti."This_is_created_at_Timestamp" ASC,
									curr."This_has_Currency_code" ASC;						
	ELSE
		RETURN QUERY SELECT '',
							'',
							''::character varying,
							''::character varying,
							0.0::numeric;
	END IF;
END;
$BODY$;