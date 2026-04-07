CREATE OR REPLACE FUNCTION soberano."z-fn_Helper_saveHistory"(
	)
    RETURNS void
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE
	lastSavedVoteId integer;
	lastSavedStockChangeId integer;
BEGIN
	IF EXISTS(SELECT * 
			FROM soberano."StockChange_history") THEN
		SELECT "StockChangeHasStockChangeId" 
			FROM soberano."StockChange_history"
			ORDER BY "StockChangeHasStockChangeId" DESC
			LIMIT 1
			INTO lastSavedStockChangeId;			
	ELSE
		lastSavedStockChangeId := 0;
	END IF;
	
	IF EXISTS(SELECT * 
			FROM metamodel."Vote_history") THEN
		SELECT "VoteHasVoteId" 
			FROM metamodel."Vote_history"
			ORDER BY "VoteHasVoteId" DESC
			LIMIT 1
			INTO lastSavedVoteId;			
	ELSE
		lastSavedVoteId := 0;
	END IF;
	
	INSERT INTO soberano."StockChange_history"("StockChangeHasStockChangeId", 
												"This_is_at_DateTime", 
												"This_is_called_with_Quantity", 
												"This_saves_previous_Quantity", 
												"This_saves_previous_Value", 
												"This_is_called_with_Value", 
												"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId", 
												"This_is_called_with_InventoryItem_with_InventoryItemHasInventor", 
												"This_is_triggered_by_MaterialExpense_with_ExpenseHasExpenseId", 
												"This_takes_value_from_Warehouse_with_WarehouseHasWarehouseId", 
												"This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId", 
												"This_is_of_InventoryOperation_with_InventoryOperationHasInvento")
		SELECT "StockChangeHasStockChangeId", 
				"This_is_at_DateTime", 
				"This_is_called_with_Quantity", 
				"This_saves_previous_Quantity", 
				"This_saves_previous_Value", 
				"This_is_called_with_Value", 
				"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId", 
				"This_is_called_with_InventoryItem_with_InventoryItemHasInventor", 
				"This_is_triggered_by_MaterialExpense_with_ExpenseHasExpenseId", 
				"This_takes_value_from_Warehouse_with_WarehouseHasWarehouseId", 
				"This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId", 
				"This_is_of_InventoryOperation_with_InventoryOperationHasInvento"
			FROM soberano."StockChange"
			WHERE "StockChangeHasStockChangeId" > lastSavedStockChangeId
				AND now()::date - "This_is_at_DateTime"::date > 100; --registros más viejos que 100 días
		
	DELETE FROM soberano."StockChange"
		WHERE "StockChangeHasStockChangeId" > lastSavedStockChangeId
		AND now()::date - "This_is_at_DateTime"::date > 100; --registros más viejos que 100 días
		
	INSERT INTO metamodel."Vote_history"("VoteHasVoteId", 
										 "This_is_on_Timestamp", 
										 "This_has_an_Argument", 
										 "This_favors_Decision_with_DecisionHasDecisionId", 
										 "This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT", 
										 "This_is_by_User_with_UserHasUserId")
		SELECT "VoteHasVoteId", 
				"This_is_on_Timestamp", 
				"This_has_an_Argument", 
				"This_favors_Decision_with_DecisionHasDecisionId", 
				"This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT", 
				"This_is_by_User_with_UserHasUserId"
			FROM metamodel."Vote"
			WHERE "VoteHasVoteId" > lastSavedVoteId
				AND now()::date - "This_is_on_Timestamp"::date > 100; --registros más viejos que 100 días
				
	DELETE FROM metamodel."Vote"
		WHERE "VoteHasVoteId" > lastSavedVoteId
				AND now()::date - "This_is_on_Timestamp"::date > 100; --registros más viejos que 100 días
		
END;
$BODY$;