CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport3_stock"(
	fromd date,
	untild date,
	loginname character varying)
    RETURNS TABLE("warehouseName" character varying, "inventoryItemName" character varying, 
	"openingQuantity" numeric, "openingUnitCost" numeric, "endingQuantity" numeric, "endingUnitCost" numeric) 
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

		RETURN QUERY SELECT warehouse."This_has_Name"::character varying,
							iitem."This_has_Name"::character varying,

							COALESCE(opening."This_saves_previous_Quantity" + opening."This_is_called_with_Quantity", 0),
							COALESCE(CASE WHEN opening."This_saves_previous_Quantity" + opening."This_is_called_with_Quantity" <= 0 THEN 0
													WHEN opening."This_is_called_with_Quantity" <= 0 THEN opening."This_saves_previous_Value"
													WHEN opening."This_saves_previous_Quantity" < 0 THEN opening."This_is_called_with_Value"
													ELSE (opening."This_saves_previous_Quantity" * opening."This_saves_previous_Value" + opening."This_is_called_with_Quantity" * opening."This_is_called_with_Value") / (opening."This_saves_previous_Quantity" + opening."This_is_called_with_Quantity") END, 0),
							
							COALESCE(ending."This_saves_previous_Quantity" + ending."This_is_called_with_Quantity", 0),
							COALESCE(CASE WHEN ending."This_saves_previous_Quantity" + ending."This_is_called_with_Quantity" <= 0 THEN 0
													WHEN ending."This_is_called_with_Quantity" <= 0 THEN ending."This_saves_previous_Value"
													WHEN ending."This_saves_previous_Quantity" < 0 THEN ending."This_is_called_with_Value"
													ELSE (ending."This_saves_previous_Quantity" * ending."This_saves_previous_Value" + ending."This_is_called_with_Quantity" * ending."This_is_called_with_Value") / (ending."This_saves_previous_Quantity" + ending."This_is_called_with_Quantity") END, 0)
			FROM (SELECT "This_saves_previous_Quantity",
						"This_is_called_with_Quantity",
						"This_saves_previous_Value",
						"This_is_called_with_Value",
						"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
						"This_is_called_with_InventoryItem_with_InventoryItemHasInventor"
						FROM (SELECT MIN(rownumber) rownumber, 
									"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
									"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" 
								FROM (SELECT  ROW_NUMBER () OVER (ORDER BY "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" ASC,
																	"This_is_at_DateTime" DESC, 
																	"StockChangeHasStockChangeId" DESC) rownumber,
												"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
												"This_is_called_with_InventoryItem_with_InventoryItemHasInventor"
											FROM soberano."StockChange"
											WHERE "This_is_at_DateTime" < cTime
																								
												--only procurement and cost center warehouses
												AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" IN (1001, 1002)) sc
											
											GROUP BY "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
													"This_is_called_with_InventoryItem_with_InventoryItemHasInventor") sq1
										INNER JOIN (SELECT * 
														FROM (SELECT  ROW_NUMBER () OVER (ORDER BY "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" ASC,
																								"This_is_at_DateTime" DESC, 
																								"StockChangeHasStockChangeId" DESC) rownumber,
																		"This_saves_previous_Quantity",
																		"This_is_called_with_Quantity",
																		"This_saves_previous_Value",
																		"This_is_called_with_Value"
																FROM soberano."StockChange"
																WHERE "This_is_at_DateTime" < cTime
																																		
																	--only procurement and cost center warehouses
																	AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" IN (1001, 1002)) sc) sq2
											ON sq1.rownumber = sq2.rownumber) ending
				INNER JOIN soberano."Warehouse" warehouse
					ON warehouse."WarehouseHasWarehouseId" = ending."This_is_called_with_Warehouse_with_WarehouseHasWarehouseId"	
				INNER JOIN soberano."InventoryItem" iitem
					ON iitem."InventoryItemHasInventoryItemCode" = ending."This_is_called_with_InventoryItem_with_InventoryItemHasInventor"
				INNER JOIN soberano."AcquirableMaterial" am
					ON am."InventoryItemHasInventoryItemCode" = iitem."InventoryItemHasInventoryItemCode"
				INNER JOIN metamodel."EntityTypeInstance" eti
					ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = am."This_is_identified_by_EntityTypeInstance_id"
						
						--only enabled acquirable materials
						AND eti."This_is_in_Stage_with_StageHasStageId" = 2

				LEFT JOIN (SELECT "This_saves_previous_Quantity",
								"This_is_called_with_Quantity",
								"This_saves_previous_Value",
								"This_is_called_with_Value",
								"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
								"This_is_called_with_InventoryItem_with_InventoryItemHasInventor"
								FROM (SELECT MIN(rownumber) rownumber, 
											"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
											"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" 
										FROM (SELECT  ROW_NUMBER () OVER (ORDER BY "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" ASC,
																			"This_is_at_DateTime" DESC, 
																			"StockChangeHasStockChangeId" DESC) rownumber,
														"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
														"This_is_called_with_InventoryItem_with_InventoryItemHasInventor"
													FROM soberano."StockChange"
													WHERE "This_is_at_DateTime" < pTime
																										
														--only procurement and cost center warehouses
														AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" IN (1001, 1002)) sc
													
													GROUP BY "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
															"This_is_called_with_InventoryItem_with_InventoryItemHasInventor") sq1
												INNER JOIN (SELECT * 
																FROM (SELECT  ROW_NUMBER () OVER (ORDER BY "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" ASC,
																										"This_is_at_DateTime" DESC, 
																										"StockChangeHasStockChangeId" DESC) rownumber,
																				"This_saves_previous_Quantity",
																				"This_is_called_with_Quantity",
																				"This_saves_previous_Value",
																				"This_is_called_with_Value"
																		FROM soberano."StockChange"
																		WHERE "This_is_at_DateTime" < pTime
																																				
																			--only procurement and cost center warehouses
																			AND "This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" IN (1001, 1002)) sc) sq2
													ON sq1.rownumber = sq2.rownumber) opening

					ON opening."This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" = ending."This_is_called_with_Warehouse_with_WarehouseHasWarehouseId"	
						AND opening."This_is_called_with_InventoryItem_with_InventoryItemHasInventor" = ending."This_is_called_with_InventoryItem_with_InventoryItemHasInventor"
						
				ORDER BY iitem."This_has_Name" ASC, warehouse."This_has_Name" ASC;
	ELSE
		RETURN QUERY SELECT ''::character varying,
							''::character varying,
							0.0::numeric,
							0.0::numeric,
							0.0::numeric,
							0.0::numeric;
	END IF;
END;
$BODY$;