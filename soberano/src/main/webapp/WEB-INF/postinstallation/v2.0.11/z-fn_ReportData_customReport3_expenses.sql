CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport3_expenses"(
	fromd date,
	untild date,
	loginname character varying)
    RETURNS TABLE("materialExpenses" numeric, "serviceExpenses" numeric, "payrollExpenses" numeric) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1

AS $BODY$
DECLARE
	pTime timestamp with time zone;
	cTime timestamp with time zone;
	fromClosureId integer;
	untilClosureId integer;

	materialExpenses numeric;	
	serviceExpenses numeric;
	payrollExpenses numeric;	
BEGIN
	--the user has rights to record a closure,
	IF (SELECT 'Manager' IN (SELECT metamodel."fn_User_getResponsibilities"(loginname))) THEN

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

		--material expenses
		SELECT COALESCE(SUM("Amount" * "This_is_converted_to_system_currency_multiplying_by_ExchangeRat"), 0)
			FROM soberano."ExpenseAmount" amount
				INNER JOIN soberano."Currency" currency
					ON currency."CurrencyHasCurrencyId" = amount."This_is_in_Currency_with_CurrencyHasCurrencyId"
				INNER JOIN soberano."MaterialExpense" claexpense
					ON claexpense."ExpenseHasExpenseId" = amount."ExpenseHasExpenseId"
				INNER JOIN soberano."Expense" expense
					ON expense."ExpenseHasExpenseId" = amount."ExpenseHasExpenseId"
				INNER JOIN metamodel."EntityTypeInstance" eti
					ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = claexpense."This_is_identified_by_EntityTypeInstance_id" 
	
			--not cancelled and within the passed time interval
			WHERE "This_is_in_Stage_with_StageHasStageId" != 5
				AND "This_is_at_DateTime" > pTime
					AND "This_is_at_DateTime" <= cTime
			INTO materialExpenses;
		
		--service expenses
		SELECT COALESCE(SUM("Amount" * "This_is_converted_to_system_currency_multiplying_by_ExchangeRat"), 0)
			FROM soberano."ExpenseAmount" amount
				INNER JOIN soberano."Currency" currency
					ON currency."CurrencyHasCurrencyId" = amount."This_is_in_Currency_with_CurrencyHasCurrencyId"
				INNER JOIN soberano."ServiceExpense" claexpense
					ON claexpense."ExpenseHasExpenseId" = amount."ExpenseHasExpenseId"
				INNER JOIN soberano."Expense" expense
					ON expense."ExpenseHasExpenseId" = amount."ExpenseHasExpenseId"
				INNER JOIN metamodel."EntityTypeInstance" eti
					ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = claexpense."This_is_identified_by_EntityTypeInstance_id" 
	
			--not cancelled and within the passed time interval
			WHERE "This_is_in_Stage_with_StageHasStageId" != 5
				AND "This_is_at_DateTime" > pTime
					AND "This_is_at_DateTime" <= cTime
			INTO serviceExpenses;
		
		--payroll expenses
		SELECT COALESCE(SUM("Amount" * "This_is_converted_to_system_currency_multiplying_by_ExchangeRat"), 0)
			FROM soberano."ExpenseAmount" amount
				INNER JOIN soberano."Currency" currency
					ON currency."CurrencyHasCurrencyId" = amount."This_is_in_Currency_with_CurrencyHasCurrencyId"
				INNER JOIN soberano."PayrollExpense" claexpense
					ON claexpense."ExpenseHasExpenseId" = amount."ExpenseHasExpenseId"
				INNER JOIN soberano."Expense" expense
					ON expense."ExpenseHasExpenseId" = amount."ExpenseHasExpenseId"
				INNER JOIN metamodel."EntityTypeInstance" eti
					ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = claexpense."This_is_identified_by_EntityTypeInstance_id" 
	
			--not cancelled and within the passed time interval
			WHERE "This_is_in_Stage_with_StageHasStageId" != 5
				AND "This_is_at_DateTime" > pTime
				AND "This_is_at_DateTime" <= cTime
			INTO payrollExpenses;

		RETURN QUERY SELECT materialExpenses,
						serviceExpenses,
						payrollExpenses;
	ELSE
		RETURN QUERY SELECT 0.0::numeric,
							0.0::numeric,
							0.0::numeric;
	END IF;
END;
$BODY$;