CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport1_detailedConsumption"(
	lang character,
	fromd date,
	untild date,
	ccname character varying,
	loginname character varying)
    RETURNS TABLE(stockchangedate date, "iCode" character varying, "iCostCenterName" character varying, "iName" text, "iUnit" character varying, "iQty" numeric, "iAmount" numeric, days integer, "processName" character varying) 
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
	IF ('Manager' IN (SELECT * from metamodel."fn_User_getResponsibilities"(loginname)))
	
		--or make decisions on an existing closure
		OR EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(20, 1, loginname)) THEN

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

			--calc the shifts within which stock changes were recorded
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
			
			RETURN QUERY SELECT shift,
								iCode,
								ccn,
								iName,
								unit,
								SUM(quantity),
								SUM(costc),
								shift - fromd,
								process
							FROM (SELECT shift,
										iCode,
										cc."This_has_Name" ccn,
										itemName::text iName,
										unit,
										quantity,
										costc,
										process
									FROM (SELECT DISTINCT stockItem."itemName" /*|| ' : ' || stockItem."itemCode"*/ itemName,
												stockItem."itemCode" iCode,
												stockItem."Acronym" unit,
												COALESCE(outputs.oQty, 0) * -1 quantity,
												"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" warehouseId,
												oValue costc,
												stockChangeDateTime,
												stockChangeId,
												shift,
												process
												FROM (SELECT inventoryItem."This_has_Name" "itemName",
															inventoryItem."InventoryItemHasInventoryItemCode" "itemCode",
															"Acronym"
														FROM (SELECT "InventoryItemHasInventoryItemCode",
																	"This_is_identified_by_EntityTypeInstance_id"
																FROM soberano."AcquirableMaterial" amat) item
															INNER JOIN metamodel."EntityTypeInstance" eti
																ON item."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId"
																	--only not disabled items
																	AND eti."This_is_in_Stage_with_StageHasStageId" != 8
															INNER JOIN soberano."InventoryItem" inventoryItem
																ON inventoryItem."InventoryItemHasInventoryItemCode" = item."InventoryItemHasInventoryItemCode"
															INNER JOIN soberano."UnitHasAcronymInLanguage" unit
																ON unit."UnitHasUnitId" = inventoryItem."This_is_measured_in_Unit_with_UnitHasUnitId"
																	AND unit."Language" = lang
														) stockItem
													INNER JOIN (SELECT shift,
																		SUM("This_is_called_with_Quantity") oQty,
																		"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" oItem,
																		"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
																		SUM("This_is_called_with_Value") oValue,
																		sc."This_is_at_DateTime" stockChangeDateTime,
																		sc."StockChangeHasStockChangeId" stockChangeId,
																		process."This_has_Name" process
																	FROM soberano."StockChange" sc
																		INNER JOIN shiftDateTimes
																			ON shiftDateTimes.datetime = sc."This_is_at_DateTime"
																		INNER JOIN soberano."ProcessRun" pr
																			ON pr."ProcessRunHasProcessRunId" = sc."This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId"
																		INNER JOIN soberano."Process" process
																			ON process."ProcessHasProcessId" = pr."This_is_of_Process_with_ProcessHasProcessId"
																		INNER JOIN soberano."OrderProcessRun" opr
																			ON opr."ProcessRunHasProcessRunId" = pr."ProcessRunHasProcessRunId"
																	WHERE "This_is_called_with_Quantity" < 0
																		AND sc."This_is_at_DateTime" > pTime
																		AND sc."This_is_at_DateTime" <= cTime											
																	GROUP BY "This_is_called_with_InventoryItem_with_InventoryItemHasInventor",
																		"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
																		sc."This_is_at_DateTime",
																		sc."StockChangeHasStockChangeId",
																		shift,
																		process."This_has_Name") outputs
														ON stockItem."itemCode" = outputs.oItem) sq
									INNER JOIN soberano."CostCenter" cc
										ON cc."This_consumes_materials_from_InputWarehouse_with_WarehouseHasWa" = sq.warehouseId
											AND (cc."This_has_Name" = ccname OR ccname = '' OR ccname IS NULL)) sq
							GROUP BY shift,
									iCode,
									ccn,
									iName,
									unit,
									process
							ORDER BY ccn ASC,
									process,
									iName ASC,
									shift ASC;
	END IF;
END;
$BODY$;