CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport3_query"(
	lang character,
	fromd date,
	untild date,
	ccname character varying,
	loginname character varying)
    RETURNS TABLE("iName" character varying, "unitCost" numeric, "iPrice" numeric, 
				"soldQuantity" numeric, opening numeric, inputs numeric, outputs numeric, ending numeric) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 100

AS $BODY$
DECLARE
	pTime timestamp with time zone;
	cTime timestamp with time zone;
	fromClosureId integer;
	untilClosureId integer;
BEGIN
	--the user has rights to record a closure,
	IF (soberano."fn_ShiftClosure_closureAllowed"(loginname))
	
		--or make decisions on an existing closure
		OR EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(20, 1, loginname)) THEN

			SELECT soberano."fn_ShiftClosure_getIdFromDate"(to_char(COALESCE(fromd, now()::date), 'YYYY-MM-DD')) 
				INTO fromClosureId;
	
			SELECT soberano."fn_ShiftClosure_getIdFromDate"(to_char(COALESCE(untild, now()::date), 'YYYY-MM-DD')) 
				INTO untilClosureId;
	
			SELECT previousclosuretime 
				FROM soberano."fn_ShiftClosure_getTimes"(fromClosureId)
				INTO pTime;
	
			SELECT closuretime 
				FROM soberano."fn_ShiftClosure_getTimes"(untilClosureId)
				INTO cTime;

			DROP TABLE IF EXISTS shiftDateTimes;
			CREATE TEMPORARY TABLE shiftDateTimes(shift date, datetime timestamp with time zone);

			--calc the shifts within which orders were closed
			INSERT INTO shiftDateTimes 
				SELECT DISTINCT soberano."fn_ShiftClosure_getShiftFromDateTime"(orderCollectingVotet."This_is_on_Timestamp") oDate,
								orderCollectingVotet."This_is_on_Timestamp"
					FROM soberano."Order" ord
						INNER JOIN metamodel."EntityTypeInstance" eti
							ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = ord."This_is_identified_by_EntityTypeInstance_id"
									
								--only closed orders
								AND eti."This_is_in_Stage_with_StageHasStageId" = 6
						INNER JOIN metamodel."Vote" orderCollectingVotet
							ON ord."This_is_identified_by_EntityTypeInstance_id" = orderCollectingVotet."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"
			
								--orders closed (collected) within the passed time interval
								AND orderCollectingVotet."This_favors_Decision_with_DecisionHasDecisionId" = 3005
								AND orderCollectingVotet."This_is_on_Timestamp" > pTime
								AND orderCollectingVotet."This_is_on_Timestamp" <= cTime;

			RETURN QUERY SELECT iitemo."This_has_Name",
								priv."Value" / SUM(pro."Quantity"),
								"Price",
								SUM(pro."Quantity"),
								stock.opening, 
								stock.input_, 
								stock.output_ + stock.losses + stock.movement,
								stock.ending
							FROM soberano."ProcessRunOutputHasPriceForOrder" prohpfo
								INNER JOIN soberano."InventoryItem" iitemo
									ON iitemo."InventoryItemHasInventoryItemCode" = prohpfo."InventoryItemHasInventoryItemCode"
								INNER JOIN soberano."Product" prod
									ON prod."InventoryItemHasInventoryItemCode" = iitemo."InventoryItemHasInventoryItemCode"
								INNER JOIN soberano."ProductIsOfProductCategory" pcat
									ON pcat."ProductHasProductId" = prod."ProductHasProductId"
								INNER JOIN soberano."ProductCategory" cat
									ON cat."ProductCategoryHasProductCategoryId" = pcat."ProductCategoryHasProductCategoryId"
								INNER JOIN soberano."ProcessRunOutputValue" prov
									ON prov."InventoryItemHasInventoryItemCode" = iitemo."InventoryItemHasInventoryItemCode"
										AND prov."ProcessRunHasProcessRunId" = prohpfo."ProcessRunHasProcessRunId"				
								INNER JOIN soberano."ProcessRunOutput" pro
									ON pro."InventoryItemHasInventoryItemCode" = prov."InventoryItemHasInventoryItemCode"
										AND pro."ProcessRunHasProcessRunId" = prov."ProcessRunHasProcessRunId"
								INNER JOIN soberano."ProcessRun" pr
									ON pr."ProcessRunHasProcessRunId" = pro."ProcessRunHasProcessRunId"
								INNER JOIN soberano."CostCenter" cc
									ON cc."CostCenterHasCostCenterId" = pr."This_is_executed_in_CostCenter_with_CostCenterHasCostCenterId"
								INNER JOIN soberano."ProcessRunInput" pri
									ON pr."ProcessRunHasProcessRunId" = pri."ProcessRunHasProcessRunId"
								INNER JOIN soberano."InventoryItem" iitemi
									ON iitemi."InventoryItemHasInventoryItemCode" = pri."InventoryItemHasInventoryItemCode"
								INNER JOIN soberano."ProcessRunInputValue" priv
									ON priv."InventoryItemHasInventoryItemCode" = iitemi."InventoryItemHasInventoryItemCode"
										AND priv."ProcessRunHasProcessRunId" = prohpfo."ProcessRunHasProcessRunId"	
								INNER JOIN soberano."OrderProcessRun" opr
									ON opr."OrderHasOrderId" = prohpfo."OrderHasOrderId"
										AND opr."ProcessRunHasProcessRunId" = prohpfo."ProcessRunHasProcessRunId"
											AND opr."This_has_Description" != 'subprocess'
								INNER JOIN soberano."Order" ord
									ON ord."OrderHasOrderId" = opr."OrderHasOrderId"
								INNER JOIN metamodel."EntityTypeInstance" eti
									ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = ord."This_is_identified_by_EntityTypeInstance_id"
						
										--only closed orders
										AND eti."This_is_in_Stage_with_StageHasStageId" = 6
								INNER JOIN metamodel."Vote" orderCollectingVotet
									ON ord."This_is_identified_by_EntityTypeInstance_id" = orderCollectingVotet."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"
						
										--orders closed (collected) within the passed time interval
										AND orderCollectingVotet."This_favors_Decision_with_DecisionHasDecisionId" = 3005
										AND orderCollectingVotet."This_is_on_Timestamp" > pTime
										AND orderCollectingVotet."This_is_on_Timestamp" <= cTime
								INNER JOIN shiftDateTimes
									ON shiftDateTimes.datetime = orderCollectingVotet."This_is_on_Timestamp"
								INNER JOIN soberano."AcquirableMaterial" am
									ON am."InventoryItemHasInventoryItemCode" = iitemi."InventoryItemHasInventoryItemCode"
								
								--param value 1002 is the id of the warehouse from which the cost center consumes materials
								INNER JOIN (SELECT * from soberano."fn_InventoryOperation_getSPI"(to_char(untild, 'YYYY/MM/DD'), 1002, 0, lang, loginname)) stock
									ON stock."acquirableMaterialId" = am."AcquirableMaterialHasAcquirableMaterialId"
							GROUP BY iitemo."This_has_Name",
									priv."Value",
									"Price",
									cat."This_is_shown_in_Position",
									stock.opening, 
									stock.input_, 
									stock.output_,
									stock.losses,
									stock.movement,
									stock.ending
							ORDER BY cat."This_is_shown_in_Position" ASC,
									iitemo."This_has_Name" ASC;						
	ELSE
		RETURN QUERY SELECT ''::character varying,
							0.0::numeric, 
							0.0 numeric,
							0.0::numeric,
							0.0::numeric,
							0.0::numeric,
							0.0::numeric,
							0.0::numeric;
	END IF;
END;
$BODY$;