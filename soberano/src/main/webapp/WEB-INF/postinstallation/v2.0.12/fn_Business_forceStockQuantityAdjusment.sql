CREATE OR REPLACE FUNCTION soberano."fn_Business_forceStockQuantityAdjusment"(
	warehouseid integer,
	inventoryitemcode character varying,
	inventoryitemquantity numeric,
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	DECLARE
		qryResult integer;
		entityTypeInstanceId integer;
		decisionId integer;
	BEGIN
		--default returning value. user has no right.
		qryResult := -1;		
		
		--it's an owner
		IF 'Owner' IN (SELECT * from metamodel."fn_User_getResponsibilities"(loginname)) THEN

			UPDATE soberano."Stock"
				SET "Quantity" = inventoryItemQuantity
				WHERE "WarehouseHasWarehouseId" = warehouseId
					AND "InventoryItemHasInventoryItemCode" = inventoryItemCode;

			SELECT objectdata."This_is_identified_by_EntityTypeInstance_id", 
				decision."DecisionHasDecisionId"
			FROM (SELECT "This_is_identified_by_EntityTypeInstance_id" FROM soberano."Configuration") objectdata
				INNER JOIN (SELECT "DecisionHasDecisionId", 
									"This_has_Name" 
								FROM metamodel."Decision") decision
					ON decision."DecisionHasDecisionId" = 
						(SELECT "DecisionId" 
						 	FROM metamodel."fn_EntityTypeInstance_getDecisions"(
										objectdata."This_is_identified_by_EntityTypeInstance_id",
										loginname)
							WHERE "DecisionName" =
						   
								/************/
									'Apply'
						   		/************/						   
						   
						   )
			INTO entityTypeInstanceId,
				decisionId;	

			--make the decision. this is treated like a configuration decision and it's logged as-is.
			PERFORM metamodel."fn_Vote_vote"(loginname,
											entityTypeInstanceId, 
											decisionId, 
											'tt_Stock_tt ' || 
											' tt_MODIFIED_BY_tt ' || 
											loginname || 
											'. tt_Warehouse_tt : ' || warehouseid::text || '. ' ||
											'tt_Item_tt : ' || inventoryitemcode || '. ' ||
											'tt_NewStockQuantity_tt : ' || inventoryItemQuantity::text || '. ');

			qryResult := 0;
		
		ELSE
			qryResult := -1;
		END IF;
		RETURN qryResult;
END;
$BODY$;