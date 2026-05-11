CREATE OR REPLACE FUNCTION soberano."fn_InventoryOperation_getStock"(
	warehouseid integer,
	acquirablematerialid integer,
	amnamefilterstr text,
	lang character,
	loginname character varying)
    RETURNS TABLE("inventoryItemCode" character varying, "inventoryItemName" character varying, quantity numeric, unit character varying, "unitValue" numeric) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
	BEGIN
		RETURN QUERY SELECT *
						FROM (SELECT DISTINCT stock."InventoryItemHasInventoryItemCode",
							  				inventoryItem."This_has_Name",
							  				SUM(stock."Quantity"),
							  				unit."Acronym",
							  				CASE WHEN SUM(stock."Quantity") <= 0 THEN 0 
							  						ELSE ROUND(SUM(stock."Quantity" * stock."This_has_Value") / SUM(stock."Quantity"), 8) END
									FROM soberano."Stock" stock
							  			INNER JOIN soberano."InventoryItem" inventoryItem
							  				ON inventoryItem."InventoryItemHasInventoryItemCode" = stock."InventoryItemHasInventoryItemCode"
							  			INNER JOIN soberano."UnitHasAcronymInLanguage" unit
							  				ON unit."UnitHasUnitId" = inventoryItem."This_is_measured_in_Unit_with_UnitHasUnitId"
							  					AND unit."Language" = lang

										INNER JOIN soberano."AcquirableMaterial" amat
											ON amat."InventoryItemHasInventoryItemCode" = inventoryItem."InventoryItemHasInventoryItemCode"
												  
							  		WHERE --user can make decisions on inventory operations 
							  			(EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(21, 1, loginname))
											--or user can execute inventory operations
											OR 1 IN (SELECT * FROM metamodel."fn_User_canCreateInstance"(1, '_E439FAF1-C89D-4C86-A1F7-0A970074FA02', loginname)))
										AND (stock."WarehouseHasWarehouseId" = warehouseId OR warehouseId = 0)

										AND (amat."AcquirableMaterialHasAcquirableMaterialId" = acquirableMaterialId OR acquirableMaterialId = 0)
										AND (amNameFilterStr = '' OR (inventoryItem."This_has_Name" LIKE '%' || amNameFilterStr || '%'))
										
							 	GROUP BY stock."InventoryItemHasInventoryItemCode",
										inventoryItem."This_has_Name",
										unit."Acronym") sq
						ORDER BY "This_has_Name" ASC;
	END;	
$BODY$;