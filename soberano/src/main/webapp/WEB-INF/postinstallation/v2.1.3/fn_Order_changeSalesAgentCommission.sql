CREATE OR REPLACE FUNCTION soberano."fn_Order_changeSalesAgentCommission"(
	orderid integer,
	commission numeric,
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
		
		currentCommission double precision;
	BEGIN
		--default returning value. user has no right.
		qryResult := -1;
		
		SELECT proc.*
			FROM (SELECT objectdata."This_is_identified_by_EntityTypeInstance_id", 
								objectdata."This_is_identified_by_Label",
								decision."DecisionHasDecisionId",
				  				sa."This_carries_a_Commission"
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
							WHERE objectdata."OrderHasOrderId" = orderid) proc
				INTO entityTypeInstanceId,
					objectCode,
					decisionId,
					currentCommission;
					
		IF currentCommission IS NULL THEN
			currentCommission := 0.0;
		END IF;
		
		--user has rights
		IF decisionId IS NOT NULL AND entityTypeInstanceId IS NOT NULL THEN

			--update commission
			UPDATE soberano."SalesAgentOrderCapture" sa SET "This_carries_a_Commission" = COALESCE(commission::double precision, 0.0::double precision)
				WHERE sa."OrderHasOrderId" = orderid;

			--apply new commission to the current catalog prices for the order's all items
			UPDATE soberano."ProcessRunOutputHasPriceForOrder" prohpfo
					SET "Price" = product."This_has_Price" * (1 + COALESCE(commission::double precision, 0.0::double precision) / 100)
				FROM soberano."Product" product, soberano."SalesAgentOrderCapture" sa
				WHERE prohpfo."InventoryItemHasInventoryItemCode" = product."InventoryItemHasInventoryItemCode"
					AND prohpfo."OrderHasOrderId" = orderId
					AND prohpfo."OrderHasOrderId" = sa."OrderHasOrderId";
			
			--make the decision
			PERFORM metamodel."fn_Vote_vote"(loginname,
											entityTypeInstanceId, 
											decisionId,
											'tt_SALES_AGENT_COMMISSION_CHANGED_FOR_ORDER_tt ' 
											 	|| CAST(orderid AS text) || ' : ' 
											 	|| objectCode || chr(13) 
											 	|| ' tt_BY_tt ' || loginname || chr(13)  
											 	|| '. tt_BEFORE_tt: ' || currentCommission || chr(13)  
											 	|| '. tt_NOW_tt: ' || commission);
			qryResult := 0;

		END IF;
					
		RETURN qryResult;
END;
$BODY$;