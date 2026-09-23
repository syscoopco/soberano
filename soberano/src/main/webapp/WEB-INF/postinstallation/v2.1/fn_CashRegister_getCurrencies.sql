CREATE OR REPLACE FUNCTION soberano."fn_CashRegister_getCurrencies"(
	cashregisterid integer,
	excludecash boolean,
	loginname character varying)
    RETURNS TABLE("itemId" integer, "entityTypeInstanceId" integer, "itemName" character varying, "itemCode" character varying, "isSystemCurrency" boolean, "isPriceReferenceCurrency" boolean, "isCash" boolean, "exchangeRate" numeric, "itemPosition" integer, "paymentProcessor" integer, "paymentProcessorName" character varying) 
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
		
		RETURN QUERY SELECT DISTINCT curr."CurrencyHasCurrencyId",
										"This_is_identified_by_EntityTypeInstance_id",
										curr."This_has_Name",
										"This_has_Currency_code",
										"Currency_is_system_currency",
										"Currency_is_price_reference_currency",
										"Currency_is_cash",
										"This_is_converted_to_system_currency_multiplying_by_ExchangeRat",
										"This_is_shown_in_Position",
										pp."PaymentProcessorHasPaymentProcessorId",
										pp."This_has_Name"
								FROM soberano."Currency" curr
									INNER JOIN
										soberano."PaymentProcessorIsUsedForCurrency" ppc
											ON curr."CurrencyHasCurrencyId" = ppc."CurrencyHasCurrencyId"
									INNER JOIN soberano."PaymentProcessor" pp
										ON pp."PaymentProcessorHasPaymentProcessorId" = ppc."PaymentProcessorHasPaymentProcessorId"
									INNER JOIN
										metamodel."EntityTypeInstance" eti
											ON curr."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId"
												
												--only enabled currencies
												AND eti."This_is_in_Stage_with_StageHasStageId" = 2
								WHERE (curr."Currency_is_cash" AND NOT excludeCash)
										OR NOT curr."Currency_is_cash"
									AND (--user is allowed to deposit, withdraw, balance, or it is an auditor
											1 IN (SELECT * FROM metamodel."fn_User_canCreateInstance"(1, '_87225364-61F0-4563-B1BC-E601F83D0B6E', loginname))	
											OR 1 IN (SELECT * FROM metamodel."fn_User_canCreateInstance"(1, '_5C00E9C9-05FC-4B07-A1F4-A679E4A52D6D', loginname))
											OR 1 IN (SELECT * FROM metamodel."fn_User_canCreateInstance"(1, '_41FC249B-08AC-4DC8-A024-431318812945', loginname))	
											OR EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(23, 1, loginname))
											OR EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(24, 1, loginname))
											OR EXISTS(SELECT * FROM metamodel."fn_EntityTypeInstance_getDecisions"(26, 1, loginname)))
								ORDER BY "This_is_shown_in_Position"; 
	END;	
$BODY$;