CREATE OR REPLACE FUNCTION soberano."fn_Business_forceStockAdjusment"(
	warehouseid integer,
	inventoryitemcode character varying,
	inventoryitemvalue numeric,
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	DECLARE
		qryResult integer;
	BEGIN
		--default returning value. user has no right.
		qryResult := -1;		
		
		--it's an owner
		IF 'Owner' IN (SELECT * from metamodel."fn_User_getResponsibilities"(loginname)) THEN

			UPDATE soberano."Stock"
				SET "This_has_Value" = inventoryItemValue
				WHERE "WarehouseHasWarehouseId" = warehouseId
					AND "InventoryItemHasInventoryItemCode" = inventoryItemCode;

			qryResult := 0;
		
		ELSE
			qryResult := -1;
		END IF;
		RETURN qryResult;
END;
$BODY$;