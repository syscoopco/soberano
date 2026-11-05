CREATE OR REPLACE FUNCTION soberano."fn_Order_changeSalesAgent"(
	orderid integer,
	providerid integer,
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	DECLARE
		qryResult integer;
		entityTypeInstanceId integer;
		objectCode character varying;
		decisionId integer;
		
		currentProviderId integer;
		currentProviderName character varying;
		newProviderId integer;
		newProviderName character varying;
	BEGIN
		--default returning value. user has no right.
		qryResult := -1;
		
		SELECT proc.*
			FROM (SELECT objectdata."This_is_identified_by_EntityTypeInstance_id", 
								objectdata."This_is_identified_by_Label",
								decision."DecisionHasDecisionId",
				  				sa."SalesAgent_ProviderHasProviderId",
				  				prov."This_has_Name"
							FROM soberano."Order" objectdata
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
													'Change'
												/************/						   

										   )
				 				LEFT JOIN soberano."SalesAgentOrderCapture" sa
				  					ON sa."OrderHasOrderId" = objectdata."OrderHasOrderId"
								LEFT JOIN soberano."Provider" prov
				  					ON sa."SalesAgent_ProviderHasProviderId" = prov."ProviderHasProviderId"
							WHERE objectdata."OrderHasOrderId" = orderid) proc
				INTO entityTypeInstanceId,
					objectCode,
					decisionId,
					currentProviderId,
					currentProviderName;
					
		IF currentProviderId IS NULL THEN
			currentProviderId := 0;
			currentProviderName := '';
		END IF;
		
		--user has rights
		IF decisionId IS NOT NULL AND entityTypeInstanceId IS NOT NULL THEN
		
			--get new provider data
			SELECT "ProviderHasProviderId", 
					"This_has_Name"
				FROM soberano."Provider" prov
				WHERE "ProviderHasProviderId" = providerid
				INTO newProviderId,
					newProviderName;

			--reset sales agent for the order (set no agent)
			DELETE FROM soberano."SalesAgentOrderCapture" sa
				WHERE sa."OrderHasOrderId" = orderid;

			--set current catalog prices for the order (set not applying a commission)
			UPDATE soberano."ProcessRunOutputHasPriceForOrder" prohpfo
					SET "Price" = product."This_has_Price"
				FROM soberano."Product" product
				WHERE prohpfo."InventoryItemHasInventoryItemCode" = product."InventoryItemHasInventoryItemCode"
					AND prohpfo."OrderHasOrderId" = orderId;
			
			IF newProviderId IS NOT NULL THEN				

				INSERT INTO soberano."SalesAgentOrderCapture"("This_carries_a_Commission", 
															"OrderHasOrderId", 
															"SalesAgent_ProviderHasProviderId")
					VALUES (0::double precision,
							orderid,
							newProviderId);
			ELSE
				newProviderId := 0;
				newProviderName := '';
			END IF;
				
			--make the decision
			PERFORM metamodel."fn_Vote_vote"(loginname,
											entityTypeInstanceId, 
											decisionId,
											'tt_SALES_AGENT_CHANGED_FOR_ORDER_tt ' 
											 	|| CAST(orderid AS text) || ' : ' 
											 	|| objectCode || chr(13) 
											 	|| ' tt_BY_tt ' || loginname || chr(13)  
											 	|| '. tt_BEFORE_tt: ' || currentProviderId || ':' || currentProviderName || chr(13)  
											 	|| '. tt_NOW_tt: ' || newProviderId || ':' || newProviderName);
			qryResult := 0;

		END IF;
					
		RETURN qryResult;
END;
$BODY$;