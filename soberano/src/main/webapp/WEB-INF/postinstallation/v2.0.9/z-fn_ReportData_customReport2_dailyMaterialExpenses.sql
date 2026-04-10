CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport2_dailyMaterialExpenses"(
	lang character,
	fromd date,
	untild date,
	loginname character varying)
    RETURNS TABLE(expensedate date, "iName" character varying, "iQty" numeric, "iUnit" character varying, "iPrice" numeric) 
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
				SELECT DISTINCT soberano."fn_ShiftClosure_getShiftFromDateTime"(expense."This_is_at_DateTime") eDate,
								expense."This_is_at_DateTime"
					FROM soberano."Expense" expense
						INNER JOIN soberano."MaterialExpense" matexp
							ON expense."ExpenseHasExpenseId" = matexp."ExpenseHasExpenseId"
						INNER JOIN metamodel."EntityTypeInstance" eti
							ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = matexp."This_is_identified_by_EntityTypeInstance_id"
									
								--only not cancelled expense
								AND eti."This_is_in_Stage_with_StageHasStageId" = 4
								
								--with expense time within the passed time interval
								AND expense."This_is_at_DateTime" > pTime
								AND expense."This_is_at_DateTime" <= cTime;

			RETURN QUERY SELECT shift,
								"This_has_Name",
								"This_is_for_Quantity",
								"Acronym",
								"Amount" / "This_is_for_Quantity"
							FROM soberano."ExpenseAmount" expamount
								INNER JOIN soberano."MaterialExpense" matexp
									ON expamount."ExpenseHasExpenseId" = matexp."ExpenseHasExpenseId"
								INNER JOIN soberano."MaterialAcquisition" matacq
									ON matacq."ExpenseHasExpenseId" = matexp."ExpenseHasExpenseId"
								INNER JOIN soberano."AcquirableMaterial" am
									ON am."AcquirableMaterialHasAcquirableMaterialId" = matacq."AcquirableMaterialHasAcquirableMaterialId"
								INNER JOIN soberano."InventoryItem" iitem
									ON iitem."InventoryItemHasInventoryItemCode" = am."InventoryItemHasInventoryItemCode"
								INNER JOIN soberano."UnitHasAcronymInLanguage" unit
									ON unit."UnitHasUnitId" = matacq."This_is_in_Unit_with_UnitHasUnitId"
										AND unit."Language" = lang
								INNER JOIN soberano."Expense" expense
									ON expense."ExpenseHasExpenseId" = matexp."ExpenseHasExpenseId"
										AND expense."This_is_at_DateTime" > pTime
										AND expense."This_is_at_DateTime" <= cTime
								INNER JOIN metamodel."EntityTypeInstance" eti
									ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = matexp."This_is_identified_by_EntityTypeInstance_id"
										
										--not cancelled expenses
										AND eti."This_is_in_Stage_with_StageHasStageId" = 4
								INNER JOIN shiftDateTimes
									ON shiftDateTimes.datetime = expense."This_is_at_DateTime"
							ORDER BY shift ASC,
									iitem."This_has_Name" ASC;									
	ELSE
		RETURN QUERY SELECT now()::date,
							''::character varying,
							0.0::numeric,
							''::character varying,
							0.0::numeric;
	END IF;
END;
$BODY$;