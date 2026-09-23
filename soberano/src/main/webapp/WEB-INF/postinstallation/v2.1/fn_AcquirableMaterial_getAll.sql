CREATE OR REPLACE FUNCTION soberano."fn_AcquirableMaterial_getAll"(
	typed text,
	nrows integer,
	loginname character varying)
    RETURNS TABLE("domainObjectId" integer, "domainObjectName" text) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 100

AS $BODY$
	BEGIN
		RETURN QUERY SELECT *
						FROM (SELECT DISTINCT "AcquirableMaterialHasAcquirableMaterialId", 
									"This_has_Name" || ' : ' || am."InventoryItemHasInventoryItemCode" "domainObjectName"
									FROM soberano."AcquirableMaterial" am
							 			INNER JOIN soberano."InventoryItem" ii
											ON am."InventoryItemHasInventoryItemCode" = ii."InventoryItemHasInventoryItemCode") sq
						WHERE LOWER(sq."domainObjectName") LIKE '%' || LOWER(typed) || '%'
						ORDER BY sq."domainObjectName" ASC
						LIMIT nrows;
	END;	
$BODY$;