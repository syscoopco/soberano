CREATE OR REPLACE FUNCTION soberano."fn_Product_disable"(
	itemid integer,
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	DECLARE
		entityTypeInstanceId integer;
		decisionId integer;
		objectCode character varying;
		qryResult integer;
	BEGIN
		--default returning value. user has no right.
		qryResult := -1;
		
		SELECT "This_is_identified_by_EntityTypeInstance_id", 
				ii."This_has_Name",
				decision."DecisionHasDecisionId" 
			FROM soberano."Product" objectdata
				INNER JOIN soberano."InventoryItem" ii
					ON ii."InventoryItemHasInventoryItemCode" = objectdata."InventoryItemHasInventoryItemCode"
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
									'Disable'
						   		/************/						   
						   
						   )
			WHERE "ProductHasProductId" = itemid
			INTO entityTypeInstanceId,
				objectCode,
				decisionId;		
			
		--user has rights
		IF decisionId IS NOT NULL AND entityTypeInstanceId IS NOT NULL THEN

			UPDATE soberano."Product"
				SET "Product_is_enabled" = false
				WHERE "ProductHasProductId" = itemid;
				
			--make the decision
			PERFORM metamodel."fn_Vote_vote"(loginname,
											entityTypeInstanceId, 
											decisionId, 
											'tt_Product_tt ' || CAST(itemid AS text) || ' : ' || objectCode || chr(13) || ' tt_DISABLED_BY_tt ' || loginname);
				
			qryResult := 0;
		END IF;
		RETURN qryResult;
END;
$BODY$;