CREATE OR REPLACE FUNCTION soberano."fn_Stock_recalculateInventoryFrom"(
	startdatetime timestamp with time zone)
    RETURNS void
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	DECLARE
		stockChangeRecord record;	
		inputItemRecord record;	
		inputStockChangeRecord record;
		systemCurrencyId integer;

		currentQuantity numeric;
		stockValueInOriginWarehouseMustBeRestored boolean;
		
	BEGIN
		PERFORM soberano."fn_Stock_restoreToStateBefore"(startDateTime, "InventoryItemHasInventoryItemCode")
			FROM soberano."InventoryItem";
			
		SELECT "CurrencyHasCurrencyId" 
			FROM soberano."Currency"
			WHERE "Currency_is_system_currency"
			INTO systemCurrencyId;

		-- the value of an item in the origin warehouse of an inventory operation goes 
		-- to zero or below if the quantity to move is equal or higher than the current 
		-- quantity of that item in that warehouse. it is managed properly by the inventory 
		-- creation function. but, during recalculation (remaking that inventory operation's 
		-- stock changes), it is important to save that value before the next stock change 
		-- using the value from the origin (the field This_takes_value_from_Warehouse_...) 
		-- uses it to properly calculate the new item value in the destination warehouse, and 
		-- to restore it afteward. otherwise, the new value will always be zero.

		-- temporary table to store values before they fall to or under zero as part of the 
		-- recalculation process.
		CREATE TEMPORARY TABLE "stockValuesBeforeFallingToOrBelowZero"
		(
		    warehouse integer NOT NULL, 
			itemValue numeric NOT NULL, 
			item character varying(30) COLLATE pg_catalog."default" NOT NULL,
		    CONSTRAINT "PK_stockValuesBeforeFallingToOrBelowZero" PRIMARY KEY (warehouse, item)
		);
		
		--rerun the item's stock changes (inventory history) from start time
		FOR stockChangeRecord IN SELECT "StockChangeHasStockChangeId",
										"This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId" processRunId,
										"This_is_called_with_Quantity" qty,
										"This_is_called_with_InventoryItem_with_InventoryItemHasInventor" inventoryItem,
										"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId" warehouse
									FROM soberano."StockChange"
									WHERE "This_is_at_DateTime" >= startDateTime 
									ORDER BY "This_is_at_DateTime" ASC, "StockChangeHasStockChangeId" ASC LOOP

			IF EXISTS(SELECT "Quantity" 
					FROM soberano."Stock" 
					WHERE "InventoryItemHasInventoryItemCode" = stockChangeRecord.inventoryItem
							AND "WarehouseHasWarehouseId" = stockChangeRecord.warehouse) THEN
				SELECT "Quantity" 
					FROM soberano."Stock" 
					WHERE "InventoryItemHasInventoryItemCode" = stockChangeRecord.inventoryItem
							AND "WarehouseHasWarehouseId" = stockChangeRecord.warehouse
					INTO currentQuantity;
			ELSE
				currentQuantity := 0::numeric;
			END IF;

			-- save value of an item whose value is falling to or under zero during recalculation
			IF currentQuantity + stockChangeRecord.qty <= 0 THEN
				IF EXISTS(SELECT * FROM "stockValuesBeforeFallingToOrBelowZero"
							WHERE warehouse = stockChangeRecord.warehouse
								AND item = stockChangeRecord.inventoryItem) THEN
					UPDATE "stockValuesBeforeFallingToOrBelowZero" svbftbz SET itemValue = stock."This_has_Value"
						FROM soberano."Stock" stock
						WHERE svbftbz.warehouse = stockChangeRecord.warehouse
							AND svbftbz.item = stockChangeRecord.inventoryItem
							AND svbftbz.warehouse = stock."WarehouseHasWarehouseId"
							AND svbftbz.item = stock."InventoryItemHasInventoryItemCode";
				ELSE
					INSERT INTO "stockValuesBeforeFallingToOrBelowZero"(warehouse, itemValue, item)
						SELECT stockChangeRecord.warehouse,
								"This_has_Value",
								stockChangeRecord.inventoryItem
							FROM soberano."Stock" stock
							WHERE "WarehouseHasWarehouseId" = stockChangeRecord.warehouse
								AND "InventoryItemHasInventoryItemCode" = stockChangeRecord.inventoryItem;
				END IF;
				stockValueInOriginWarehouseMustBeRestored := true;
			ELSE
				stockValueInOriginWarehouseMustBeRestored := false;
			END IF;
									
			/*this stock change record corresponds to a process run input. it needs to be rerun.*/
			IF stockChangeRecord.processRunId IS NOT NULL
				AND stockChangeRecord.qty <= 0 THEN
				
				--restore all input stock values
				UPDATE soberano."ProcessRunInputValue" priv
					SET "Value" = stock."This_has_Value" * pri."Quantity",
						"This_is_in_Currency_with_CurrencyHasCurrencyId" = systemCurrencyId
					FROM soberano."ProcessRunInput" pri, 
						soberano."ProcessRun" pr,
						soberano."CostCenter" cc,
						soberano."Stock" stock
					WHERE priv."ProcessRunHasProcessRunId" = pri."ProcessRunHasProcessRunId"
						AND pri."ProcessRunHasProcessRunId" = pr."ProcessRunHasProcessRunId"
						AND priv."InventoryItemHasInventoryItemCode" = pri."InventoryItemHasInventoryItemCode"					
						AND priv."InventoryItemHasInventoryItemCode" = stock."InventoryItemHasInventoryItemCode"
						AND pr."This_is_executed_in_CostCenter_with_CostCenterHasCostCenterId" = cc."CostCenterHasCostCenterId"
						AND cc."This_consumes_materials_from_InputWarehouse_with_WarehouseHasWa" = stock."WarehouseHasWarehouseId"
						AND pr."ProcessRunHasProcessRunId" = stockChangeRecord.processRunId;
			END IF;
			PERFORM soberano."fn_Stock_change"(stockChangeRecord."StockChangeHasStockChangeId");

			IF stockValueInOriginWarehouseMustBeRestored THEN
				
				IF currentQuantity <= 0 THEN

					-- so, the value was aleready used. just set it to zero, 
					-- as the stock was depleted in the previous stock change
					UPDATE soberano."Stock" stock SET "This_has_Value" = 0
						FROM "stockValuesBeforeFallingToOrBelowZero" svbftbz
							WHERE stock."WarehouseHasWarehouseId" = stockChangeRecord.warehouse
								AND stock."InventoryItemHasInventoryItemCode" = stockChangeRecord.inventoryItem;

				ELSE

					-- restore stock value in origin warehouse for the case it falls to or under zero, for
					-- the next stock change taking that value from that warehouse to properly recalculate
					-- the new value.
					UPDATE soberano."Stock" stock SET "This_has_Value" = svbftbz.itemValue
						FROM "stockValuesBeforeFallingToOrBelowZero" svbftbz
							WHERE svbftbz.warehouse = stockChangeRecord.warehouse
								AND svbftbz.item = stockChangeRecord.inventoryItem
								AND svbftbz.warehouse = stock."WarehouseHasWarehouseId"
								AND svbftbz.item = stock."InventoryItemHasInventoryItemCode";					

				END IF;
			END IF;
		END LOOP;		
	END;
$BODY$;