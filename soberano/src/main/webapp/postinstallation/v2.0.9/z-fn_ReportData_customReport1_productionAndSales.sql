CREATE OR REPLACE FUNCTION soberano."z-fn_ReportData_customReport1_productionAndSales"(
	lang character,
	fromd date,
	untild date,
	ccname character varying,
	loginname character varying)
    RETURNS TABLE(orderdate date, "iCategoryName" character varying, "iName" character varying, "iQty" numeric, "iAmount" numeric, days integer) 
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
	IF ('Manager' IN (SELECT * from metamodel."fn_User_getResponsibilities"(loginname)))
	
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

			DROP TABLE IF EXISTS "translator";
			CREATE TEMPORARY TABLE "translator"("order" integer,
												"system_term" character varying(255),
												"translated_term" character varying(255),
												"translated_term_category" character varying(255));

			INSERT INTO "translator"("order", 
												system_term, 
												translated_term, 
												translated_term_category)
				SELECT DISTINCT tprod."This_has_Position",
						iitem."This_has_Name",
						tttttprod."Term",
						tcat."Term"
					FROM soberano."Product" prod
						INNER JOIN soberano."InventoryItem" iitem
							ON iitem."InventoryItemHasInventoryItemCode" = prod."InventoryItemHasInventoryItemCode"
						INNER JOIN soberano."ProductIsOfProductCategory" pcat
							ON prod."ProductHasProductId" = pcat."ProductHasProductId"
						INNER JOIN (SELECT "ProductCategoryHasProductCategoryId",
											"Term"
										FROM soberano."ProductCategory" cat
											INNER JOIN soberano."TranslationTranslateTermToTerm" tttttcat
												ON cat."This_has_Name" = tttttcat."SystemTerm") tcat
							ON tcat."ProductCategoryHasProductCategoryId" = pcat."ProductCategoryHasProductCategoryId"
						INNER JOIN soberano."TranslationTranslateTermToTerm" tttttprod
							ON tttttprod."SystemTerm" = iitem."This_has_Name"
						INNER JOIN soberano."Translation" tprod
							ON tprod."TranslationHasTranslationId" = tttttprod."TranslationHasTranslationId";

			RETURN QUERY SELECT shift,
								transCat,
								transItem,
								SUM(iQty),
								SUM(iAmnt),
								daysNumber
							FROM (	
						SELECT shift,
								CASE WHEN trans.translated_term IS NULL 
									THEN categoryName ELSE trans."translated_term_category" END transCat,
								CASE WHEN trans.translated_term IS NULL 
									THEN itemName ELSE trans."translated_term" END transItem,
								COALESCE(SUM(itemQty), 0.0) iQty,
								COALESCE(SUM(itemAmount), 0.0) iAmnt,
								shift - fromd daysNumber,
								trans.order transOrder,
								ccentername
							FROM "translator" trans
								LEFT JOIN
									(SELECT CASE WHEN shift IS NULL THEN fromd ELSE shift END,
												"This_is_usually_produced_in_CostCenter_with_CostCenterHasCostCe" ccenter,
												iitem."InventoryItemHasInventoryItemCode" itemCode,
												pc."This_has_Name" categoryName,
												pc."This_is_shown_in_Position" categoryPosition,
												iitem."This_has_Name" itemName,
												CASE WHEN shift IS NOT NULL THEN ("This_has_ordered_Runs" - "This_has_customer-canceled_Runs") 
													ELSE 0.0 END itemQty,
												CASE WHEN shift IS NOT NULL THEN ("Price" * ("This_has_ordered_Runs" - 
																"This_has_customer-canceled_Runs" - 
																"This_has_full_discounted_Runs") *
														(1 - COALESCE(ord."This_is_granted_a_DiscountRate", 0.0) / 100) * 
														(1 - COALESCE(custo."This_has_DiscountRate", 0.0) / 100))
													ELSE 0.0 END itemAmount,
												cc."This_has_Name" ccentername
									FROM soberano."Product" prod
									 		INNER JOIN metamodel."EntityTypeInstance" prodeti
												ON prodeti."EntityTypeInstanceHasEntityTypeInstanceId" = prod."This_is_identified_by_EntityTypeInstance_id"
									 				--solo artículos habilitados
									 				AND prodeti."This_is_in_Stage_with_StageHasStageId" = 2
											INNER JOIN soberano."ProductIsOfProductCategory" ppc
												ON prod."ProductHasProductId" = ppc."ProductHasProductId"
											INNER JOIN soberano."ProductCategory" pc
												ON ppc."ProductCategoryHasProductCategoryId" = pc."ProductCategoryHasProductCategoryId"
											INNER JOIN soberano."CostCenter" cc
												ON cc."CostCenterHasCostCenterId" = prod."This_is_usually_produced_in_CostCenter_with_CostCenterHasCostCe"
											INNER JOIN soberano."InventoryItem" iitem
												ON iitem."InventoryItemHasInventoryItemCode" = prod."InventoryItemHasInventoryItemCode"
											LEFT JOIN soberano."ProcessRunOutputHasPriceForOrder" propo
												ON propo."InventoryItemHasInventoryItemCode" = prod."InventoryItemHasInventoryItemCode"
											LEFT JOIN soberano."OrderProcessRun" opr
												ON opr."OrderHasOrderId" = propo."OrderHasOrderId"
													AND opr."ProcessRunHasProcessRunId" = propo."ProcessRunHasProcessRunId"
														AND opr."This_has_Description" != 'subprocess'
											LEFT JOIN soberano."Order" ord
												ON ord."OrderHasOrderId" = propo."OrderHasOrderId"
													AND ord."OrderHasOrderId" = opr."OrderHasOrderId"
											LEFT JOIN metamodel."EntityTypeInstance" eti
												ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = ord."This_is_identified_by_EntityTypeInstance_id"
								
													--only closed orders
													AND eti."This_is_in_Stage_with_StageHasStageId" = 6
											LEFT JOIN metamodel."Vote" orderCollectingVotet
												ON ord."This_is_identified_by_EntityTypeInstance_id" = orderCollectingVotet."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"
								
													--orders closed (collected) within the passed time interval
													AND orderCollectingVotet."This_favors_Decision_with_DecisionHasDecisionId" = 3005
													AND orderCollectingVotet."This_is_on_Timestamp" > pTime
													AND orderCollectingVotet."This_is_on_Timestamp" <= cTime
											LEFT JOIN shiftDateTimes
												ON shiftDateTimes.datetime = orderCollectingVotet."This_is_on_Timestamp"
											LEFT JOIN soberano."CustomerOrder" custo
															ON custo."OrderHasOrderId" = ord."OrderHasOrderId") sq
										ON sq.itemName = trans."system_term"
							GROUP BY shift,
									ccentername,
									categoryName,
									itemName,
									trans."translated_term_category",
									trans.translated_term,
									trans.order) ssqq
							WHERE (ccentername = ccname OR ccname = '' OR ccname IS NULL)
							GROUP BY shift,
									transCat,
									transItem,
									daysNumber,
									ccentername,
									transOrder
							ORDER BY ccentername ASC,
									transOrder ASC,--categoryPosition ASC,
									transItem ASC,
									shift ASC;									
	ELSE
		RETURN QUERY SELECT now()::date,
							''::character varying,
							''::character varying,
							0.0::numeric,
							0.0::numeric,
							0;
	END IF;
END;
$BODY$;