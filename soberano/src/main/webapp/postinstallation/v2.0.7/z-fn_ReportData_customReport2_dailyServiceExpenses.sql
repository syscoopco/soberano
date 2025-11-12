CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport2_dailyServiceExpenses"(
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
	
BEGIN
	IF ('Manager' IN (SELECT * from metamodel."fn_User_getResponsibilities"(loginname)))
	
		--or make decisions on an existing closure
		OR EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(20, 1, loginname)) THEN

			RETURN QUERY SELECT expense."This_is_at_DateTime"::date shift,
								"This_has_Name",
								1::numeric,
								'U'::character varying,
								SUM("Amount")
							FROM soberano."ExpenseAmount" expamount
								INNER JOIN soberano."ServiceExpense" matexp
									ON expamount."ExpenseHasExpenseId" = matexp."ExpenseHasExpenseId"
								INNER JOIN soberano."Service" am
									ON am."ServiceHasServiceId" = matexp."This_is_on_Service_with_ServiceHasServiceId"
								INNER JOIN soberano."Expense" expense
									ON expense."ExpenseHasExpenseId" = matexp."ExpenseHasExpenseId"
										AND expense."This_is_at_DateTime"::date >= fromd
										AND expense."This_is_at_DateTime"::date <= untild
								INNER JOIN metamodel."EntityTypeInstance" eti
									ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = matexp."This_is_identified_by_EntityTypeInstance_id"
										
										--not cancelled expenses
										AND eti."This_is_in_Stage_with_StageHasStageId" = 4
							GROUP BY shift,
									"This_has_Name"								
							ORDER BY shift ASC,
									am."This_has_Name" ASC;									
	ELSE
		RETURN QUERY SELECT now()::date,
							''::character varying,
							0.0::numeric,
							''::character varying,
							0.0::numeric;
	END IF;
END;
$BODY$;