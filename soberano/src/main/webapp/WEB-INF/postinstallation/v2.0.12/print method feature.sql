ALTER TABLE IF EXISTS soberano."PrinterProfile"
    ADD COLUMN "This_uses_PrintMethod" smallint;
    
UPDATE soberano."PrinterProfile"
	SET "This_uses_PrintMethod"=0;

CREATE OR REPLACE FUNCTION soberano."fn_PrinterProfile_create"(
	printerprofilename character varying,
	fontsize integer,
	pagewidth integer,
	pageheight integer,
	margin integer,
	pageheader character varying,
	pagefooter character varying,
	compactformat boolean,
	isdefaultprinter boolean,
	ismanagementprinter boolean,
	printserver character varying,	
	printername character varying,
	printmethod smallint,
	objectusingthisids integer[],
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	DECLARE
		entityTypeInstanceId integer;
		itemId integer;
		oldDefaultPrinterProfile integer;
		entityTypeMeaningId character varying;
	BEGIN
		--default returning value. user has no right.
		itemId = -1;
		
		--create entity type instance	
		SELECT metamodel."fn_EntityTypeInstance_create"('PrinterProfile', loginname)
			INTO entityTypeInstanceId;
			
		--user has rights to create instance of that entity type
		IF entityTypeInstanceId > 0 THEN

			SELECT "PrinterProfileHasPrinterProfileId" FROM soberano."PrinterProfile" 
				WHERE "PrinterProfile_is_default_printer_profile" INTO oldDefaultPrinterProfile;

			--at most one 'PrinterProfile' can be the default printer profile.
			--at most one 'PrinterProfile' can be the management printer profile. 
			PERFORM soberano."fn_PrinterProfile_RULE_CONSTRAINTS_16_AND_17"(isDefaultPrinter, 
																			isManagementPrinter);
																			
			INSERT INTO soberano."PrinterProfile"("This_has_Name",
												  "This_uses_FontSize", 
												  "This_uses_PageWidth", 
												  "This_uses_PageHeight", 
												  "This_uses_Margin", 
												  "This_is_identified_by_EntityTypeInstance_id", 
												  "PrinterProfile_compacts_text", 
												  "PrinterProfile_is_used_by_management", 
												  "PrinterProfile_is_default_printer_profile", 
												  "This_has_PrintServer",
												  "This_has_PrinterName", 
												  "This_uses_PrintMethod",
												  "This_has_PageHeader", 
												  "This_has_PageFooter")
				VALUES (printerProfileName,
					   fontSize,
					   pageWidth,
					   pageHeight,
					   margin,
					   entityTypeInstanceId,
					   compactFormat,
					   isManagementPrinter,
					   isDefaultPrinter,
					   printserver,
					   printername,
					   printmethod,
					   pageHeader,
					   pageFooter)
				RETURNING "PrinterProfileHasPrinterProfileId" 
				INTO itemId;
							
			IF array_lower(objectUsingThisIds, 1) IS NOT NULL AND array_upper(objectUsingThisIds, 1) IS NOT NULL THEN
			
				FOR i IN array_lower(objectUsingThisIds, 1) .. array_upper(objectUsingThisIds, 1) LOOP
				
					SELECT "This_is_instance_of_EntityType_with_MeaningHasMeaningId"
						FROM metamodel."EntityTypeInstance"
						WHERE "EntityTypeInstanceHasEntityTypeInstanceId" = objectUsingThisIds[i]
						INTO entityTypeMeaningId;
					
					--cash register
					IF entityTypeMeaningId = '_7E0F5DAA-21DB-433E-BFBF-F4437F3E0FE9' THEN
						UPDATE soberano."CashRegister"
							SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId
							WHERE "This_is_identified_by_EntityTypeInstance_id" = objectUsingThisIds[i];
							
					--cost center
					ELSIF entityTypeMeaningId = '_BF752D2F-A23F-4764-BFE3-8B3ED196AB88' THEN
						UPDATE soberano."CostCenter"
							SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId
							WHERE "This_is_identified_by_EntityTypeInstance_id" = objectUsingThisIds[i];
							
					--counter
					ELSIF entityTypeMeaningId = '_0126EFEB-6646-4C9C-B0C2-28EB3B25E3A7' THEN
						UPDATE soberano."Counter"
							SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId
							WHERE "This_is_identified_by_EntityTypeInstance_id" = objectUsingThisIds[i];
							
					--production line
					ELSIF entityTypeMeaningId = '_D6394DC1-F701-4B68-96A3-8167D217F6E8' THEN
						UPDATE soberano."ProductionLine"
							SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId
							WHERE "This_is_identified_by_EntityTypeInstance_id" = objectUsingThisIds[i];
							
					--warehouse
					ELSIF entityTypeMeaningId = '_F6E70C11-9ABC-469E-8468-930AAC301E70' THEN					
						UPDATE soberano."Warehouse"
							SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId
							WHERE "This_is_identified_by_EntityTypeInstance_id" = objectUsingThisIds[i];
					END IF;
				END LOOP;
			END IF;
		END IF;
		RETURN itemId;
END;
$BODY$;

CREATE OR REPLACE FUNCTION soberano."fn_PrinterProfile_get"(
	itemid integer,
	loginname character varying)
    RETURNS TABLE("printerProfileId" integer, "entityTypeInstanceId" integer, "profileName" character varying, 
				"fontSize" integer, "pageWidth" integer, "pageHeight" integer, margin integer, 
				header character varying, footer character varying, "compactFormat" boolean, 
				"isDefaultPrinter" boolean, "isManagementPrinter" boolean, "printServer" character varying, 
				"printerName" character varying, "printMethod" smallint) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1

AS $BODY$
	BEGIN
		RETURN QUERY SELECT DISTINCT "PrinterProfileHasPrinterProfileId",
										"This_is_identified_by_EntityTypeInstance_id",
										"This_has_Name",
										"This_uses_FontSize",
										"This_uses_PageWidth",
										"This_uses_PageHeight",
										"This_uses_Margin",
										"This_has_PageHeader",
										"This_has_PageFooter",
										"PrinterProfile_compacts_text",
										"PrinterProfile_is_default_printer_profile",
										"PrinterProfile_is_used_by_management",
										"This_has_PrintServer",
										"This_has_PrinterName",
										"This_uses_PrintMethod"
								FROM soberano."PrinterProfile" pprof
								WHERE pprof."PrinterProfileHasPrinterProfileId" = itemid
									AND EXISTS (SELECT metamodel."fn_EntityTypeInstance_getDecisions"("This_is_identified_by_EntityTypeInstance_id", loginname));
	END;	
$BODY$;

CREATE OR REPLACE FUNCTION soberano."fn_PrinterProfile_modify"(
	itemid integer,
	printerprofilename character varying,
	fontsize integer,
	pagewidth integer,
	pageheight integer,
	margin integer,
	pageheader character varying,
	pagefooter character varying,
	compactformat boolean,
	isdefaultprinter boolean,
	ismanagementprinter boolean,
	printserver character varying,
	printername character varying,
	printmethod smallint,
	objectusingthisids integer[],
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	DECLARE
		entityTypeInstanceId integer;
		decisionId integer;
		objectCode character varying;
		qryResult integer;
		oldDefaultPrinterProfile integer;
		rules16and17Met boolean;
		entityTypeMeaningId character varying;
		
	BEGIN
		--default returning value. user has no right.
		qryResult := -1;
		
		SELECT "This_is_identified_by_EntityTypeInstance_id", 
					objectdata."This_has_Name",
					decision."DecisionHasDecisionId" 
				FROM soberano."PrinterProfile" objectdata
					INNER JOIN (SELECT "DecisionHasDecisionId", 
										"This_has_Name" 
									FROM metamodel."Decision") decision
						ON decision."DecisionHasDecisionId" = 
							(SELECT "DecisionId" 
								FROM metamodel."fn_EntityTypeInstance_getDecisions"(
											objectdata."This_is_identified_by_EntityTypeInstance_id",
											loginname)
								WHERE "DecisionName" =

									/************/
										'Apply'
									/************/						   

							   )
				WHERE "PrinterProfileHasPrinterProfileId" = itemid
				INTO entityTypeInstanceId,
					objectCode,
					decisionId;
					
		--user has rights
		IF decisionId IS NOT NULL AND entityTypeInstanceId IS NOT NULL THEN
		
			SELECT "PrinterProfileHasPrinterProfileId" FROM soberano."PrinterProfile" 
				WHERE "PrinterProfile_is_default_printer_profile" INTO oldDefaultPrinterProfile;
				
			
			--at most one 'PrinterProfile' can be the default printer profile.
			--at most one 'PrinterProfile' can be the management printer profile. 
			PERFORM soberano."fn_PrinterProfile_RULE_CONSTRAINTS_16_AND_17"(isDefaultPrinter, 
																			isManagementPrinter);
																			
			UPDATE soberano."PrinterProfile" SET "This_has_Name" = printerProfileName,
												  "This_uses_FontSize" = fontSize,
												  "This_uses_PageWidth" = pageWidth,
												  "This_uses_PageHeight" = pageHeight,
												  "This_uses_Margin" = margin,
												  "PrinterProfile_compacts_text" = compactFormat,
												  "PrinterProfile_is_used_by_management" = isManagementPrinter,
												  "PrinterProfile_is_default_printer_profile" = isDefaultPrinter,
												  "This_has_PrintServer" = printserver,
												  "This_has_PrinterName" = printername,
												  "This_uses_PrintMethod" = printmethod,
												  "This_has_PageHeader" = pageHeader,
												  "This_has_PageFooter" = pageFooter
				WHERE "PrinterProfileHasPrinterProfileId" = itemId;
				
			UPDATE soberano."CashRegister"
				SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = NULL
				WHERE "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId;
			UPDATE soberano."CostCenter"
				SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = NULL
				WHERE "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId;
			UPDATE soberano."Counter"
				SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = NULL
				WHERE "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId;
			UPDATE soberano."ProductionLine"
				SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = NULL
				WHERE "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId;
			UPDATE soberano."Warehouse"
				SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = NULL
				WHERE "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId;
			 
			IF array_lower(objectUsingThisIds, 1) IS NOT NULL AND array_upper(objectUsingThisIds, 1) IS NOT NULL THEN
			
				FOR i IN array_lower(objectUsingThisIds, 1) .. array_upper(objectUsingThisIds, 1) LOOP
				
					SELECT "This_is_instance_of_EntityType_with_MeaningHasMeaningId"
						FROM metamodel."EntityTypeInstance"
						WHERE "EntityTypeInstanceHasEntityTypeInstanceId" = objectUsingThisIds[i]
						INTO entityTypeMeaningId;
					
					--cash register
					IF entityTypeMeaningId = '_7E0F5DAA-21DB-433E-BFBF-F4437F3E0FE9' THEN
						UPDATE soberano."CashRegister"
							SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId
							WHERE "This_is_identified_by_EntityTypeInstance_id" = objectUsingThisIds[i];
							
					--cost center
					ELSIF entityTypeMeaningId = '_BF752D2F-A23F-4764-BFE3-8B3ED196AB88' THEN
						UPDATE soberano."CostCenter"
							SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId
							WHERE "This_is_identified_by_EntityTypeInstance_id" = objectUsingThisIds[i];
							
					--counter
					ELSIF entityTypeMeaningId = '_0126EFEB-6646-4C9C-B0C2-28EB3B25E3A7' THEN
						UPDATE soberano."Counter"
							SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId
							WHERE "This_is_identified_by_EntityTypeInstance_id" = objectUsingThisIds[i];
							
					--production line
					ELSIF entityTypeMeaningId = '_D6394DC1-F701-4B68-96A3-8167D217F6E8' THEN
						UPDATE soberano."ProductionLine"
							SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId
							WHERE "This_is_identified_by_EntityTypeInstance_id" = objectUsingThisIds[i];
							
					--warehouse
					ELSIF entityTypeMeaningId = '_F6E70C11-9ABC-469E-8468-930AAC301E70' THEN					
						UPDATE soberano."Warehouse"
							SET "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId" = itemId
							WHERE "This_is_identified_by_EntityTypeInstance_id" = objectUsingThisIds[i];
					END IF;
				END LOOP;
			END IF;
																			
			--make the decision
			PERFORM metamodel."fn_Vote_vote"(loginname,
											entityTypeInstanceId, 
											decisionId, 
											'tt_PRINTER_PROFILE_tt ' || CAST(itemid AS text) || ' : ' || objectCode || chr(13) || ' tt_MODIFIED_BY_tt ' || loginname);

			qryResult := 0;		
		END IF;
		RETURN qryResult;
END;
$BODY$;

