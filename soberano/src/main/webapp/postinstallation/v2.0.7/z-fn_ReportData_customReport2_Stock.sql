CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport2_Stock"(
	lang character,
	warehouse character varying,
	loginname character varying)
    RETURNS TABLE(whouse character varying, "iName" character varying, "iQty" numeric, "iUnitValue" numeric, "iTotalValue" numeric) 
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

			RETURN QUERY SELECT wh."This_has_Name",
								iitem."This_has_Name",
								"Quantity",
								"This_has_Value",
								"Quantity" * "This_has_Value"
							FROM soberano."Stock" stock
								INNER JOIN soberano."InventoryItem" iitem
									ON stock."InventoryItemHasInventoryItemCode" = iitem."InventoryItemHasInventoryItemCode"
								INNER JOIN soberano."Warehouse" wh
									ON wh."WarehouseHasWarehouseId" = stock."WarehouseHasWarehouseId"
										AND (wh."This_has_Name" = warehouse OR warehouse = '' OR warehouse IS NULL) 
								INNER JOIN soberano."AcquirableMaterial" prod
									ON stock."InventoryItemHasInventoryItemCode" = prod."InventoryItemHasInventoryItemCode"
								INNER JOIN metamodel."EntityTypeInstance" prodeti
									ON prodeti."EntityTypeInstanceHasEntityTypeInstanceId" = prod."This_is_identified_by_EntityTypeInstance_id"
										--solo artículos habilitados
										AND prodeti."This_is_in_Stage_with_StageHasStageId" = 2
							ORDER BY  wh."This_has_Name" ASC,
										iitem."This_has_Name" ASC;									
	ELSE
		RETURN QUERY SELECT ''::character varying,
							''::character varying,
							0.0::numeric,
							0.0::numeric,
							0.0::numeric;
	END IF;
END;
$BODY$;