CREATE OR REPLACE FUNCTION soberano."fn_Order_make"(
	orderid integer,
	itemid integer,
	description character varying,
	runs numeric,
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	DECLARE
		objectCode character varying;
		processName character varying;
		entityTypeInstanceId integer;
		decisionId integer;
		processId integer;
		processRunCode text;
		processRunId integer;
		costCenter integer;
		inputitems character varying[];
		inputquantities numeric[];
		inputunits integer[];
		outputitems character varying[];
		outputquantities numeric[];
		outputunits integer[];
		weights integer[];
		qryResult integer;
		
		deliveryProviderIsReseller boolean;
		deliveryProviderRate numeric;
		compensateDeliveryProviderRates boolean;

		currentUserId integer;
	BEGIN
		--default returning value. user has no right.
		qryResult := -1;
--if the counter is stocked by a cost center, that cost center has precedence over the
--cost center where the product is usually produced.
--IMPORTANT: the cost center doesn't change in case the counter changes (order movement)
SELECT "This_is_stocked_by_CostCenter_with_CostCenterHasCostCenterId"
			FROM soberano."Counter" counter
				INNER JOIN soberano."CounterOrder" counterorder
					ON counter."CounterHasCounterId" = counterorder."CounterHasCounterId"
						AND counterorder."OrderHasOrderId" = orderid
			INTO costCenter;		
		SELECT proc.*, 
				CASE WHEN inputitemsarr IS NULL THEN ARRAY[]::character varying[] ELSE inputitemsarr END,
				array(select unnest(inputquantitiesarr) * runs),
				CASE WHEN inputunitsarr IS NULL THEN ARRAY[]::integer[] ELSE inputunitsarr END,
				outputitemsarr,
				array(select unnest(outputquantitiesarr) * runs),
				outputunitsarr,
				weightsarr
			FROM (SELECT objectdata."This_is_identified_by_EntityTypeInstance_id", 
								objectdata."This_is_identified_by_Label",
								process."ProcessHasProcessId",
								CASE WHEN costCenter IS NULL 
										THEN "This_is_usually_produced_in_CostCenter_with_CostCenterHasCostCe"
										ELSE costCenter END,
								decision."DecisionHasDecisionId",
				  				process."This_has_Name"
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
													'Make'
												/************/						   

										   )
								INNER JOIN soberano."Product" product
									ON product."ProductHasProductId" = itemid
								INNER JOIN soberano."ProcessOutput" processoutput
									ON processoutput."InventoryItemHasInventoryItemCode" = product."InventoryItemHasInventoryItemCode"
				  				INNER JOIN soberano."Process" process
				  					ON process."ProcessHasProcessId" = processoutput."ProcessHasProcessId"
							WHERE "OrderHasOrderId" = orderid

							--only one of the processes that produces the item is returned
							--the last one configured
							ORDER BY "ProcessHasProcessId" DESC
							LIMIT 1) proc
					LEFT JOIN (SELECT "ProcessHasProcessId",
									ARRAY_AGG("InventoryItemHasInventoryItemCode") inputitemsarr,
									ARRAY_AGG("Quantity") inputquantitiesarr,
									ARRAY_AGG("This_is_measured_in_Unit_with_UnitHasUnitId") inputunitsarr
								FROM (SELECT "ProcessHasProcessId",
									 		pin."InventoryItemHasInventoryItemCode",
											"Quantity",
											"This_is_measured_in_Unit_with_UnitHasUnitId"
									 	FROM  soberano."ProcessInput" pin
											INNER JOIN soberano."InventoryItem" iitem
												ON iitem."InventoryItemHasInventoryItemCode" = pin."InventoryItemHasInventoryItemCode"
											ORDER BY "ProcessHasProcessId" ASC, pin."InventoryItemHasInventoryItemCode" ASC) sqi
							   		GROUP BY "ProcessHasProcessId") pinput
						ON proc."ProcessHasProcessId" = pinput."ProcessHasProcessId"
					LEFT JOIN (SELECT "ProcessHasProcessId",
									ARRAY_AGG("InventoryItemHasInventoryItemCode") outputitemsarr,
									ARRAY_AGG("Quantity") outputquantitiesarr,
									ARRAY_AGG("This_is_measured_in_Unit_with_UnitHasUnitId") outputunitsarr,
							   		ARRAY_AGG("This_value_is_weighted_by_WeightCoefficient") weightsarr
								FROM (SELECT "ProcessHasProcessId",
									 		pout."InventoryItemHasInventoryItemCode",
											"Quantity",
											"This_is_measured_in_Unit_with_UnitHasUnitId",
									  		"This_value_is_weighted_by_WeightCoefficient"
									 	FROM  soberano."ProcessOutput" pout
											INNER JOIN soberano."InventoryItem" iitem
												ON iitem."InventoryItemHasInventoryItemCode" = pout."InventoryItemHasInventoryItemCode"
											ORDER BY "ProcessHasProcessId" ASC, pout."InventoryItemHasInventoryItemCode" ASC) sqo
							   		GROUP BY "ProcessHasProcessId") poutput
						ON proc."ProcessHasProcessId" = poutput."ProcessHasProcessId"
				INTO entityTypeInstanceId,
					objectCode,
					processId,
					costCenter,
					decisionId,
					processName,
					inputitems,
					inputquantities,
					inputunits,
					outputitems,
					outputquantities,
					outputunits,
					weights;		
			
		--user has rights
		IF decisionId IS NOT NULL AND entityTypeInstanceId IS NOT NULL THEN
		
			--create process run
			SELECT array_to_string(ARRAY(SELECT chr((65 + round(random() * 25)) :: integer) 
											FROM generate_series(1, 15)), 
								   '')
				INTO processRunCode;
			SELECT soberano."fn_ProcessRun_create"(processRunCode,
													processId,
													costCenter,
													inputitems,
													inputquantities,
													inputunits,
													outputitems,
													outputquantities,
													outputunits,
													weights,
													'soberano.user.top') 
				INTO processRunId;

			SELECT "UserHasUserId" 
				FROM metamodel."User" 
				WHERE "This_has_LoginName" = loginname 
				INTO currentUserId;

			UPDATE metamodel."EntityTypeInstance" eti
				SET "This_is_created_by_User_with_UserHasUserId" = currentUserId
				FROM soberano."ProcessRun" pr
				WHERE pr."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId"
					AND "ProcessRunHasProcessRunId" = processRunId;
				
			IF processRunId > 0 THEN
			
				INSERT INTO soberano."OrderProcessRun"("This_has_Description",
												   "This_has_ordered_Runs",
												   "This_has_customer-canceled_Runs",
												   "This_has_full_discounted_Runs",
												   "This_has_ended_Runs",
												   "ProcessRunHasProcessRunId",
												   "OrderHasOrderId")
					VALUES (description, 
							runs,
							0,
							0,
							runs,
							processRunId,
							orderid);

				INSERT INTO soberano."ProcessRunOutputHasPriceForOrder"("Price",
																		"ProcessRunHasProcessRunId",
																		"InventoryItemHasInventoryItemCode", 
																		"OrderHasOrderId", 
																		"This_is_in_Currency_with_CurrencyHasCurrencyId")
					SELECT "This_has_Price",
						   processRunId,
						   product."InventoryItemHasInventoryItemCode",
						   orderid,
						   "CurrencyHasCurrencyId" 
						FROM soberano."Product" product
							INNER JOIN soberano."ProcessRunOutput" prout
								ON product."InventoryItemHasInventoryItemCode" = prout."InventoryItemHasInventoryItemCode"
									AND prout."ProcessRunHasProcessRunId" = processRunId
							INNER JOIN soberano."Currency" curr
								ON curr."Currency_is_system_currency";

				--make the decision
				PERFORM metamodel."fn_Vote_vote"(loginname,
												entityTypeInstanceId, 
												decisionId,
												CAST(runs AS text) || ' ' || processName || ' tt_WITH_DESCRIPTION_tt ' || description || chr(13) || ' tt_ADDED_TO_ORDER_tt ' || CAST(orderid AS text) || ' : ' || objectCode || chr(13) || ' tt_BY_tt ' || loginname);
				qryResult := processRunId;
				
				--compensate delivery provider rate
				SELECT "This_has_CompensateDeliveryProviderRates",
						COALESCE(dp."This_charges_Rate_on_order_amount", 0),
						"DeliveryProvider_is_reseller"
					FROM soberano."Configuration" conf
						LEFT JOIN soberano."Delivery" de
							ON "OrderHasOrderId" = orderId
						LEFT JOIN soberano."DeliveryProvider" dp
							ON dp."DeliveryProviderHasDeliveryProviderId" = de."DeliveryProviderHasDeliveryProviderId"
					INTO compensateDeliveryProviderRates, 
						deliveryProviderRate,
						deliveryProviderIsReseller;
						
				IF compensateDeliveryProviderRates 
					AND deliveryProviderIsReseller 
					AND deliveryProviderRate > 0.1 THEN
					
					--set compensated prices
					UPDATE soberano."ProcessRunOutputHasPriceForOrder"

						SET "Price" = "Price" * (100  + deliveryProviderRate) / 100
							WHERE "ProcessRunHasProcessRunId" = processRunId;
										
					/* --after compensation, round up to the nearest multiple of 10
					UPDATE soberano."ProcessRunOutputHasPriceForOrder"
						SET "Price" = round("Price" * (100  + deliveryProviderRate) / 100 / 10) * 10
							WHERE "ProcessRunHasProcessRunId" = processRunId;
					*/
					
				END IF;
				----------
				
			END IF;
		END IF;
		RETURN qryResult;
END;
$BODY$;