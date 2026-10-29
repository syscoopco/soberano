CREATE OR REPLACE FUNCTION soberano."fn_MaterialExpense_create"(
	material integer,
	unit integer,
	quantity numeric,
	provider integer,
	reference character varying,
	expensedate date,
	amount numeric,
	currency integer,
	usethiswarehouse integer,
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    SET default_transaction_isolation='serializable'
AS $BODY$
	DECLARE
		itemId integer;
		currentProcurementWarehouse integer;
	BEGIN

		--default returning value. user has no right.
		itemId := -1;

		SELECT "WarehouseHasWarehouseId"
				FROM soberano."Warehouse"
				WHERE "Warehouse_is_procurement_warehouse" = true 
				INTO currentProcurementWarehouse;

		--if a valid warehouse is passed, use it as the procurement warehouse only for this expense
		IF EXISTS(SELECT * FROM soberano."Warehouse" WHERE "WarehouseHasWarehouseId" = usethiswarehouse) THEN

			UPDATE soberano."Warehouse" SET "Warehouse_is_procurement_warehouse" = false;
			
			UPDATE soberano."Warehouse" SET "Warehouse_is_procurement_warehouse" = true
				WHERE "WarehouseHasWarehouseId" = usethiswarehouse;
			
		END IF;
		
		SELECT soberano."fn_MaterialExpense_create"(material,
													unit,
													quantity,
													provider,
													reference,
													expensedate,
													amount,
													currency,
													loginname) INTO itemId;

		UPDATE soberano."Warehouse" SET "Warehouse_is_procurement_warehouse" = false;

		UPDATE soberano."Warehouse" SET "Warehouse_is_procurement_warehouse" = true
			WHERE "WarehouseHasWarehouseId" = currentProcurementWarehouse;
	
		RETURN itemId;
END;
$BODY$;