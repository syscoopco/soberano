CREATE OR REPLACE FUNCTION soberano."fn_Order_getTicket"(
	orderid integer,
	receivedamount numeric,
	change numeric,
	lang character,
	loginname character varying)
    RETURNS TABLE(ttp text, "printerProfile" integer) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1

AS $BODY$
DECLARE
	ticket text;
	
	orderAmount numeric;
	collectedAmount numeric;
	surchargevar numeric;
	surchargeAmount numeric;
	customerDiscountRate numeric;
	orderDiscountRate numeric;
	combinedDiscountRate numeric;
	deliveryAmount numeric;
	toCollectAmount numeric;
	subtotal numeric;
	
	pageHeader text;
	pageFooter text;
	printerProfile integer;
	counterPP integer;
	cashRegisterPP integer;
	defaultPP integer;
	compactPrinting boolean;
	
	customerName text;
	deliveryTo text;
	orderTime text;
	counter character varying;
	stage integer;
	providerName character varying;
	providerIsReseller boolean;
	deliveryProviderRate double precision;
	compensateDeliveryProviderRates boolean;
	
	itemRecord RECORD;
	itemName character varying;
	itemQty text;
	itemAmount text;
	itemDiscount text;
	itemPrice text;
	
	paidAmount numeric;
	
	previousparentprocessrunid integer;
	additionsCount integer;
	
BEGIN
	ticket := '';
	
	--get order data
	SELECT to_char(COALESCE(vote."This_is_on_Timestamp", NOW()), 'YYYY/MM/DD HH24:MI'),
			"This_has_Counter_code",
			"This_is_in_Stage_with_StageHasStageId",
			cu."This_has_FirstName" || ' ' || cu."This_has_LastName",
			'Tel: ' || ddata."This_includes_PhoneNumber" || chr(13) ||
			'Email: ' || ddata."This_includes_EmailAddress" || chr(13) ||
			'Address: ' || addr."This_includes_AddressString" || ', ' || 
							addr."This_includes_Town" || ', ' || 
							addr."This_includes_PostalCode"	|| ', ' || 
							mun."This_has_Name"	|| ', ' || 
							prov."This_has_Name" || ', ' || 
							ctry."This_has_Name",
			dp."This_has_Name",
			delivery."Delivery_provider_is_a_reseller",
			delivery."Rate_is_applied_to_This"
		FROM soberano."OrderOccupiesCounter" co
			INNER JOIN soberano."Counter" counter
				ON counter."CounterHasCounterId" = co."CounterHasCounterId"
			INNER JOIN soberano."Order" o
				ON o."OrderHasOrderId" = co."OrderHasOrderId"
					AND o."OrderHasOrderId" = orderid
			INNER JOIN "metamodel"."EntityTypeInstance" eti
				ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = o."This_is_identified_by_EntityTypeInstance_id"
			LEFT JOIN "metamodel"."Vote" vote
				ON o."This_is_identified_by_EntityTypeInstance_id" = vote."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"
			LEFT JOIN "metamodel"."Decision" decision
				ON vote."This_favors_Decision_with_DecisionHasDecisionId" = decision."DecisionHasDecisionId"
			LEFT JOIN "metamodel"."StageFilter" stage
				ON stage."StageFilterHasStageFilterId" = decision."This_causes_advance_to_StageFilter_with_StageFilterHasStageFilt"
			LEFT JOIN soberano."CustomerOrder" custoo
				ON custoo."OrderHasOrderId" = o."OrderHasOrderId"
			LEFT JOIN soberano."Customer" cu
				ON cu."CustomerHasCustomerId" = custoo."CustomerHasCustomerId"
			LEFT JOIN soberano."ContactData" ddata
				ON ddata."ContactDataHasContactDataId" = o."This_is_delivered_to_ContactData_with_ContactDataHasContactData"
			LEFT JOIN soberano."PostalAddress" addr
				ON ddata."This_includes_PostalAddress_with_PostalAddressHasPostalAddressI" = addr."PostalAddressHasPostalAddressId"
			LEFT JOIN soberano."Municipality" mun
				ON mun."MunicipalityHasMunicipalityId" = addr."This_includes_Municipality_with_MunicipalityHasMunicipalityId"
			LEFT JOIN soberano."Province" prov
				ON prov."ProvinceHasProvinceId" = mun."This_belongs_to_Province_with_ProvinceHasProvinceId"
			LEFT JOIN soberano."Country" ctry
				ON ctry."CountryHasCountryCode" = prov."This_belongs_to_Country_with_CountryHasCountryCode"
			LEFT JOIN soberano."Delivery" delivery
				ON delivery."OrderHasOrderId" = o."OrderHasOrderId"
			LEFT JOIN soberano."DeliveryProvider" dp
				ON dp."DeliveryProviderHasDeliveryProviderId" = delivery."DeliveryProviderHasDeliveryProviderId"
		WHERE EXISTS (SELECT metamodel."fn_EntityTypeInstance_getDecisions"(o."This_is_identified_by_EntityTypeInstance_id", loginname))
		ORDER BY vote."VoteHasVoteId" DESC LIMIT 1
		INTO orderTime,
			counter,
			stage,
			customerName,
			deliveryTo,
			providerName,
			providerIsReseller,
			deliveryProviderRate;
			
	IF counter IS NOT NULL THEN
	
		--get order amounts
		SELECT round(COALESCE("orderAmount", CAST(0 AS numeric)), 2), 
				round(COALESCE(surcharge, CAST(0 AS numeric)), 2), 
				round(COALESCE("customerDiscountRate", CAST(0 AS numeric)), 2),
				round(COALESCE("orderDiscountRate", CAST(0 AS numeric)), 2),
				round(COALESCE("deliveryAmount", CAST(0 AS numeric)), 2)
			FROM soberano."fn_Order_getAmount"(orderid)
			INTO orderAmount,
				surchargevar,
				customerDiscountRate,
				orderDiscountRate,
				deliveryAmount;			
		SELECT "collectedAmount",
				"toCollectAmount"
			FROM soberano."fn_Order_getAmounts"(orderid)
			INTO collectedAmount,
				toCollectAmount;
				
		IF collectedAmount IS NULL THEN collectedAmount := CAST(0 AS numeric); END IF;
		IF toCollectAmount IS NULL THEN toCollectAmount := CAST(0 AS numeric); END IF;

		combinedDiscountRate := customerDiscountRate + orderDiscountRate;
		IF combinedDiscountRate > 100 THEN combinedDiscountRate := 100; END IF;

		--get printer profile
		SELECT co."This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId",
				cr."This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId",
				dpp."PrinterProfileHasPrinterProfileId"
			FROM soberano."Order" o
				INNER JOIN soberano."OrderOccupiesCounter" ooc
					ON o."OrderHasOrderId" = ooc."OrderHasOrderId"
						AND o."OrderHasOrderId" = orderid
				LEFT JOIN soberano."Counter" co
					ON co."CounterHasCounterId" = ooc."CounterHasCounterId"
						AND co."This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" IS NOT NULL
				LEFT JOIN soberano."CashRegister" cr
					ON cr."This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" IS NOT NULL
				LEFT JOIN soberano."PrinterProfile" dpp
					ON "PrinterProfile_is_default_printer_profile"
			LIMIT 1
			INTO counterPP,
				cashRegisterPP,
				defaultPP;

		IF cashRegisterPP IS NOT NULL THEN
			printerProfile := cashRegisterPP;
		ELSIF counterPP IS NOT NULL THEN
			printerProfile := counterPP;
		ELSIF defaultPP IS NOT NULL THEN
			printerProfile := defaultPP;
		ELSE printerProfile := 0;
		END IF;

		--printer profile conf
		SELECT "This_has_PageHeader", "This_has_PageFooter", "PrinterProfile_compacts_text"
			FROM soberano."PrinterProfile"
			WHERE "PrinterProfileHasPrinterProfileId" = printerProfile
			INTO pageHeader, pageFooter, compactPrinting;

		IF compactPrinting OR NOT printerProfile = 0 THEN	
			ticket := ticket || '-------------------- ' || chr(13);
		ELSE	
			ticket := ticket || 'tt_ITEM_tt' || chr(13);
			ticket := ticket || 'tt_ITEM_QTY_tt' || chr(13);
			ticket := ticket || 'tt_ITEM_PRICE_tt' || '   ' || 'tt_ITEM_AMOUNT_tt' || chr(13) || chr(13);
			ticket := ticket || '----- ' || chr(13);	
		END IF;

		subtotal := 0;

		previousparentprocessrunid := 0;
		additionsCount := 0;
		FOR itemRecord IN SELECT iname,
									SUM(iqtyi) iqty,
									iprice,
									SUM(iamounti) iamount,
									oid,
									unit,
									posi
								FROM (SELECT iitem."This_has_Name" iname,
									round(opr."This_has_ordered_Runs" - opr."This_has_customer-canceled_Runs", 2) iqtyi,
									round(prohpfo."Price", 2) iprice,
									round(((opr."This_has_ordered_Runs" - opr."This_has_customer-canceled_Runs") 
										- opr."This_has_full_discounted_Runs") * prohpfo."Price", 2) iamounti,
									round(opr."This_has_full_discounted_Runs" * prohpfo."Price", 2) idiscount,
									opr."OrderHasOrderId" oid,
									unit."Acronym" unit,
									opr."ProcessRunHasProcessRunId" processrunid,
									COALESCE(opr."This_is_an_addition_of_OrderProcessRun_with_ProcessRunHasProces", opr."ProcessRunHasProcessRunId") parentprocessrunid,
									pc."This_is_shown_in_Position" posi,
									parentpc."This_is_shown_in_Position" --this is to order by parent process run
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
								INNER JOIN soberano."UnitHasAcronymInLanguage" unit
									ON unit."UnitHasUnitId" = iitem."This_is_measured_in_Unit_with_UnitHasUnitId"
										AND unit."Language" = lang
										
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
									ON parentproductcat."ProductCategoryHasProductCategoryId" = parentpc."ProductCategoryHasProductCategoryId") ungroupeditemssq
							GROUP BY iname,
									iprice,
									oid,
									unit,
									posi
						ORDER BY posi LOOP
									
			IF itemRecord.iqty > 0 THEN
			
				subtotal := subtotal + itemRecord.iamount;

				IF compactPrinting THEN

					itemQty := CAST(itemRecord.iqty AS text) || ' ' || itemRecord.unit || ' x ';
					itemAmount := CAST(itemRecord.iamount AS text);
					--itemDiscount := '-' || CAST(itemRecord.idiscount AS text);
					itemPrice := CAST(itemRecord.iprice AS text);
					
					--IF previousparentprocessrunid != itemRecord."parentprocessrunid" THEN
					--	IF additionsCount > 0 THEN
					--		ticket := ticket || chr(13) || itemRecord.iname || chr(13);
					--		additionsCount := 0;
					--	ELSE
							ticket := ticket || itemRecord.iname || chr(13);
					--	END IF;
					--ELSE
					--	additionsCount := additionsCount + 1;
					--	ticket := ticket || itemRecord.iname || chr(13);				
					--END IF;	
					--previousparentprocessrunid := itemRecord."parentprocessrunid";

					--IF itemRecord.idiscount > 0 THEN
						--ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
						--																   3,
						--																   ARRAY[itemQty, itemPrice, ''],
						--																   ARRAY['l', 'r', 'r']) || chr(13);
						--ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
						--																   3,
						--																   ARRAY[itemDiscount, '', itemAmount],
						--																   ARRAY['l', 'r', 'r']) || chr(13);
					--ELSE
						ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
																						   3,
																						   ARRAY[itemQty, itemPrice, itemAmount],
																						   ARRAY['l', 'r', 'r']) || chr(13);
					--END IF;
				ELSE
					SELECT soberano."fn_Utilities_fillWithSpaces"(CAST(itemRecord.iqty AS text)) INTO itemQty;
					SELECT soberano."fn_Utilities_fillWithSpaces"(CAST(itemRecord.iamount AS text)) INTO itemAmount;
					--SELECT soberano."fn_Utilities_fillWithSpaces"('-' || CAST(itemRecord.idiscount AS text)) INTO itemDiscount;
					SELECT soberano."fn_Utilities_fillWithSpaces"(CAST(itemRecord.iprice AS text)) INTO itemPrice;

					ticket := ticket || itemRecord.iname || chr(13);
					ticket := ticket || itemQty || '   ' || itemRecord.unit || chr(13);
					ticket := ticket || 'x' || chr(13);
					--IF itemRecord.idiscount > 0 THEN
					--	ticket := ticket || itemPrice || chr(13);
					--	ticket := ticket || itemDiscount || '   ' || itemAmount || chr(13) || chr(13);
					--ELSE
						ticket := ticket || itemPrice || '   ' || itemAmount || chr(13) || chr(13);
					--END IF;
				END IF;			
			END IF;			
		END LOOP;

		ticket := ticket || '-------------------- ' || chr(13);
		ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
																		   3,
																		   ARRAY['tt_SUBTOTAL_tt', '', CAST(round(subtotal, 2) AS text)],
																		   ARRAY['l', 'r', 'r']) || chr(13);
		ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
																		   3,
																		   ARRAY['tt_DISCOUNT_tt' || ' ' || CAST(round(combinedDiscountRate, 2) AS text) || '%', '', CAST(round(subtotal - subtotal * combinedDiscountRate / 100, 2) AS text)],
																		   ARRAY['l', 'r', 'r']) || chr(13) || chr(13);

		--only surcharge if not delivery
		IF (providerName IS NULL) THEN

			surchargeAmount := subtotal * surchargevar / 100;

			ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
																			   3,
																			   ARRAY['tt_SURCHARGE_tt' || ' ' || CAST(round(surchargevar, 2) AS text) || '%', '', CAST(round(surchargeAmount, 2) AS text)],
																			   ARRAY['l', 'r', 'r']) || chr(13) || chr(13);
		END IF;

		ticket := ticket || '-------------------- ' || chr(13);
		ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
																		   3,
																		   ARRAY['tt_TOTAL_TO_PAY_tt', '', CAST(round(orderAmount, 2) AS text)],
																		   ARRAY['l', 'r', 'r']) || chr(13);	
		ticket := ticket || '-------------------- ' || chr(13) || chr(13);

		--the order has just been paid
		IF receivedamount > 0 THEN
			paidAmount := receivedamount;
		ELSE
			paidAmount := collectedAmount;
		END IF;

		ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
																			   3,
																			   ARRAY['tt_PAID_tt', '', CAST(round(paidAmount, 2) AS text)],
																			   ARRAY['l', 'r', 'r']) || chr(13);

		IF change > 0 THEN				
			ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
																			   3,
																			   ARRAY['tt_CHANGE_tt', '', CAST(round(change, 2) AS text)],
																			   ARRAY['l', 'r', 'r']) || chr(13);
		END IF;
		
		ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
																				   3,
																				   ARRAY['tt_DEBT_tt', '', CAST(round(orderAmount - collectedAmount, 2) AS text)],
																				   ARRAY['l', 'r', 'r']) || chr(13) || chr(13);

		ticket := COALESCE(pageHeader, '') || chr(13) || chr(13) || 
					'tt_ID_tt' || ' ' || CAST(orderid AS text) || chr(13) ||		
					'tt_TIME_tt' || ' ' || orderTime || chr(13) ||
					'tt_COUNTERS_tt' || ' ' || counter || chr(13) || chr(13) || ticket;

		--it's a delivery
		IF providerName IS NOT NULL THEN

			IF providerIsReseller THEN

				SELECT "This_has_CompensateDeliveryProviderRates"
					FROM soberano."Configuration" conf
						INTO compensateDeliveryProviderRates;

				IF compensateDeliveryProviderRates THEN

					deliveryAmount := deliveryAmount * 100 / (100  + deliveryProviderRate);

					orderAmount := orderAmount - 2 * deliveryAmount;
				
				END IF;
			END IF;

			ticket := ticket ||
					'tt_TO_BE_DELIVERED_TO_tt' || chr(13) || chr(13) ||
					customerName || chr(13) ||
					deliveryTo || chr(13) || chr(13) || 
					'tt_DELIVERY_BY_tt' || ' ' || providerName || chr(13) || chr(13) ||				
					soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
																   3,
																   ARRAY['tt_DELIVERY_FEE_tt', '', CAST(round(deliveryAmount, 2) AS text)],
																   ARRAY['l', 'r', 'r']) || chr(13);				
			IF providerIsReseller THEN
				ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
																				   3,
																				   ARRAY['tt_TOTAL_AMOUNT_tt', '', CAST(round(orderAmount + deliveryAmount, 2) AS text)],
																				   ARRAY['l', 'r', 'r']) || chr(13) || chr(13);
			ELSE
				ticket := ticket || soberano."fn_Utilities_formatColumnedTextLine"(printerProfile, 
																				   3,
																				   ARRAY['tt_TOTAL_AMOUNT_tt', '', CAST(round(orderAmount , 2) AS text)],
																				   ARRAY['l', 'r', 'r']) || chr(13) || chr(13);
			END IF;
		END IF;
		ticket := ticket || COALESCE(pageFooter, '');	
	END IF;
	RETURN QUERY SELECT ticket, printerProfile;
END;
$BODY$;