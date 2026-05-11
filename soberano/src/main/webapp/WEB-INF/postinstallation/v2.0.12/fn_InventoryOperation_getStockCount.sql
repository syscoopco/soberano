CREATE OR REPLACE FUNCTION soberano."fn_InventoryOperation_getStockCount"(
	warehouseid integer,
	acquirablematerialid integer,
	amnamefilterstr text,
	lang character,
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE
	count integer;
BEGIN
	SELECT COUNT(*) FROM soberano."fn_InventoryOperation_getStock"(warehouseId, acquirableMaterialId, amNameFilterStr, lang, loginname) INTO count;
	RETURN count;
END;
$BODY$;