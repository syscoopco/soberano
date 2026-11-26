CREATE OR REPLACE FUNCTION soberano."fn_Order_getProductionLineGroupedAllocations"(
	orderid integer,
	productionlineid integer,
	lang character,
	loginname character varying)
    RETURNS text
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE
	ticket text;
	counter integer;
	
	itemRecord RECORD;
	itemName character varying;
	itemQty text;
	
	previousparentprocessrunid integer;
	additionsCount integer;
	
	lastProcessRunId text; 
	
BEGIN
	ticket := '';
	
	--get order data
	SELECT co."CounterHasCounterId"
		FROM soberano."OrderOccupiesCounter" co
			INNER JOIN soberano."Counter" counter
				ON counter."CounterHasCounterId" = co."CounterHasCounterId"
			INNER JOIN soberano."Order" o
				ON o."OrderHasOrderId" = co."OrderHasOrderId"
					AND o."OrderHasOrderId" = orderid
			INNER JOIN "metamodel"."EntityTypeInstance" eti
				ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = o."This_is_identified_by_EntityTypeInstance_id"
		WHERE EXISTS (SELECT metamodel."fn_EntityTypeInstance_getDecisions"(o."This_is_identified_by_EntityTypeInstance_id", loginname))
		INTO counter;
			
	IF counter IS NOT NULL THEN
	
		FOR itemRecord IN SELECT iitem."This_has_Name" iname,
									round(opr."This_has_ordered_Runs" - opr."This_has_customer-canceled_Runs", /*2*/ 0) iqty,
									unit."Acronym" unit,
									opr."ProcessRunHasProcessRunId" processrunid,
									COALESCE(opr."This_is_an_addition_of_OrderProcessRun_with_ProcessRunHasProces", opr."ProcessRunHasProcessRunId") parentprocessrunid,
									pc."This_is_shown_in_Position",
									parentpc."This_is_shown_in_Position", --this is to order by parent process run,
									CASE WHEN opr."This_has_Description" = '' THEN ''
										WHEN opr."This_has_Description" IS NULL THEN ''
										ELSE ' (' || opr."This_has_Description" || ')' END description
							FROM soberano."Product" product
								INNER JOIN soberano."ProductIsOfProductCategory" ppc
									ON product."ProductHasProductId" = ppc."ProductHasProductId"
								INNER JOIN soberano."ProductCategory" pc
									ON pc."ProductCategoryHasProductCategoryId" = ppc."ProductCategoryHasProductCategoryId"
								INNER JOIN soberano."InventoryItem" iitem
									ON iitem."InventoryItemHasInventoryItemCode" = product."InventoryItemHasInventoryItemCode"
								INNER JOIN soberano."ProcessRunOutputHasPriceForOrder" prohpfo
									ON prohpfo."InventoryItemHasInventoryItemCode" = product."InventoryItemHasInventoryItemCode"
										AND prohpfo."OrderHasOrderId" = orderid
								INNER JOIN soberano."OrderProcessRun" opr
									ON opr."OrderHasOrderId" = prohpfo."OrderHasOrderId"
										AND opr."ProcessRunHasProcessRunId" = prohpfo."ProcessRunHasProcessRunId"
											AND opr."This_has_Description" != 'subprocess'

												AND opr."ProcessRunHasProcessRunId" NOT IN
													(SELECT "ProcessRunOutput_with_ProcessRunHasProcessRunId_is_allocated_by"
														FROM soberano."ProcessRunOutputAllocation"
														WHERE "ProcessRunOutputAllocation_is_omitted")											
											
								INNER JOIN soberano."UnitHasAcronymInLanguage" unit
									ON unit."UnitHasUnitId" = iitem."This_is_measured_in_Unit_with_UnitHasUnitId"
										AND unit."Language" = lang
										
								INNER JOIN soberano."CostCenter" ccenter
									ON ccenter."CostCenterHasCostCenterId" = product."This_is_usually_produced_in_CostCenter_with_CostCenterHasCostCe"
										AND ccenter."This_uses_ProductionLine_with_ProductionLineHasProductionLineId" = productionlineid
										
								--this is to order by parent process run
								LEFT JOIN soberano."OrderProcessRun" parentopr
									ON opr."This_is_an_addition_of_OrderProcessRun_with_ProcessRunHasProces" = parentopr."ProcessRunHasProcessRunId"
										AND parentopr."This_has_Description" != 'subprocess'
								LEFT JOIN soberano."ProcessRunOutputHasPriceForOrder" parentprohpfo
									ON parentopr."ProcessRunHasProcessRunId" = parentprohpfo."ProcessRunHasProcessRunId"
								LEFT JOIN soberano."Product" parentproduct
									ON parentproduct."InventoryItemHasInventoryItemCode" = parentprohpfo."InventoryItemHasInventoryItemCode"
								LEFT JOIN soberano."ProductIsOfProductCategory" parentproductcat
									ON parentproductcat."ProductHasProductId" = parentproduct."ProductHasProductId"
								LEFT JOIN soberano."ProductCategory" parentpc
									ON parentproductcat."ProductCategoryHasProductCategoryId" = parentpc."ProductCategoryHasProductCategoryId"
							WHERE opr."ProcessRunHasProcessRunId" NOT IN (SELECT "ProcessRun" FROM soberano."z-allocationsAlreadyRequestedForPrinted")
							ORDER BY COALESCE(parentpc."This_is_shown_in_Position", pc."This_is_shown_in_Position") ASC, --this is to order by parent process run
									COALESCE(opr."This_is_an_addition_of_OrderProcessRun_with_ProcessRunHasProces", opr."ProcessRunHasProcessRunId") ASC,
									opr."ProcessRunHasProcessRunId" ASC
									/*pc."This_is_shown_in_Position" ASC, 
									iitem."This_has_Name" ASC,
									opr."ProcessRunHasProcessRunId" ASC*/ LOOP
									
			IF itemRecord.iqty > 0 THEN
			
				itemQty := CAST(itemRecord.iqty AS text) || /*' ' || itemRecord.unit ||*/ ' x ';
				
				IF previousparentprocessrunid != itemRecord."parentprocessrunid" THEN
					IF additionsCount > 0 THEN
						ticket := ticket || chr(13) || itemQty || itemRecord.iname || itemRecord.description || chr(13);
						additionsCount := 0;
					ELSE
						ticket := ticket || itemQty || itemRecord.iname || itemRecord.description || chr(13);
					END IF;
				ELSE
					additionsCount := additionsCount + 1;
					ticket := ticket || itemQty || itemRecord.iname || itemRecord.description || chr(13);				
				END IF;	
				previousparentprocessrunid := itemRecord."parentprocessrunid";
				
			END IF;
			
			INSERT INTO soberano."z-allocationsAlreadyRequestedForPrinted"("ProcessRun")
				VALUES (itemRecord."processrunid");
				
			lastProcessRunId := CAST(itemRecord.parentprocessrunid AS text);
			
		END LOOP;		
		RETURN ticket || chr(13) || lastProcessRunId;
	ELSE
		RETURN '';
	END IF;	
END;
$BODY$;