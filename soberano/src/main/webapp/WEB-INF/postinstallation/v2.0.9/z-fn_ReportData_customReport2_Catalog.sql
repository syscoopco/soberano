CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport2_Catalog"(
	lang character,
	loginname character varying)
    RETURNS TABLE("categoryName" character varying, "productName" character varying, "productPrice" numeric) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 100

AS $BODY$
DECLARE
	
BEGIN
	--the user has rights to record a closure,
	IF ('Manager' IN (SELECT * from metamodel."fn_User_getResponsibilities"(loginname)))
	
		--or make decisions on an existing closure
		OR EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(20, 1, loginname)) THEN

			RETURN QUERY SELECT cat."This_has_Name",
								iitem."This_has_Name",
								"This_has_Price"
							FROM soberano."Product" prod
								INNER JOIN soberano."InventoryItem" iitem
									ON prod."InventoryItemHasInventoryItemCode" = iitem."InventoryItemHasInventoryItemCode"
								INNER JOIN soberano."ProductIsOfProductCategory" pcat
									ON pcat."ProductHasProductId" = prod."ProductHasProductId"
								INNER JOIN soberano."ProductCategory" cat
									ON pcat."ProductCategoryHasProductCategoryId" = cat."ProductCategoryHasProductCategoryId"
								INNER JOIN metamodel."EntityTypeInstance" prodeti
									ON prodeti."EntityTypeInstanceHasEntityTypeInstanceId" = prod."This_is_identified_by_EntityTypeInstance_id"
										--solo artículos habilitados
										AND prodeti."This_is_in_Stage_with_StageHasStageId" = 2
							WHERE cat."This_has_Name" != 'z-Subprocesos'
							ORDER BY cat."This_has_Name" ASC,
										iitem."This_has_Name" ASC;									
	ELSE
		RETURN QUERY SELECT ''::character varying,
							''::character varying,
							0.0::numeric;
	END IF;
END;
$BODY$;