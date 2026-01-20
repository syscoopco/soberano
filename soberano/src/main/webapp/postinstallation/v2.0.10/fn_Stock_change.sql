CREATE OR REPLACE FUNCTION soberano."fn_Stock_change"(
	stockchangeid integer)
    RETURNS void
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE
	inventoryitem character varying;
	warehouse integer;
	quantity numeric;
	unitvalue numeric;
	pQty numeric;
	pValue numeric;
	
	processRunId integer;
	newStockUnitValue numeric;
	outputsTotalValue numeric;
	weighttoredistribute numeric;
	producedoutputcount integer;
	outputvalue numeric;
	itemRecord record;
	processRunDateTime timestamp with time zone;
	systemCurrencyId integer;
BEGIN
	--here, the item already exists in that warehouse since stock change was called before.
	SELECT "This_is_called_with_Quantity", 
			CASE WHEN "This_takes_value_from_Warehouse_with_WarehouseHasWarehouseId" IS NULL THEN "This_is_called_with_Value"
				ELSE "This_has_Value" END,
			"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
			"This_is_called_with_InventoryItem_with_InventoryItemHasInventor",
			"This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId"
		FROM soberano."StockChange" sc
			LEFT JOIN soberano."Stock" st
				ON sc."This_takes_value_from_Warehouse_with_WarehouseHasWarehouseId" = st."WarehouseHasWarehouseId"
					AND sc."This_is_called_with_InventoryItem_with_InventoryItemHasInventor" = st."InventoryItemHasInventoryItemCode"
		WHERE "StockChangeHasStockChangeId" = stockChangeId
		INTO quantity,
			unitvalue,
			warehouse,
			inventoryitem,
			processRunId;
			
	--current corresponding stock state	
	SELECT 	"Quantity",
			"This_has_Value"
		FROM soberano."Stock"
			WHERE "WarehouseHasWarehouseId" = warehouse
				AND "InventoryItemHasInventoryItemCode" = inventoryitem
		INTO pQty, pValue;
		
	--update stock change
	UPDATE soberano."StockChange" SET "This_is_called_with_Value" = unitvalue, 
										   "This_saves_previous_Quantity" = pQty, 
										   "This_saves_previous_Value" = pValue
		WHERE "StockChangeHasStockChangeId" = stockChangeId;
		
	--update stock. 
	SELECT CASE WHEN "Quantity" + quantity <= 0 THEN 0
				WHEN quantity <= 0 THEN "This_has_Value"
				WHEN "Quantity" < 0 THEN unitvalue
				ELSE ("Quantity" * "This_has_Value" + quantity * unitvalue) / ("Quantity" + quantity) END
		FROM soberano."Stock"
		WHERE "WarehouseHasWarehouseId" = warehouse
			AND "InventoryItemHasInventoryItemCode" = inventoryItem
		INTO newStockUnitValue;
		
	UPDATE soberano."Stock" 
		SET "This_has_Value" = newStockUnitValue,
			"Quantity" = "Quantity" + quantity
		WHERE "WarehouseHasWarehouseId" = warehouse
				AND "InventoryItemHasInventoryItemCode" = inventoryItem;
				
	--if it is an input of a process run
	IF EXISTS(SELECT * 
				FROM soberano."ProcessRunInput" 
				WHERE "ProcessRunHasProcessRunId" = processRunId
					AND "InventoryItemHasInventoryItemCode" = inventoryItem)
		AND NOT quantity > 0 THEN
		
		SELECT "CurrencyHasCurrencyId" 
			FROM soberano."Currency"
			WHERE "Currency_is_system_currency"
			INTO systemCurrencyId;

		--update input value for proper following process run output values recalculation
		UPDATE soberano."ProcessRunInputValue"
			SET "Value" = newStockUnitValue * abs(quantity),
				"This_is_in_Currency_with_CurrencyHasCurrencyId" = systemCurrencyId
			WHERE "ProcessRunHasProcessRunId" = processRunId
					AND "InventoryItemHasInventoryItemCode" = inventoryItem;

		SELECT SUM("Value" * "This_is_converted_to_system_currency_multiplying_by_ExchangeRat") 
			FROM soberano."ProcessRunInputValue" priv
				INNER JOIN soberano."Currency" curr
					ON priv."This_is_in_Currency_with_CurrencyHasCurrencyId" = curr."CurrencyHasCurrencyId"
			WHERE "ProcessRunHasProcessRunId" = processRunId
			INTO outputsTotalValue;

		/******************************************************************/
		/* almost the same as soberano."fn_ProcessRun_close" 	    	  */
		/* it's about process run output values matching the input values */
		/******************************************************************/
		
		--process run output values are to be recalculated next
		DELETE FROM soberano."ProcessRunOutputValue"
			WHERE "ProcessRunHasProcessRunId" = processrunid;
		
		--distribute outputs total value among outputs and update output warehouse stocks.
		--the weights of outputs equal to zero are redistributed among the other ones
		weighttoredistribute := 0;
		producedoutputcount := 0;
		FOR itemRecord IN SELECT pro."InventoryItemHasInventoryItemCode" item,
									pro."Quantity" quantity,
									pro."This_value_is_weighted_by_WeightCoefficient" weight,
									pr."This_is_at_DateTime" datetime
								FROM soberano."ProcessRunOutput" pro
									INNER JOIN soberano."ProcessRun" pr
										ON pr."ProcessRunHasProcessRunId" = pro."ProcessRunHasProcessRunId"
											AND pro."ProcessRunHasProcessRunId" = processrunid LOOP
								
			--no production of this output
			IF itemRecord.quantity = 0 THEN

				--no output value is recorded

				--its weight must be redistributed among the other ones
				weighttoredistribute := weighttoredistribute + itemRecord.weight;
			ELSE
				--replaced by next line outputvalue := itemRecord.quantity * outputsTotalValue * itemRecord.weight / 100;	
				outputvalue := outputsTotalValue * itemRecord.weight / 100;	

				INSERT INTO soberano."ProcessRunOutputValue"("Value", 
															 "ProcessRunHasProcessRunId", 
															 "InventoryItemHasInventoryItemCode", 
															 "This_is_in_Currency_with_CurrencyHasCurrencyId")
					VALUES(outputvalue, 
							processrunid, 
							itemRecord.item,
							systemCurrencyId);

				producedoutputcount := producedoutputcount + 1;
			END IF;

			UPDATE soberano."ProcessRunOutput"
				SET "This_value_is_weighted_by_WeightCoefficient" = itemRecord.weight
				WHERE "ProcessRunHasProcessRunId" = processrunid
					AND "InventoryItemHasInventoryItemCode" = itemRecord.item;
					
 			processRunDateTime := itemRecord.datetime;
		END LOOP;

		--only outputs with quantity > 0 have rows in soberano."ProcessRunOutputValue"
		UPDATE soberano."ProcessRunOutputValue" prov
			--replaced by next line SET "Value" = "Value" + pro."Quantity" * outputsTotalValue * weighttoredistribute / producedoutputcount / 100
			SET "Value" = "Value" + outputsTotalValue * weighttoredistribute / producedoutputcount / 100
			FROM soberano."ProcessRunOutput" pro
			WHERE prov."ProcessRunHasProcessRunId" = processrunid
				AND prov."ProcessRunHasProcessRunId" = pro."ProcessRunHasProcessRunId"
				AND prov."InventoryItemHasInventoryItemCode" = pro."InventoryItemHasInventoryItemCode";
				
		UPDATE soberano."ProcessRunFixedCost" prfc
						SET "FixedCost" = proc."This_has_FixedCost",
							"This_is_in_Currency_with_CurrencyHasCurrencyId" = systemCurrencyId
						FROM soberano."ProcessRun" pr, soberano."Process" proc
						WHERE prfc."ProcessRunHasProcessRunId" = processrunid
							AND prfc."ProcessRunHasProcessRunId" = pr."ProcessRunHasProcessRunId"
							AND pr."This_is_of_Process_with_ProcessHasProcessId" = proc."ProcessHasProcessId";
	END IF;	
END;
$BODY$;