CREATE OR REPLACE FUNCTION soberano."fn_Product_getAllWithStringIdForOrder"(
	typed text,
	nrows integer,
	loginname character varying)
    RETURNS TABLE("domainObjectId" integer, "domainObjectStringId" character varying, "domainObjectName" text, unit integer, "oneRunQuantity" numeric) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 100

AS $BODY$
	BEGIN
		RETURN QUERY SELECT *
						FROM (SELECT DISTINCT am."ProductHasProductId",
							  		am."InventoryItemHasInventoryItemCode", 
									"This_has_Name" || ' : ' || am."InventoryItemHasInventoryItemCode" "domainObjectName",
							  		"This_is_measured_in_Unit_with_UnitHasUnitId",
							  		"Quantity"
									FROM soberano."Product" am							  					
							 			INNER JOIN soberano."InventoryItem" ii
											ON am."InventoryItemHasInventoryItemCode" = ii."InventoryItemHasInventoryItemCode"

											 --the product must be shown (enabled) in catalog
											AND am."Product_is_enabled"
						  
											--the product isn't an addition
											AND (am."Product_is_an_addition" IS NULL OR NOT am."Product_is_an_addition")
										 
										 LEFT JOIN (	--only one of the processes that produces the item is returned
			  										--the last one configured
													SELECT MAX("ProcessHasProcessId") proid, 
												   			pro."InventoryItemHasInventoryItemCode"
												   		FROM soberano."Product" pro
														INNER JOIN soberano."ProcessOutput" pout
															ON pro."InventoryItemHasInventoryItemCode" = pout."InventoryItemHasInventoryItemCode"
														GROUP BY pro."InventoryItemHasInventoryItemCode") pout
											ON ii."InventoryItemHasInventoryItemCode" = pout."InventoryItemHasInventoryItemCode"
										LEFT JOIN soberano."ProcessOutput" pout1
											ON pout."InventoryItemHasInventoryItemCode" = pout1."InventoryItemHasInventoryItemCode"
												AND pout."proid" = pout1."ProcessHasProcessId") sq
						WHERE LOWER(sq."domainObjectName") LIKE '%' || LOWER(typed) || '%'
						ORDER BY "domainObjectName" ASC
						LIMIT nrows;
	END;	
$BODY$;