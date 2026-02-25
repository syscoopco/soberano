INSERT INTO "metamodel"."Responsibility" ("ResponsibilityHasResponsibilityId",
											"This_has_Name")
	VALUES (22, 'Owner');

CREATE OR REPLACE FUNCTION soberano."fn_Business_clean"(
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	DECLARE
		qryResult integer;
	BEGIN
		--default returning value. user has no right.
		qryResult := -1;		
		
		--it's an owner
		IF 'Owner' IN (SELECT * from metamodel."fn_User_getResponsibilities"(loginname)) THEN

			--orders
			DELETE FROM metamodel."EntityTypeInstance" eti
				USING soberano."Order" ord
				WHERE ord."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId";

			--stock change records
			DELETE FROM soberano."StockChange";

			--deposits
			DELETE FROM metamodel."EntityTypeInstance" eti
				USING soberano."Deposit" ord
				WHERE ord."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId";

			--withdrawals
			DELETE FROM metamodel."EntityTypeInstance" eti
				USING soberano."Withdrawal" ord
				WHERE ord."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId";

			--cash register balancing
			DELETE FROM metamodel."EntityTypeInstance" eti
				USING soberano."Balancing" ord
				WHERE ord."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId";

			--inventory operations
			DELETE FROM metamodel."EntityTypeInstance" eti
				USING soberano."InventoryOperation" ord
				WHERE ord."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId";

			--production records
			DELETE FROM metamodel."EntityTypeInstance" eti
				USING soberano."ProcessRun" ord
				WHERE ord."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId";

			--material expenses
			DELETE FROM metamodel."EntityTypeInstance" eti
				USING soberano."MaterialExpense" ord
				WHERE ord."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId";

			--payroll expenses
			DELETE FROM metamodel."EntityTypeInstance" eti
				USING soberano."PayrollExpense" ord
				WHERE ord."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId";

			--service expenses
			DELETE FROM metamodel."EntityTypeInstance" eti
				USING soberano."ServiceExpense" ord
				WHERE ord."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId";

			--balances on shift closures
			DELETE FROM soberano."ShiftClosureRecordsBalanceInCurrency";
			
			--history
			DELETE FROM soberano."StockChange_history";
			DELETE FROM metamodel."Vote_history";

			qryResult := 0;
		
		ELSE
			qryResult := -1;
		END IF;
		RETURN qryResult;
END;
$BODY$;