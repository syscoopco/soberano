CREATE OR REPLACE FUNCTION soberano."fn_ProcessRun_close"(
	processrunid integer,
	outputitems character varying[],
	outputquantities numeric[],
	outputunits integer[],
	weights integer[],
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    SET default_transaction_isolation='serializable'
AS $BODY$
	DECLARE
		entityTypeInstanceId integer;
		decisionId integer;
		objectCode character varying;
		inputsWarehouse integer;
		outputsWarehouse integer;
		qryResult integer;
		itemRecord record;
		qtybefore numeric;
		qtyafter numeric;
		unitvaluebefore numeric;
		inputvalue numeric;
		outputsTotalValue numeric;
		i integer;
		weighttoredistribute numeric;
		producedoutputcount integer;
		outputvalue numeric;
	BEGIN
		--default returning value. user has no right.
		qryResult := -1;
		
		SELECT "This_is_identified_by_EntityTypeInstance_id", 
				"This_has_Code",
				"DecisionId",
				"This_consumes_materials_from_InputWarehouse_with_WarehouseHasWa",
				"This_increases_stock_in_OutputWarehouse_with_WarehouseHasWareho" 
			FROM (SELECT *, 
						ROW_NUMBER () OVER (ORDER BY "DecisionId" ASC) rownumber 
					FROM (SELECT DISTINCT "DecisionId", 
											"DecisionName"
							  	
							  	--important to use here top user, for all the decisions 
						  		--on process runs (lifecycle 27) get retrieved					
								FROM metamodel."fn_EntityTypeInstance_getDecisions"(27, 1, 'Close%', 'soberano.user.top')
								ORDER BY "DecisionName") sq) closedec
			INNER JOIN (SELECT "CostCenterHasCostCenterId", 
								"This_consumes_materials_from_InputWarehouse_with_WarehouseHasWa",
								"This_increases_stock_in_OutputWarehouse_with_WarehouseHasWareho",
								ROW_NUMBER () OVER (ORDER BY "CostCenterHasCostCenterId" ASC) rownumber
							FROM soberano."CostCenter" coc
								INNER JOIN metamodel."EntityTypeInstance" eti
				 					ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = coc."This_is_identified_by_EntityTypeInstance_id"
				 			
										--cost center is enabled
										AND eti."This_is_in_Stage_with_StageHasStageId" = 2) cc
				ON cc.rownumber = closedec.rownumber
			INNER JOIN soberano."ProcessRun" objectdata
				ON "DecisionId" IN (SELECT "DecisionId" 
						 				FROM metamodel."fn_EntityTypeInstance_getDecisions"(
											objectdata."This_is_identified_by_EntityTypeInstance_id",
											loginname))
					AND objectData."This_is_executed_in_CostCenter_with_CostCenterHasCostCenterId" = cc."CostCenterHasCostCenterId"
			WHERE "ProcessRunHasProcessRunId" = processrunid
			INTO entityTypeInstanceId,
				objectCode,
				decisionId,
				inputsWarehouse,
				outputsWarehouse;		
			
		--user has rights
		IF decisionId IS NOT NULL AND entityTypeInstanceId IS NOT NULL THEN
		
			--verify process spec: compare IO items and units arrays with the process specs'
			IF ARRAY(SELECT item."InventoryItemHasInventoryItemCode"
						FROM soberano."InventoryItem" item
							INNER JOIN soberano."ProcessRunOutput" poutput
								ON item."InventoryItemHasInventoryItemCode" = poutput."InventoryItemHasInventoryItemCode"
					 		INNER JOIN soberano."ProcessRun" pr
					 			ON poutput."ProcessRunHasProcessRunId" = pr."ProcessRunHasProcessRunId"
					 				AND pr."ProcessRunHasProcessRunId" = processrunid
						ORDER BY item."InventoryItemHasInventoryItemCode" ASC) = outputItems

				AND

				ARRAY(SELECT "UnitHasUnitId" FROM (SELECT DISTINCT "UnitHasUnitId", item."InventoryItemHasInventoryItemCode"
						FROM soberano."InventoryItem" item
							INNER JOIN soberano."ProcessRunOutput" poutput
								ON item."InventoryItemHasInventoryItemCode" = poutput."InventoryItemHasInventoryItemCode"
					 		INNER JOIN soberano."ProcessRun" pr
					 			ON poutput."ProcessRunHasProcessRunId" = pr."ProcessRunHasProcessRunId"
					 				AND pr."ProcessRunHasProcessRunId" = processrunid
							INNER JOIN soberano."UnitHasAcronymInLanguage" unit
								ON unit."UnitHasUnitId" = item."This_is_measured_in_Unit_with_UnitHasUnitId"
						ORDER BY item."InventoryItemHasInventoryItemCode" ASC) sq) = outputUnits THEN
					
				--RULE_CONSTRAINT_3: weights sum 100			
				IF (SELECT (SELECT SUM(w) FROM (SELECT UNNEST(weights) as w) as sq) = 100) THEN
				
					--calc value transfered to outputs and update input warehouse stocks
					outputsTotalValue := 0;				
					FOR itemRecord IN SELECT pri."InventoryItemHasInventoryItemCode" item, 
												pri."Quantity" qtytoconsume,
												sk."Quantity" qtycurrent,
												round(sk."This_has_Value", 8) unitvaluecurrent, --ROUND IS IMPORTANT HERE!!!
												cc."This_consumes_materials_from_InputWarehouse_with_WarehouseHasWa" warehouse
											FROM soberano."ProcessRunInput" pri
												INNER JOIN soberano."ProcessRun" pr
													ON pri."ProcessRunHasProcessRunId" = pr."ProcessRunHasProcessRunId"
														AND pr."ProcessRunHasProcessRunId" = processrunid
												INNER JOIN soberano."CostCenter" cc
													ON pr."This_is_executed_in_CostCenter_with_CostCenterHasCostCenterId" = cc."CostCenterHasCostCenterId"
												LEFT JOIN soberano."Stock" sk
													ON pri."InventoryItemHasInventoryItemCode" = sk."InventoryItemHasInventoryItemCode"
														AND cc."This_consumes_materials_from_InputWarehouse_with_WarehouseHasWa" = sk."WarehouseHasWarehouseId" LOOP

						IF itemRecord.qtycurrent IS NULL THEN
							qtybefore := 0;
							unitvaluebefore := 0;														
						ELSE
							qtybefore := itemRecord.qtycurrent;
							unitvaluebefore := itemRecord.unitvaluecurrent;
						END IF;
						
						IF qtybefore < itemRecord.qtytoconsume THEN						
							IF qtybefore > 0 THEN							
								inputvalue := qtybefore * unitvaluebefore;
							ELSE
								inputvalue := 0;
							END IF;						
						ELSE						
							inputvalue := itemRecord.qtytoconsume * unitvaluebefore;							
						END IF;
						
						INSERT INTO soberano."ProcessRunInputValue"("Value", 
																	"ProcessRunHasProcessRunId", 
																	"InventoryItemHasInventoryItemCode", 
																	"This_is_in_Currency_with_CurrencyHasCurrencyId")
							SELECT inputvalue, processrunid, itemRecord.item, "CurrencyHasCurrencyId"
								FROM soberano."Currency" WHERE "Currency_is_system_currency";
						
						outputsTotalValue := outputsTotalValue + inputvalue;
						PERFORM soberano."fn_Stock_change"(NULL,
															itemRecord.item,
															itemRecord.warehouse,
															-itemRecord.qtytoconsume,
															CAST(0 AS numeric),
														    NULL,
														  	NULL,
														  	true,
														  	processrunid,
														    NULL);					
					END LOOP;
					
					--distribute outputs total value among outputs and update output warehouse stocks.
					--the weights of outputs equal to zero are redistributed among the other ones
					weighttoredistribute := 0;
					producedoutputcount := 0;
					i := 1;
					FOR itemRecord IN SELECT pro."InventoryItemHasInventoryItemCode" item,
												cc."This_increases_stock_in_OutputWarehouse_with_WarehouseHasWareho" warehouse
											FROM soberano."ProcessRunOutput" pro
												INNER JOIN soberano."ProcessRun" pr
													ON pro."ProcessRunHasProcessRunId" = pr."ProcessRunHasProcessRunId"
														AND pr."ProcessRunHasProcessRunId" = processrunid
												INNER JOIN soberano."CostCenter" cc
													ON pr."This_is_executed_in_CostCenter_with_CostCenterHasCostCenterId" = cc."CostCenterHasCostCenterId"
												LEFT JOIN soberano."Stock" sk
													ON pro."InventoryItemHasInventoryItemCode" = sk."InventoryItemHasInventoryItemCode"
														AND cc."This_increases_stock_in_OutputWarehouse_with_WarehouseHasWareho" = sk."WarehouseHasWarehouseId" LOOP
						--no production of this output
						IF outputquantities[i] = 0 THEN
						
							--no output value is recorded
							
							--its weight must be redistributed among the other ones
							weighttoredistribute := weighttoredistribute + weights[i];
						ELSE
							-- replaced by next line outputvalue := outputquantities[i] * outputsTotalValue * weights[i] / 100;	
							outputvalue := outputsTotalValue * weights[i] / 100;	

							INSERT INTO soberano."ProcessRunOutputValue"("Value", 
																		 "ProcessRunHasProcessRunId", 
																		 "InventoryItemHasInventoryItemCode", 
																		 "This_is_in_Currency_with_CurrencyHasCurrencyId")
								SELECT outputvalue, 
										processrunid, 
										itemRecord.item,
										"CurrencyHasCurrencyId"
									FROM soberano."Currency" WHERE "Currency_is_system_currency";
									
							producedoutputcount := producedoutputcount + 1;
						END IF;
						
						UPDATE soberano."ProcessRunOutput"
							SET "Quantity" = outputquantities[i], 
								"This_value_is_weighted_by_WeightCoefficient" = weights[i]
							WHERE "ProcessRunHasProcessRunId" = processrunid
								AND "InventoryItemHasInventoryItemCode" = itemRecord.item;
								
						i := i + 1;
					END LOOP;
					
					--only outputs with quantity > 0 have rows in soberano."ProcessRunOutputValue"
					UPDATE soberano."ProcessRunOutputValue" prov
						--replaced by next line SET "Value" = "Value" + pro."Quantity" * outputsTotalValue * weighttoredistribute / producedoutputcount / 100
						SET "Value" = "Value" + outputsTotalValue * weighttoredistribute / producedoutputcount / 100
						FROM soberano."ProcessRunOutput" pro
						WHERE prov."ProcessRunHasProcessRunId" = processrunid
							AND prov."ProcessRunHasProcessRunId" = pro."ProcessRunHasProcessRunId"
							AND prov."InventoryItemHasInventoryItemCode" = pro."InventoryItemHasInventoryItemCode";
						
					UPDATE soberano."ProcessRunFixedCost" prfc
						SET "FixedCost" = proc."This_has_FixedCost",
							"This_is_in_Currency_with_CurrencyHasCurrencyId" = curr."CurrencyHasCurrencyId"
						FROM soberano."ProcessRun" pr, soberano."Process" proc, soberano."Currency" curr
						WHERE prfc."ProcessRunHasProcessRunId" = processrunid
							AND prfc."ProcessRunHasProcessRunId" = pr."ProcessRunHasProcessRunId"
							AND pr."This_is_of_Process_with_ProcessHasProcessId" = proc."ProcessHasProcessId"
							AND curr."Currency_is_system_currency";
									
					--update stocks at output warehouse. only produced outputs, with values recorded
					PERFORM soberano."fn_Stock_change"(NULL,
														pro."InventoryItemHasInventoryItemCode",
														cc."This_increases_stock_in_OutputWarehouse_with_WarehouseHasWareho",
														pro."Quantity",
														prov."Value" / pro."Quantity",
													    NULL,
													  	NULL,
													  	true,
														processrunid,
													  	NULL)
						FROM soberano."ProcessRunOutput" pro
							INNER JOIN soberano."ProcessRunOutputValue" prov
								ON pro."ProcessRunHasProcessRunId" = prov."ProcessRunHasProcessRunId"
									AND pro."InventoryItemHasInventoryItemCode" = prov."InventoryItemHasInventoryItemCode"
							INNER JOIN soberano."ProcessRun" pr
								ON pro."ProcessRunHasProcessRunId" = pr."ProcessRunHasProcessRunId"
									AND pr."ProcessRunHasProcessRunId" = processrunid
							INNER JOIN soberano."CostCenter" cc
								ON pr."This_is_executed_in_CostCenter_with_CostCenterHasCostCenterId" = cc."CostCenterHasCostCenterId";
					
					--clear process run output allocations
					DELETE FROM soberano."ProcessRunOutputAllocation"
						WHERE "ProcessRunOutput_with_ProcessRunHasProcessRunId_is_allocated_by" = processrunid;
					
					--make the decision
					PERFORM metamodel."fn_Vote_vote"(loginname,
														entityTypeInstanceId, 
														decisionId, 
														'tt_ProcessRun_tt ' || CAST(processrunid AS text) || ' : ' || objectCode || chr(13) || ' tt_CLOSED_BY_tt ' || loginname);
										
					qryResult := 0;
				ELSE
					qryResult := -4;
				END IF;
			ELSE			
				qryResult := -2;	
			END IF;
		END IF;
		RETURN qryResult;
END;
$BODY$;