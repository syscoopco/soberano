CREATE OR REPLACE FUNCTION soberano."fn_Balancing_create"(
	cashregister integer,
	excludecash boolean,
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
		balancingId integer;
		constraint12met boolean;

		finalCashRegister integer;
	BEGIN
		--default returning value. user has no right.
		balancingId = -1;	
		
		--create entity type instance	
		SELECT metamodel."fn_EntityTypeInstance_create"('Balancing', loginname)
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
			
				INSERT INTO soberano."Balancing"("This_is_identified_by_EntityTypeInstance_id", 
											   "This_is_of_CashRegister_with_CashRegisterHasCashRegisterId",
											   "This_has_Note")
					VALUES (entityTypeInstanceId,
						   finalCashRegister,
						   notes) 
					RETURNING "BalancingHasBalancingId" INTO balancingId;
					
				IF array_lower(currencyIds, 1) IS NOT NULL AND array_upper(currencyIds, 1) IS NOT NULL THEN
			
					FOR i IN array_lower(currencyIds, 1) .. array_upper(currencyIds, 1) LOOP
					
						INSERT INTO soberano."CountedBalance"("Balance", 
															 "BalancingHasBalancingId", 
															 "CurrencyHasCurrencyId")
							SELECT amounts[i],
									balancingId,
									currencyIds[i]
									FROM soberano."Currency"
										WHERE "CurrencyHasCurrencyId" = currencyIds[i];
										
						PERFORM soberano."fn_CashRegister_updateBalance"(3,
																	 finalCashRegister,
																	 currencyIds[i],
																	 amounts[i]);
					END LOOP;

				END IF;
			
			ELSE
			
				balancingId := -2;
			
			END IF;
			
		END IF;
		RETURN balancingId;
END;
$BODY$;