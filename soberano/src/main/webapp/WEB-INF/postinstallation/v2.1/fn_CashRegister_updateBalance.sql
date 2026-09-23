CREATE OR REPLACE FUNCTION soberano."fn_CashRegister_updateBalance"(
	operation integer,
	cashregister integer,
	currencyid integer,
	amount numeric)
    RETURNS void
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	BEGIN
		IF NOT EXISTS(SELECT * 
					  	FROM soberano."CashRegisterHasBalanceInCurrency"
					 	WHERE "CurrencyHasCurrencyId" = currencyid
							AND "CashRegisterHasCashRegisterId" = cashregister) THEN
			
			INSERT INTO soberano."CashRegisterHasBalanceInCurrency"("Balance", 
																	"CurrencyHasCurrencyId", 
																	"CashRegisterHasCashRegisterId")
				SELECT CASE operation 
							WHEN 1 THEN amount
							WHEN 2 THEN -amount
							WHEN 3 THEN amount END,
						currencyid,
					   	cashregister;
		ELSE
			UPDATE soberano."CashRegisterHasBalanceInCurrency"
				SET "Balance" = CASE operation 
									WHEN 1 THEN "Balance" + amount
									WHEN 2 THEN "Balance" - amount
									WHEN 3 THEN amount END
				WHERE "CashRegisterHasCashRegisterId" = cashregister
					AND "CurrencyHasCurrencyId" = currencyid;
		END IF;
	END;
$BODY$;