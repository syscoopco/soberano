CREATE OR REPLACE FUNCTION soberano."fn_CashRegister_get"(
	cashregisterid integer,
	loginname character varying)
    RETURNS TABLE("cashRegisterId" integer, "entityTypeInstanceId" integer, "printerProfile" integer, "currencyCode" character varying, balance numeric) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 50

AS $BODY$
	DECLARE
		finalCashRegister integer;
	BEGIN
		--the cash register operated by the worker has precedence over the passed cash register
		SELECT "This_operates_CashRegister_with_CashRegisterHasCashRegisterId"
			FROM soberano."Worker"
			WHERE "This_has_LoginName" = loginname
			INTO finalCashRegister;

		finalCashRegister := CASE WHEN finalCashRegister IS NULL THEN cashregisterid ELSE finalCashRegister END;
	
		RETURN QUERY SELECT crcurr."CashRegisterHasCashRegisterId",
							"This_is_identified_by_EntityTypeInstance_id",
							COALESCE("This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId", 
								COALESCE(managementPrinterProfile, defaultPrinterProfile)),
							"This_has_Currency_code",
							CASE WHEN "Balance" IS NULL THEN CAST(0 AS numeric) ELSE ROUND("Balance", 8) END
							FROM (SELECT "CashRegisterHasCashRegisterId", 
										cr."This_is_identified_by_EntityTypeInstance_id",
										"This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId",
								  		mapp."PrinterProfileHasPrinterProfileId" managementPrinterProfile,
								  		depp."PrinterProfileHasPrinterProfileId" defaultPrinterProfile,
										"CurrencyHasCurrencyId",
										"This_has_Currency_code"
									FROM soberano."CashRegister" cr 
								  		LEFT JOIN soberano."Currency"
								  			ON true
								  		LEFT JOIN soberano."PrinterProfile" mapp
								  			ON mapp."PrinterProfile_is_used_by_management"
								  		LEFT JOIN soberano."PrinterProfile" depp
								  			ON depp."PrinterProfile_is_default_printer_profile"							  		
									WHERE (--user is allowed to deposit, withdraw, balance, or it is an auditor
											1 IN (SELECT * FROM metamodel."fn_User_canCreateInstance"(1, '_87225364-61F0-4563-B1BC-E601F83D0B6E', loginname))	
											OR 1 IN (SELECT * FROM metamodel."fn_User_canCreateInstance"(1, '_5C00E9C9-05FC-4B07-A1F4-A679E4A52D6D', loginname))
											OR 1 IN (SELECT * FROM metamodel."fn_User_canCreateInstance"(1, '_41FC249B-08AC-4DC8-A024-431318812945', loginname))	
											OR EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(23, 1, loginname))
											OR EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(24, 1, loginname))
											OR EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(26, 1, loginname)))
										AND "CashRegisterHasCashRegisterId" = finalCashRegister
									ORDER BY "This_is_shown_in_Position") crcurr
								LEFT JOIN soberano."CashRegisterHasBalanceInCurrency" b
									ON crcurr."CashRegisterHasCashRegisterId" = b."CashRegisterHasCashRegisterId"
										AND b."CurrencyHasCurrencyId" = crcurr."CurrencyHasCurrencyId";
	END;	
$BODY$;