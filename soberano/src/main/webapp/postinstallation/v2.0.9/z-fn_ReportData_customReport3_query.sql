CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport3_query"(
	lang character,
	fromd date,
	untild date,
	ccname character varying,
	loginname character varying)
    RETURNS TABLE("iName" character varying, "unitCost" numeric, "iPrice" numeric, "soldQuantity" numeric, opening numeric, inputs numeric, outputs numeric, movements numeric, losses numeric, ending numeric) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 100

AS $BODY$
DECLARE
	pTime timestamp with time zone;
	cTime timestamp with time zone;
	fromClosureId integer;
	untilClosureId integer;
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

			DROP TABLE IF EXISTS shiftDateTimes;
			CREATE TEMPORARY TABLE shiftDateTimes(shift date, datetime timestamp with time zone);

			--calc the shifts within which orders were closed
			INSERT INTO shiftDateTimes 
				SELECT DISTINCT soberano."fn_ShiftClosure_getShiftFromDateTime"(orderCollectingVotet."This_is_on_Timestamp") oDate,
								orderCollectingVotet."This_is_on_Timestamp"
					FROM soberano."Order" ord
						INNER JOIN metamodel."EntityTypeInstance" eti
							ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = ord."This_is_identified_by_EntityTypeInstance_id"
									
								--only closed orders
								AND eti."This_is_in_Stage_with_StageHasStageId" = 6
						INNER JOIN metamodel."Vote" orderCollectingVotet
							ON ord."This_is_identified_by_EntityTypeInstance_id" = orderCollectingVotet."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"
			
								--orders closed (collected) within the passed time interval
								AND orderCollectingVotet."This_favors_Decision_with_DecisionHasDecisionId" = 3005
								AND orderCollectingVotet."This_is_on_Timestamp" > pTime
								AND orderCollectingVotet."This_is_on_Timestamp" <= cTime;

			RETURN QUERY (SELECT inamee, unitcostt, pricee, sales, openingg, COALESCE(iQty, 0::numeric), oQtyy, mQtyy, lQtyy, endingg FROM (
									SELECT iitemo."This_has_Name" inamee,
														SUM(priv."Value") / SUM(pro."Quantity") unitcostt,
														"Price" pricee,
														SUM(pro."Quantity") sales,
														stockfrom.opening openingg, 
														COALESCE(outputs.oQty, 0.0::numeric) oQtyy,
														COALESCE(movements.mQty, 0.0::numeric) mQtyy,
														COALESCE(losses.lQty, 0.0::numeric) lQtyy,
														stockuntil.ending endingg,
														am."InventoryItemHasInventoryItemCode",
														cat."This_is_shown_in_Position" pos
													FROM soberano."ProcessRunOutputHasPriceForOrder" prohpfo
														INNER JOIN soberano."InventoryItem" iitemo
															ON iitemo."InventoryItemHasInventoryItemCode" = prohpfo."InventoryItemHasInventoryItemCode"
														INNER JOIN soberano."Product" prod
															ON prod."InventoryItemHasInventoryItemCode" = iitemo."InventoryItemHasInventoryItemCode"
														INNER JOIN soberano."ProductIsOfProductCategory" pcat
															ON pcat."ProductHasProductId" = prod."ProductHasProductId"
														INNER JOIN soberano."ProductCategory" cat
															ON cat."ProductCategoryHasProductCategoryId" = pcat."ProductCategoryHasProductCategoryId"
														INNER JOIN soberano."ProcessRunOutputValue" prov
															ON prov."InventoryItemHasInventoryItemCode" = iitemo."InventoryItemHasInventoryItemCode"
																AND prov."ProcessRunHasProcessRunId" = prohpfo."ProcessRunHasProcessRunId"				
														INNER JOIN soberano."ProcessRunOutput" pro
															ON pro."InventoryItemHasInventoryItemCode" = prov."InventoryItemHasInventoryItemCode"
																AND pro."ProcessRunHasProcessRunId" = prov."ProcessRunHasProcessRunId"
														INNER JOIN soberano."ProcessRun" pr
															ON pr."ProcessRunHasProcessRunId" = pro."ProcessRunHasProcessRunId"
														INNER JOIN soberano."CostCenter" cc
															ON cc."CostCenterHasCostCenterId" = pr."This_is_executed_in_CostCenter_with_CostCenterHasCostCenterId"
														INNER JOIN soberano."ProcessRunInput" pri
															ON pr."ProcessRunHasProcessRunId" = pri."ProcessRunHasProcessRunId"
														INNER JOIN soberano."InventoryItem" iitemi
															ON iitemi."InventoryItemHasInventoryItemCode" = pri."InventoryItemHasInventoryItemCode"
														INNER JOIN soberano."ProcessRunInputValue" priv
															ON priv."InventoryItemHasInventoryItemCode" = iitemi."InventoryItemHasInventoryItemCode"
																AND priv."ProcessRunHasProcessRunId" = prohpfo."ProcessRunHasProcessRunId"	
														INNER JOIN soberano."OrderProcessRun" opr
															ON opr."OrderHasOrderId" = prohpfo."OrderHasOrderId"
																AND opr."ProcessRunHasProcessRunId" = prohpfo."ProcessRunHasProcessRunId"
																	AND opr."This_has_Description" != 'subprocess'
														INNER JOIN soberano."Order" ord
															ON ord."OrderHasOrderId" = opr."OrderHasOrderId"
														INNER JOIN metamodel."EntityTypeInstance" eti
															ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = ord."This_is_identified_by_EntityTypeInstance_id"

																--only closed orders
																AND eti."This_is_in_Stage_with_StageHasStageId" = 6
														INNER JOIN metamodel."Vote" orderCollectingVotet
															ON ord."This_is_identified_by_EntityTypeInstance_id" = orderCollectingVotet."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"

																--orders closed (collected) within the passed time interval
																AND orderCollectingVotet."This_favors_Decision_with_DecisionHasDecisionId" = 3005
																AND orderCollectingVotet."This_is_on_Timestamp" > pTime
																AND orderCollectingVotet."This_is_on_Timestamp" <= cTime
														INNER JOIN shiftDateTimes
															ON shiftDateTimes.datetime = orderCollectingVotet."This_is_on_Timestamp"
														INNER JOIN soberano."AcquirableMaterial" am
															ON am."InventoryItemHasInventoryItemCode" = iitemi."InventoryItemHasInventoryItemCode"

														--param value 1001 is the id of the warehouse from which the cost center consumes materials
														INNER JOIN (SELECT * from soberano."fn_InventoryOperation_getSPI"(to_char(fromd, 'YYYY/MM/DD'), 1001, 0, lang, loginname)) stockfrom
															ON stockfrom."acquirableMaterialId" = am."AcquirableMaterialHasAcquirableMaterialId"
														INNER JOIN (SELECT * from soberano."fn_InventoryOperation_getSPI"(to_char(untild, 'YYYY/MM/DD'), 1001, 0, lang, loginname)) stockuntil
															ON stockuntil."acquirableMaterialId" = am."AcquirableMaterialHasAcquirableMaterialId"
														LEFT JOIN (SELECT SUM("This_is_called_with_Quantity") oQty,
																			"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" oItem
																	FROM soberano."StockChange"
																	WHERE "This_is_called_with_Quantity" < 0
																		AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" = 1001
																		AND "This_is_at_DateTime" > pTime
																		AND "This_is_at_DateTime" <= cTime
																		AND "This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId" IS NOT NULL
																	GROUP BY "This_is_called_with_InventoryItem_with_InventoryItemHasInventor") outputs
															ON outputs.oItem = am."InventoryItemHasInventoryItemCode"
														LEFT JOIN (SELECT SUM("This_is_called_with_Quantity") mQty,
																				"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" mItem
																		FROM soberano."StockChange" sc
																			INNER JOIN soberano."Warehouse" wh
																				ON wh."WarehouseHasWarehouseId" = sc."This_is_called_with_Warehouse_with_WarehouseHasWarehouseId"
																			INNER JOIN soberano."InventoryOperation" io
																				ON io."InventoryOperationHasInventoryOperationId" = sc."This_is_of_InventoryOperation_with_InventoryOperationHasInvento"
																					AND io."This_moves_items_to_Warehouse_with_WarehouseHasWarehouseId"
																						IN (SELECT "WarehouseHasWarehouseId" 
																									FROM soberano."Warehouse" 
																									WHERE NOT "Warehouse_is_losses_warehouse")
																		WHERE "This_is_called_with_Quantity" < 0
																			AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" = 1001
																			AND "This_is_at_DateTime" > pTime
																			AND "This_is_at_DateTime" <= cTime
																			AND "This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId" IS NULL
																		GROUP BY "This_is_called_with_InventoryItem_with_InventoryItemHasInventor") movements
															ON movements.mItem = am."InventoryItemHasInventoryItemCode"
														LEFT JOIN (SELECT SUM("This_is_called_with_Quantity") lQty,
																			"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" lItem
																	FROM soberano."StockChange" sc
																		INNER JOIN soberano."Warehouse" wh
																			ON wh."WarehouseHasWarehouseId" = sc."This_is_called_with_Warehouse_with_WarehouseHasWarehouseId"
																		INNER JOIN soberano."InventoryOperation" io
																			ON io."InventoryOperationHasInventoryOperationId" = sc."This_is_of_InventoryOperation_with_InventoryOperationHasInvento"
																				AND io."This_moves_items_to_Warehouse_with_WarehouseHasWarehouseId"
																					IN (SELECT "WarehouseHasWarehouseId" 
																								FROM soberano."Warehouse" 
																								WHERE "Warehouse_is_losses_warehouse")
																	WHERE "This_is_called_with_Quantity" < 0
																		AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" = 1001
																		AND "This_is_at_DateTime" > pTime
																		AND "This_is_at_DateTime" <= cTime
																		AND "This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId" IS NULL
																	GROUP BY "This_is_called_with_InventoryItem_with_InventoryItemHasInventor") losses
															ON losses.lItem = am."InventoryItemHasInventoryItemCode"
													GROUP BY iitemo."This_has_Name",
															"Price",
															cat."This_is_shown_in_Position",
															stockfrom.opening, 
															outputs.oQty,
															movements.mQty,
															losses.lQty,
															stockuntil.ending,
															am."InventoryItemHasInventoryItemCode") sqq
													LEFT JOIN (SELECT SUM("This_is_called_with_Quantity") iQty,
																		"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" iItem
																	FROM soberano."StockChange"
																	WHERE "This_is_called_with_Quantity" > 0
																		AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" = 1001
																		AND "This_is_at_DateTime" > pTime
																		AND "This_is_at_DateTime" <= cTime
										  							GROUP BY "This_is_called_with_InventoryItem_with_InventoryItemHasInventor") inputs
														ON inputs.iITem = "InventoryItemHasInventoryItemCode"
													ORDER BY inamee ASC)
						
						UNION
						
						SELECT inamee, 0.0::numeric, 0.0::numeric, 0.0::numeric, openingg, COALESCE(iQty, 0::numeric), oQtyy, mQtyy, lQtyy, endingg FROM (
							SELECT iitemo."This_has_Name" inamee,
												0.0::numeric,
												0.0::numeric,
												0.0::numeric,
												stockfrom.opening openingg, 
												COALESCE(outputs.oQty, 0.0::numeric) oQtyy,
												COALESCE(movements.mQty, 0.0::numeric) mQtyy,
												COALESCE(losses.lQty, 0.0::numeric) lQtyy,
												stockuntil.ending endingg,
												am."InventoryItemHasInventoryItemCode"
											FROM soberano."AcquirableMaterial" am
												INNER JOIN soberano."InventoryItem" iitemo
													ON am."InventoryItemHasInventoryItemCode" = iitemo."InventoryItemHasInventoryItemCode" 
												
												--param value 1001 is the id of the warehouse from which the cost center consumes materials
												INNER JOIN (SELECT * from soberano."fn_InventoryOperation_getSPI"(to_char(fromd, 'YYYY/MM/DD'), 1001, 0, lang, loginname)) stockfrom
													ON stockfrom."acquirableMaterialId" = am."AcquirableMaterialHasAcquirableMaterialId"
												INNER JOIN (SELECT * from soberano."fn_InventoryOperation_getSPI"(to_char(untild, 'YYYY/MM/DD'), 1001, 0, lang, loginname)) stockuntil
													ON stockuntil."acquirableMaterialId" = am."AcquirableMaterialHasAcquirableMaterialId"
												LEFT JOIN (SELECT SUM("This_is_called_with_Quantity") oQty,
																	"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" oItem
															FROM soberano."StockChange"
															WHERE "This_is_called_with_Quantity" < 0
																AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" = 1001
																AND "This_is_at_DateTime" > pTime
																AND "This_is_at_DateTime" <= cTime
																AND "This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId" IS NOT NULL
															GROUP BY "This_is_called_with_InventoryItem_with_InventoryItemHasInventor") outputs
													ON outputs.oItem = am."InventoryItemHasInventoryItemCode"
												LEFT JOIN (SELECT SUM("This_is_called_with_Quantity") mQty,
																		"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" mItem
																FROM soberano."StockChange" sc
																	INNER JOIN soberano."Warehouse" wh
																		ON wh."WarehouseHasWarehouseId" = sc."This_is_called_with_Warehouse_with_WarehouseHasWarehouseId"
																	INNER JOIN soberano."InventoryOperation" io
																		ON io."InventoryOperationHasInventoryOperationId" = sc."This_is_of_InventoryOperation_with_InventoryOperationHasInvento"
																			AND io."This_moves_items_to_Warehouse_with_WarehouseHasWarehouseId"
																				IN (SELECT "WarehouseHasWarehouseId" 
																							FROM soberano."Warehouse" 
																							WHERE NOT "Warehouse_is_losses_warehouse")
																WHERE "This_is_called_with_Quantity" < 0
																	AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" = 1001
																	AND "This_is_at_DateTime" > pTime
																	AND "This_is_at_DateTime" <= cTime
																	AND "This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId" IS NULL
																GROUP BY "This_is_called_with_InventoryItem_with_InventoryItemHasInventor") movements
													ON movements.mItem = am."InventoryItemHasInventoryItemCode"
												LEFT JOIN (SELECT SUM("This_is_called_with_Quantity") lQty,
																	"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" lItem
															FROM soberano."StockChange" sc
																INNER JOIN soberano."Warehouse" wh
																	ON wh."WarehouseHasWarehouseId" = sc."This_is_called_with_Warehouse_with_WarehouseHasWarehouseId"
																INNER JOIN soberano."InventoryOperation" io
																	ON io."InventoryOperationHasInventoryOperationId" = sc."This_is_of_InventoryOperation_with_InventoryOperationHasInvento"
																		AND io."This_moves_items_to_Warehouse_with_WarehouseHasWarehouseId"
																			IN (SELECT "WarehouseHasWarehouseId" 
																						FROM soberano."Warehouse" 
																						WHERE "Warehouse_is_losses_warehouse")
															WHERE "This_is_called_with_Quantity" < 0
																AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" = 1001
																AND "This_is_at_DateTime" > pTime
																AND "This_is_at_DateTime" <= cTime
																AND "This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId" IS NULL
															GROUP BY "This_is_called_with_InventoryItem_with_InventoryItemHasInventor") losses
													ON losses.lItem = am."InventoryItemHasInventoryItemCode"
											GROUP BY iitemo."This_has_Name",
													stockfrom.opening, 
													outputs.oQty,
													movements.mQty,
													losses.lQty,
													stockuntil.ending,
													am."InventoryItemHasInventoryItemCode") sqq
											LEFT JOIN (SELECT SUM("This_is_called_with_Quantity") iQty,
																"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" iItem
															FROM soberano."StockChange"
															WHERE "This_is_called_with_Quantity" > 0
																AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" = 1001
																AND "This_is_at_DateTime" > pTime
																AND "This_is_at_DateTime" <= cTime
															GROUP BY "This_is_called_with_InventoryItem_with_InventoryItemHasInventor") inputs
												ON inputs.iITem = "InventoryItemHasInventoryItemCode"
											WHERE oQtyy = 0
												AND (iQty != 0 OR mQtyy != 0 OR lQtyy != 0 OR endingg != 0)
											ORDER BY inamee ASC;				
	ELSE
		RETURN QUERY SELECT ''::character varying,
							0.0::numeric, 
							0.0::numeric,
							0.0::numeric,
							0.0::numeric,
							0.0::numeric,
							0.0::numeric,
							0.0::numeric,
							0.0::numeric,
							0.0::numeric;
	END IF;
END;
$BODY$;