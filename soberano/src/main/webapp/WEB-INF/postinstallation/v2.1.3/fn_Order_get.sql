CREATE OR REPLACE FUNCTION soberano."fn_Order_get"(
	lang character,
	orderid integer,
	loginname character varying)
    RETURNS TABLE("orderId" integer, "orderLabel" character varying, counters text, customer text, "customerId" integer, 
					"deliverTo" text, "orderDiscount" numeric, "orderAmount" numeric, "collectedAmount" numeric, 
					"amountToCollect" numeric, category character varying, description character varying, 
					"processRunId" integer, "productName" character varying, "productQuantity" numeric, 
					"productUnit" character varying, "orderedRuns" numeric, "canceledRuns" numeric, 
					"discountedRuns" numeric, "endedRuns" numeric, currency character varying, stage character varying, 
					"stageId" integer, "oneRunQuantity" numeric, "inventoryItemCode" character varying, 
					"deliveryBy" character varying, "mobilePhoneNumber" character varying, "emailAddress" character varying,
					"countryCode" character, address character varying, "postalCode" character varying, 
					town character varying, "municipalityId" integer, city character varying, "provinceId" integer, 
					latitude double precision, longitude double precision, "printerProfile" integer, 
					"thisIsAnAdditionOf" integer, "workerColor" character varying, "workerLoginName" character varying,
					"salesAgent" integer, "salesAgentName" character varying, "salesAgentCommission" double precision) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 100

AS $BODY$
	DECLARE
		amount numeric;
		cAmount numeric;
		aTCollect numeric;
		
		customerPP integer;
		cashRegisterPP integer;
	BEGIN
		SELECT * 
			FROM soberano."fn_Order_getAmounts"(orderid)
			INTO amount, cAmount, aTCollect;
			
		IF amount IS NULL THEN amount := CAST(0 AS numeric); END IF;
		IF cAmount IS NULL THEN cAmount := CAST(0 AS numeric); END IF;
		IF aTCollect IS NULL THEN aTCollect := CAST(0 AS numeric); END IF;
		
		SELECT "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId"
			FROM soberano."Customer" cust
				INNER JOIN soberano."CustomerOrder" custo
					ON custo."CustomerHasCustomerId" = cust."CustomerHasCustomerId"
					 AND custo."OrderHasOrderId" = orderid
			LIMIT 1
			INTO customerPP;
			
		SELECT "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId"
			FROM soberano."CashRegister" 
			LIMIT 1
			INTO cashRegisterPP;
			
		RETURN QUERY SELECT o."OrderHasOrderId",
						o."This_is_identified_by_Label",
						occ.counters,
						c."This_has_FirstName" || ' ' || c."This_has_LastName" || ' : ' || cdata."This_includes_EmailAddress",
						c."CustomerHasCustomerId",
						'Tel: ' || ddata."This_includes_PhoneNumber" || chr(13) ||
						'Email: ' || ddata."This_includes_EmailAddress" || chr(13) ||
						'Address: ' || addr."This_includes_AddressString" || ', ' || 
										addr."This_includes_Town" || ', ' || 
										addr."This_includes_PostalCode"	|| ', ' || 
										mun."This_has_Name"	|| ', ' || 
										prov."This_has_Name" || ', ' || 
										ctry."This_has_Name",
						o."This_is_granted_a_DiscountRate",
						amount,
						cAmount,
						aTCollect,
						cat."This_has_Name",
						opr."This_has_Description",
						opr."ProcessRunHasProcessRunId",
						iitem."This_has_Name",
						CASE WHEN SUM(opr."This_has_ordered_Runs") > 0 THEN SUM(pro."Quantity") - SUM(opr."This_has_customer-canceled_Runs") * SUM(pro."Quantity") / SUM(opr."This_has_ordered_Runs")
																		ELSE 0 END,
						unit."Acronym",
						SUM(opr."This_has_ordered_Runs"),
						SUM(opr."This_has_customer-canceled_Runs"),
						SUM(opr."This_has_full_discounted_Runs"),
						SUM(opr."This_has_ended_Runs"),
						curr."This_has_Currency_code",
						st."This_has_Name",						
						CASE
							--order closed but not collected yet
							WHEN st."StageHasStageId" = 6 AND amount > cAmount THEN 0 ELSE st."StageHasStageId" END,
						procout."Quantity",
						iitem."InventoryItemHasInventoryItemCode",
						dp."This_has_Name",
						ddata."This_includes_PhoneNumber",
						ddata."This_includes_EmailAddress",
						ctry."CountryHasCountryCode",
						addr."This_includes_AddressString",
						addr."This_includes_PostalCode",
						addr."This_includes_Town",
						mun."MunicipalityHasMunicipalityId",
						addr."This_includes_City",
						prov."ProvinceHasProvinceId",
						addr."This_includes_Latitute",
						addr."This_includes_Longitude",
						
						CASE WHEN st."StageHasStageId" = 3 THEN
						
							--ongoing order
							(CASE WHEN customerPP IS NULL THEN occ."printerProfileId"
									WHEN customerPP = 0 THEN occ."printerProfileId"
									ELSE customerPP END)
									
							--otherwise, print to cash register printer
							ELSE cashRegisterPP
							END,
						"This_is_an_addition_of_OrderProcessRun_with_ProcessRunHasProces",
						"Color_identifies_This",
						worker."This_has_LoginName",
						sa."SalesAgent_ProviderHasProviderId",
						saprov."This_has_Name",
						sa."This_carries_a_Commission"
					FROM soberano."Order" o
						INNER JOIN (SELECT "OrderHasOrderId",
											STRING_AGG("This_has_Counter_code" || ', ',
														''
														ORDER BY "OrderHasOrderId", "This_has_Counter_code") counters,
											COALESCE(MIN("This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId"), 
													 COALESCE(mapp."PrinterProfileHasPrinterProfileId", 
															  depp."PrinterProfileHasPrinterProfileId")) "printerProfileId"
										FROM soberano."OrderOccupiesCounter" ocou
											INNER JOIN soberano."Counter" coun
												ON coun."CounterHasCounterId" = ocou."CounterHasCounterId"
											LEFT JOIN soberano."PrinterProfile" depp
												ON depp."PrinterProfile_is_default_printer_profile"
											LEFT JOIN soberano."PrinterProfile" mapp
												ON mapp."PrinterProfile_is_used_by_management"
										GROUP BY "OrderHasOrderId",
												mapp."PrinterProfileHasPrinterProfileId",
								   				depp."PrinterProfileHasPrinterProfileId") occ
							ON o."OrderHasOrderId" = occ."OrderHasOrderId"
						LEFT JOIN soberano."CustomerOrder" cust
							ON cust."OrderHasOrderId" = o."OrderHasOrderId"
						LEFT JOIN soberano."OrderProcessRun" opr
							ON opr."OrderHasOrderId" = o."OrderHasOrderId"
								AND opr."This_has_Description" != 'subprocess'
						LEFT JOIN soberano."ProcessRunOutputHasPriceForOrder" prophfo
							ON prophfo."OrderHasOrderId" = o."OrderHasOrderId"
								AND opr."ProcessRunHasProcessRunId" = prophfo."ProcessRunHasProcessRunId"
						LEFT JOIN soberano."InventoryItem" iitem
							ON iitem."InventoryItemHasInventoryItemCode" = prophfo."InventoryItemHasInventoryItemCode"
						LEFT JOIN soberano."Product" product
							ON product."InventoryItemHasInventoryItemCode" = iitem."InventoryItemHasInventoryItemCode"
						LEFT JOIN soberano."ProductIsOfProductCategory" pcat
							ON pcat."ProductHasProductId" = product."ProductHasProductId"
						LEFT JOIN soberano."ProductCategory" cat
							ON pcat."ProductCategoryHasProductCategoryId" = cat."ProductCategoryHasProductCategoryId"
						LEFT JOIN soberano."ProcessRunOutput" pro
							ON pro."ProcessRunHasProcessRunId" = prophfo."ProcessRunHasProcessRunId"
								AND pro."InventoryItemHasInventoryItemCode" = iitem."InventoryItemHasInventoryItemCode"
						LEFT JOIN soberano."ProcessRun" processrun
							ON processrun."ProcessRunHasProcessRunId" = pro."ProcessRunHasProcessRunId"

						LEFT JOIN metamodel."EntityTypeInstance" etipr
							ON processrun."This_is_identified_by_EntityTypeInstance_id" = etipr."EntityTypeInstanceHasEntityTypeInstanceId"
						LEFT JOIN metamodel."User" us
							ON us."UserHasUserId" = etipr."This_is_created_by_User_with_UserHasUserId"
						LEFT JOIN soberano."Worker" worker
							ON worker."This_has_LoginName" = us."This_has_LoginName"
							
						LEFT JOIN soberano."ProcessOutput" procout
							ON procout."ProcessHasProcessId" = processrun."This_is_of_Process_with_ProcessHasProcessId"
								AND procout."InventoryItemHasInventoryItemCode" = pro."InventoryItemHasInventoryItemCode"
						LEFT JOIN soberano."Unit" u
							ON u."UnitHasUnitId" = pro."This_is_measured_in_Unit_with_UnitHasUnitId"
						LEFT JOIN soberano."UnitHasAcronymInLanguage" unit
							ON unit."UnitHasUnitId" = u."UnitHasUnitId"
								AND unit."Language" = lang
						LEFT JOIN soberano."Currency" curr
							ON curr."CurrencyHasCurrencyId" = prophfo."This_is_in_Currency_with_CurrencyHasCurrencyId"
						LEFT JOIN metamodel."EntityTypeInstance" eti
							ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = o."This_is_identified_by_EntityTypeInstance_id"
						LEFT JOIN metamodel."Stage" st
							ON eti."This_is_in_Stage_with_StageHasStageId" = st."StageHasStageId"
						LEFT JOIN soberano."Customer" c
							ON c."CustomerHasCustomerId" = cust."CustomerHasCustomerId"
						LEFT JOIN soberano."ContactData" cdata
							ON cdata."ContactDataHasContactDataId" = c."This_has_ContactData_with_ContactDataHasContactDataId"
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
						LEFT JOIN soberano."SalesAgentOrderCapture" sa
							ON sa."OrderHasOrderId" = o."OrderHasOrderId"
						LEFT JOIN soberano."Provider" saprov
							ON sa."SalesAgent_ProviderHasProviderId" = saprov."ProviderHasProviderId"
					WHERE o."OrderHasOrderId" = orderid
						AND EXISTS (SELECT metamodel."fn_EntityTypeInstance_getDecisions"(o."This_is_identified_by_EntityTypeInstance_id", loginname))
					GROUP BY o."OrderHasOrderId",
							o."This_is_identified_by_Label",
							occ.counters,
							c."This_has_FirstName" || ' ' || c."This_has_LastName" || ' : ' || cdata."This_includes_EmailAddress",
							c."CustomerHasCustomerId",
							o."This_is_granted_a_DiscountRate",
							cat."This_has_Name",
							opr."This_has_Description",
							opr."ProcessRunHasProcessRunId",
							iitem."This_has_Name",
							unit."Acronym",
							curr."This_has_Currency_code",
							st."This_has_Name",
							st."StageHasStageId",
							ddata."This_includes_PhoneNumber",
							ddata."This_includes_EmailAddress",
							addr."This_includes_AddressString",
							addr."This_includes_Town",
							addr."This_includes_PostalCode",
							mun."This_has_Name",
							prov."This_has_Name",
							ctry."This_has_Name",
							procout."Quantity",
							iitem."InventoryItemHasInventoryItemCode",
							dp."This_has_Name",
							ctry."CountryHasCountryCode",
							mun."MunicipalityHasMunicipalityId",
							addr."This_includes_City",
							prov."ProvinceHasProvinceId",
							addr."This_includes_Latitute",
							addr."This_includes_Longitude",
							occ."printerProfileId",
							"Color_identifies_This",
							worker."This_has_LoginName",
							sa."SalesAgent_ProviderHasProviderId",
							saprov."This_has_Name",
							sa."This_carries_a_Commission"
					ORDER BY "This_is_an_addition_of_OrderProcessRun_with_ProcessRunHasProces" DESC, --additions have a non-null value in this field
																									--so, additions are moved to the end of the set
							cat."This_has_Name",
							opr."This_has_Description",
							iitem."This_has_Name";
	END;	
$BODY$;