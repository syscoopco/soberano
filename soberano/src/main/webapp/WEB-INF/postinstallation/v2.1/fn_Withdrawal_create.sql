CREATE OR REPLACE FUNCTION soberano."fn_Withdrawal_create"(
	cashregister integer,
	excludecash boolean,
	orderid integer,
	receivable integer,
	currencyids integer[],
	amounts numeric[],
	notes character varying,
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	DECLARE
		entityTypeInstanceId integer;
		withdrawalId integer;
		constraint12met boolean;
		systemCurrencyId integer;

		finalCashRegister integer;
	BEGIN
		--default returning value. user has no right.
		withdrawalId = -1;	
		
		--create entity type instance	
		SELECT metamodel."fn_EntityTypeInstance_create"('Withdrawal', loginname)
			INTO entityTypeInstanceId;
			
		--user has rights to create instance of that entity type
		IF entityTypeInstanceId > 0 THEN

			--the cash register operated by the worker has precedence over the passed cash register
			SELECT "This_operates_CashRegister_with_CashRegisterHasCashRegisterId"
				FROM soberano."Worker"
				WHERE "This_has_LoginName" = loginname
				INTO finalCashRegister;

			finalCashRegister := CASE WHEN finalCashRegister IS NULL THEN cashRegister ELSE finalCashRegister END;
		
			SELECT soberano."fn_CashRegister_RULE_CONSTRAINT_12"(currencyIds, finalCashRegister, excludeCash, loginname) INTO constraint12met;
		
			IF constraint12met THEN
			
				SELECT "CurrencyHasCurrencyId" 
					FROM soberano."Currency" 
					WHERE "Currency_is_system_currency"
					INTO systemCurrencyId;
			
				INSERT INTO soberano."Withdrawal"("This_is_identified_by_EntityTypeInstance_id", 
											   "This_is_from_CashRegister_with_CashRegisterHasCashRegisterId", 
											   "This_is_for_Order_with_OrderHasOrderId", 
											   "This_is_for_Receivable_with_ReceivableHasReceivableId",
											   "ExchangeRate_is_used_from_order_currency_on_This",
											   "Currency_with_CurrencyHasCurrencyId_is_system_currency_on_This",
											   "This_has_Note")
					SELECT entityTypeInstanceId,
							   finalCashRegister,
							   orderId,
							   receivable,

							   --any order output price row serves for taking the exchange rate from the order currency
							   --to the current system currency. the agreed price is in the currency that was the 
							   --system currency upon opening the order. so MAX, since usually an order has several items,
							   --every of them priced in the same currency
							   MAX("This_is_converted_to_system_currency_multiplying_by_ExchangeRat"),
							   systemCurrencyId,
							   notes
							FROM soberano."Order" ord
								INNER JOIN soberano."ProcessRunOutputHasPriceForOrder" price
									ON ord."OrderHasOrderId" = price."OrderHasOrderId"
										AND ord."OrderHasOrderId" = orderId
								INNER JOIN soberano."Currency" curr
									ON curr."CurrencyHasCurrencyId" = price."This_is_in_Currency_with_CurrencyHasCurrencyId"
					RETURNING "WithdrawalHasWithdrawalId" INTO withdrawalId;
					
				IF array_lower(currencyIds, 1) IS NOT NULL AND array_upper(currencyIds, 1) IS NOT NULL THEN
			
					FOR i IN array_lower(currencyIds, 1) .. array_upper(currencyIds, 1) LOOP
					
						INSERT INTO soberano."WithdrawalAmount"("Amount", 
															 "This_uses_ExchangeRate", 
															 "WithdrawalHasWithdrawalId", 
															 "CurrencyHasCurrencyId")
							SELECT amounts[i],
									"This_is_converted_to_system_currency_multiplying_by_ExchangeRat",
									withdrawalId,
									currencyIds[i]
									FROM soberano."Currency"
										WHERE "CurrencyHasCurrencyId" = currencyIds[i];
										
						PERFORM soberano."fn_CashRegister_updateBalance"(2,
																	 finalCashRegister,
																	 currencyIds[i],
																	 amounts[i]);
					END LOOP;

				END IF;
			
			ELSE
			
				withdrawalId := -2;
			
			END IF;
			
		END IF;
		RETURN withdrawalId;
END;
$BODY$;