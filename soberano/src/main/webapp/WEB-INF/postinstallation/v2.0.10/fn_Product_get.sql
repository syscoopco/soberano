CREATE OR REPLACE FUNCTION soberano."fn_Product_get"(
	productid character varying,
	loginname character varying)
    RETURNS TABLE("itemId" integer, "entityTypeInstanceId" integer, "itemCode" character varying, "itemName" character varying, "inventoryLevel" numeric, "itemUnit" integer, "itemPrice" numeric, "itemReferencePrice" numeric, "costCenter" integer, "isEnabled" boolean, "itemProcess" integer, "categoryId" integer, "categoryName" character varying, "productPosition" integer, "productIsAddition" boolean, "productPicture" bytea) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1

AS $BODY$
	BEGIN
		RETURN QUERY SELECT DISTINCT am."ProductHasProductId",
										am."This_is_identified_by_EntityTypeInstance_id",
										ii."InventoryItemHasInventoryItemCode",
										ii."This_has_Name",
										"This_has_MinimumInventoryLevel",
										"This_is_measured_in_Unit_with_UnitHasUnitId",
										"This_has_Price",
										"This_has_Price_in_reference_currency",
										"This_is_usually_produced_in_CostCenter_with_CostCenterHasCostCe",
										"Product_is_enabled",
										"ProcessHasProcessId",
										cat."ProductCategoryHasProductCategoryId",
										cat."This_has_Name",
										am."This_is_shown_in_Position",
										COALESCE(am."Product_is_an_addition", false),
										am."This_has_Picture"
								FROM soberano."Product" am
									INNER JOIN soberano."InventoryItem" ii
										ON ii."InventoryItemHasInventoryItemCode" = am."InventoryItemHasInventoryItemCode"
											AND ii."InventoryItemHasInventoryItemCode" = productid
									LEFT JOIN soberano."ProductIsOfProductCategory" piocat
										ON am."ProductHasProductId" = piocat."ProductHasProductId"
									LEFT JOIN soberano."ProductCategory" cat
										ON piocat."ProductCategoryHasProductCategoryId" = cat."ProductCategoryHasProductCategoryId"
									LEFT JOIN (SELECT product."InventoryItemHasInventoryItemCode", 
											   		"ProcessHasProcessId"
													FROM soberano."ProcessOutput" poutput
														INNER JOIN soberano."Product" product
															ON poutput."InventoryItemHasInventoryItemCode" = product."InventoryItemHasInventoryItemCode"
												WHERE product."InventoryItemHasInventoryItemCode" = productid
											   ORDER BY "ProcessHasProcessId") process
										ON ii."InventoryItemHasInventoryItemCode" = process."InventoryItemHasInventoryItemCode"
											AND process."InventoryItemHasInventoryItemCode" = am."InventoryItemHasInventoryItemCode"
								WHERE EXISTS (SELECT metamodel."fn_EntityTypeInstance_getDecisions"(am."This_is_identified_by_EntityTypeInstance_id", loginname));
	END;	
$BODY$;