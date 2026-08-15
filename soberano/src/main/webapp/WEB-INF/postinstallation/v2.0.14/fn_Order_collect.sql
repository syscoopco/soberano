CREATE OR REPLACE FUNCTION soberano."fn_Order_collect"(
	cashregister integer,
	orderid integer,
	lang character,
	currencies integer[],
	amounts numeric[],
	tip numeric,
	notes character varying,
	customer integer,
	loginname character varying)
    RETURNS TABLE(res integer, ttp text, printerprofile integer) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1

    SET default_transaction_isolation='serializable'
AS $BODY$
	DECLARE
		constraint12met boolean;
		orderAmount numeric; --in order currency
		collectedAmount numeric; --in order currency
		toCollectAmount numeric; --in system currency
		orderEntityTypeInstanceId integer;
		orderCurrentStatageId integer;
		decisionIdToCollectOrder integer;
		passedCustomerExistsAndIsEnabled boolean;
		receivableEntityTypeInstanceId integer;
		decisionIdToCollectReceivable integer;
		currentDebtor integer;
		currentReceivableId integer;
		currentDebtorQualifiedName text;
		newReceivableMustBeRecorded boolean;
		receivableDishonored boolean;
		orderMustBeClosed boolean;
		decisionIdToDishonorReceivable integer;
		totalInputAmount numeric; --in system currency
		newReceivableEntityTypeInstanceId integer;
		newReceivableId integer;systemCurrencyId integer;
		systemCurrencyArrayIndex integer;
		withdrawalAmountsArray numeric[];
		changeAmount numeric; --in system currency
		qryResult integer;
		qryReport text;
		qryPrinterProfileId integer;
		
		customerPP integer;

		finalCashRegister integer;
	BEGIN
		--default returning values.
		qryResult := 0;
		qryReport := '';		
		newReceivableMustBeRecorded := false;
		receivableDishonored := false;
		orderMustBeClosed := false;

		--customization: in multi cash registers context, if the counter has a specified cash register
		--for collecting the payments, the cash register operations must be undertaken in that cash register.
		SELECT "Payments_collected_at_This_are_deposited_in_CashRegister_with_C"
			FROM soberano."Counter" counter
				INNER JOIN soberano."CounterOrder" counterorder
					ON counter."CounterHasCounterId" = counterorder."CounterHasCounterId"
						AND counterorder."OrderHasOrderId" = orderid
			INTO finalCashRegister;

		finalCashRegister := CASE WHEN finalCashRegister IS NULL THEN cashRegister ELSE finalCashRegister END;
		-----
		
		SELECT soberano."fn_CashRegister_RULE_CONSTRAINT_12"(currencies, 
															 finalCashRegister, 
															 false, 
															 loginname) INTO constraint12met;															 
		IF NOT constraint12met THEN		
			qryResult := -2; --DisabledCurrencyException
		ELSE
			--retrieve order's state
			SELECT proc.*
					FROM (SELECT objectdata."This_is_identified_by_EntityTypeInstance_id", 
										"This_is_in_Stage_with_StageHasStageId",
										decision."DecisionHasDecisionId",
						  				COALESCE(MIN(counter."This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId"), 
													 COALESCE(mapp."PrinterProfileHasPrinterProfileId", 
															  depp."PrinterProfileHasPrinterProfileId"))
									FROM soberano."Order" objectdata
										INNER JOIN metamodel."EntityTypeInstance" eti
											ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = objectdata."This_is_identified_by_EntityTypeInstance_id"
										INNER JOIN soberano."OrderOccupiesCounter" ooc
						  					ON ooc."OrderHasOrderId" = objectdata."OrderHasOrderId"
						  				INNER JOIN soberano."Counter" counter
						  					ON ooc."CounterHasCounterId" = counter."CounterHasCounterId"
						  				LEFT JOIN soberano."PrinterProfile" depp
												ON depp."PrinterProfile_is_default_printer_profile"
										LEFT JOIN soberano."PrinterProfile" mapp
											ON mapp."PrinterProfile_is_used_by_management"
						  				LEFT JOIN (SELECT "DecisionHasDecisionId", 
															"This_has_Name" 
														FROM metamodel."Decision") decision
											ON decision."DecisionHasDecisionId" = 
												(SELECT "DecisionId" 
													FROM metamodel."fn_EntityTypeInstance_getDecisions"(
																objectdata."This_is_identified_by_EntityTypeInstance_id",
																loginname)
													WHERE "DecisionName" =

														/************/
															'Collect'
														/************/						   

												   )
										LEFT JOIN soberano."CustomerOrder" ordercust
											ON ordercust."OrderHasOrderId" = objectdata."OrderHasOrderId"
									WHERE objectdata."OrderHasOrderId" = orderid
						 			GROUP BY objectdata."This_is_identified_by_EntityTypeInstance_id", 
											"This_is_in_Stage_with_StageHasStageId",
											decision."DecisionHasDecisionId",
						 					mapp."PrinterProfileHasPrinterProfileId", 
											depp."PrinterProfileHasPrinterProfileId") proc
						INTO orderEntityTypeInstanceId,
							orderCurrentStatageId,
							decisionIdToCollectOrder,
							qryPrinterProfileId;
			
			--retrieve order's amounts
			SELECT * 
				FROM soberano."fn_Order_getAmounts"(orderid)
				INTO orderAmount, collectedAmount, toCollectAmount;
			
			--calc passed amount in system currency
			SELECT COALESCE(SUM(amount * "This_is_converted_to_system_currency_multiplying_by_ExchangeRat"), 0)
					FROM (SELECT currency, ROW_NUMBER () OVER () rownumber FROM 
								(SELECT UNNEST(currencies) currency) cu) currsq
						INNER JOIN (SELECT amount, ROW_NUMBER () OVER () rownumber FROM 
								(SELECT UNNEST(amounts) amount) am) amtsq
							ON currsq.rownumber = amtsq.rownumber
						INNER JOIN soberano."Currency" curr
							ON curr."CurrencyHasCurrencyId" = currsq.currency
					INTO totalInputAmount;
			
			--validate passed customer
			SELECT EXISTS(SELECT * FROM soberano."Customer" cust
							  			INNER JOIN metamodel."EntityTypeInstance" eti
							  				ON cust."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId"
							   			--passed customer exists and it's enabled
							  			WHERE "CustomerHasCustomerId" = customer AND "This_is_in_Stage_with_StageHasStageId" = 2)
						INTO passedCustomerExistsAndIsEnabled;
			
			IF orderCurrentStatageId = 5 THEN
				qryResult := -5; --OrderCanceledException
			
			--order is closed
			ELSIF orderCurrentStatageId = 6 THEN
				
				--the order was already collected
				IF toCollectAmount <= 0 THEN
					qryResult := -4; --OrderAlreadyCollectedException
				ELSE
					--in this point, a receivable recorded in previous calls must be collected
				
					--so, a debtor is needed				
					IF NOT passedCustomerExistsAndIsEnabled THEN
						qryResult := -3; --DebtorRequiredException
					ELSE
						--retrieve the last order's receivable data.
						--always, the last recivable tied to the order is the one that is collected
						SELECT "This_is_identified_by_EntityTypeInstance_id", 
								"DecisionHasDecisionId",
								"Customer_with_CustomerHasCustomerId_is_the_debtor_of_This",
								receivableId,
								"This_has_FirstName" || ' ' || "This_has_LastName" || ' : ' || "This_includes_EmailAddress"
							FROM (SELECT objectdata."This_is_identified_by_EntityTypeInstance_id", 
										decision."DecisionHasDecisionId",
										"Customer_with_CustomerHasCustomerId_is_the_debtor_of_This",
										"This_is_of_Order_with_OrderHasOrderId",
										"This_has_FirstName",
										"This_has_LastName",
										"This_includes_EmailAddress",

										--last receivable
										MAX("ReceivableHasReceivableId") receivableId
									FROM soberano."Receivable" objectdata
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
															'Collect'
														/************/						   

												   )
										INNER JOIN soberano."Customer" customer
											ON customer."CustomerHasCustomerId" = objectdata."Customer_with_CustomerHasCustomerId_is_the_debtor_of_This"
										INNER JOIN soberano."ContactData" u
											ON u."ContactDataHasContactDataId" = customer."This_has_ContactData_with_ContactDataHasContactDataId"
										LEFT JOIN soberano."Order" ord
											ON objectdata."This_is_of_Order_with_OrderHasOrderId" = ord."OrderHasOrderId"
												AND ord."OrderHasOrderId" = orderid
									GROUP BY objectdata."This_is_identified_by_EntityTypeInstance_id", 
											"DecisionHasDecisionId",
											"Customer_with_CustomerHasCustomerId_is_the_debtor_of_This",
											"This_is_of_Order_with_OrderHasOrderId",
											"This_has_FirstName",
											"This_has_LastName",
											"This_includes_EmailAddress") proc
								INTO receivableEntityTypeInstanceId,
									decisionIdToCollectReceivable,
									currentDebtor,
									currentReceivableId,
									currentDebtorQualifiedName;
						
						--debtor changed
						IF COALESCE(customer != currentDebtor, false) THEN
							
							--a new receivable must be recorded
							newReceivableMustBeRecorded := true;
							receivableDishonored := true;
							
							--the current receivable must be marked as dishonored
							SELECT "DecisionHasDecisionId"
								FROM (SELECT decision."DecisionHasDecisionId"
										FROM soberano."Receivable" objectdata
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
																'Dishonor'
															/************/						   

													   )
										WHERE "ReceivableHasReceivableId" = currentReceivableId) proc
									INTO decisionIdToDishonorReceivable;
									
							IF decisionIdToDishonorReceivable IS NULL THEN
								qryResult := -1; --NotEnoughRightsException
							END IF;

						--same debtor, but the user has no right to collect a receivable
						ELSIF decisionIdToCollectReceivable IS NULL OR receivableEntityTypeInstanceId IS NULL THEN
							qryResult := -1; --NotEnoughRightsException
						END IF;						
					END IF;
				END IF;
			
			--order is ongoing
			ELSE
				--user isn't allowed to collect the order
				IF decisionIdToCollectOrder IS NULL THEN
					qryResult := -1; --NotEnoughRightsException
				ELSE
					orderMustBeClosed := true;
				
					--if the passed amount is lower than the amount to collect
					IF totalInputAmount < toCollectAmount THEN
						
						--upon collecting the order, it's required to record a receivable, so a debtor is needed
						IF NOT passedCustomerExistsAndIsEnabled THEN
							qryResult := -3; --DebtorRequiredException
						ELSE
							newReceivableMustBeRecorded := true;
						END IF;
					END IF;					
				END IF;
			END IF;
			
			--validation passed
			IF qryResult = 0 THEN
			
				SELECT "CurrencyHasCurrencyId",
								rownumber
							FROM (SELECT "CurrencyHasCurrencyId",
										"Currency_is_system_currency",
										ROW_NUMBER () OVER (ORDER BY "CurrencyHasCurrencyId" ASC) rownumber
									FROM soberano."Currency"
									ORDER BY "This_is_shown_in_Position" ASC) sq
							WHERE "Currency_is_system_currency"
							INTO systemCurrencyId, systemCurrencyArrayIndex;
			
				--if needed, record receivable
				IF newReceivableMustBeRecorded THEN
					
					--create receivable entity type instance	
					SELECT metamodel."fn_EntityTypeInstance_create"('Receivable', loginname)
						INTO newReceivableEntityTypeInstanceId;

					--rights to record receivable?
					IF newReceivableEntityTypeInstanceId < 0 THEN
						qryResult := -1; --NotEnoughRightsException
					ELSE
						INSERT INTO soberano."Receivable"("This_is_identified_by_EntityTypeInstance_id", 
														  "Customer_with_CustomerHasCustomerId_is_the_debtor_of_This", 
														  "This_is_of_Order_with_OrderHasOrderId")
							VALUES (newReceivableEntityTypeInstanceId,
								   customer,
								   orderId)
							RETURNING "ReceivableHasReceivableId" INTO newReceivableId;

						IF NOT receivableDishonored THEN
							INSERT INTO soberano."ReceivableIsForAmountInCurrency"("Amount", 
																			   "ReceivableHasReceivableId", 
																			   "CurrencyHasCurrencyId")
								VALUES (toCollectAmount - totalInputAmount, --order closing. new receivable must not be collected in this call to collect
										newReceivableId,
										systemCurrencyId);
						ELSE
							INSERT INTO soberano."ReceivableIsForAmountInCurrency"("Amount", 
																			   "ReceivableHasReceivableId", 
																			   "CurrencyHasCurrencyId")
								VALUES (toCollectAmount, --debtor changed. new receivable must be collected in this call to collect
										newReceivableId,
										systemCurrencyId);									
							
							--dishonor the previous receivable
							PERFORM metamodel."fn_Vote_vote"('soberano.user.top',
													receivableEntityTypeInstanceId, 
													decisionIdToDishonorReceivable,
													'tt_RECEIVABLE_tt ' || CAST(currentReceivableId AS text) || chr(13) || ' tt_DISHONORED_BY_tt ' || currentDebtorQualifiedName);
							
							--update the active receivable
							currentReceivableId := newReceivableId;
							receivableEntityTypeInstanceId := newReceivableEntityTypeInstanceId;
						END IF;
					END IF;
				END IF;
				
				--no new receivable required to be recorded, or the recording succeded
				IF qryResult = 0 THEN					
					IF orderMustBeClosed THEN					
						--make decision. order goes to 'Close' stage
						PERFORM metamodel."fn_Vote_vote"(loginname,
														orderEntityTypeInstanceId, 
														decisionIdToCollectOrder, 
														'tt_ORDER_tt ' || CAST(orderid AS text) || chr(13) || ' tt_CLOSED_BY_tt ' || loginname);

						PERFORM soberano."fn_Order_closeProcessRuns"(orderid, loginname) FROM
							soberano."Configuration" WHERE "This_has_UpdateInventoryOnOrderClosing";
					ELSE
						--make decision. collect receivable
						PERFORM metamodel."fn_Vote_vote"(loginname,
														receivableEntityTypeInstanceId, 
														decisionIdToCollectReceivable, 
														'tt_RECEIVABLE_tt ' || CAST(currentReceivableId AS text) || chr(13) || ' tt_COLLECTED_BY_tt ' || loginname);
					END IF;
					
					PERFORM soberano."fn_Deposit_create"(finalCashRegister,
												false,
												orderid,
												currentReceivableId,
												currencies,
												amounts,
												notes,
												'soberano.user.top');
				
					changeAmount := totalInputAmount - toCollectAmount;

					IF changeAmount > 0 THEN
						SELECT array_fill(0, ARRAY[array_length(currencies, 1)]) INTO withdrawalAmountsArray;
						withdrawalAmountsArray[systemCurrencyArrayIndex] := changeAmount;				
						PERFORM soberano."fn_Withdrawal_create"(finalCashRegister, 
																false,
																orderid,
																currentReceivableId,
																currencies,
																withdrawalAmountsArray,
																notes,
																'soberano.user.top');
					END IF;
					SELECT * FROM soberano."fn_Order_getTicket"(orderid, totalInputAmount, changeAmount, lang, 'soberano.user.top') INTO qryReport;					
				END IF;
			END IF;
		END IF;
		
		SELECT "This_uses_PrinterProfile_with_PrinterProfileHasPrinterProfileId"
			FROM soberano."Customer" cust
				INNER JOIN soberano."CustomerOrder" custo
					ON custo."CustomerHasCustomerId" = cust."CustomerHasCustomerId"
					 AND custo."OrderHasOrderId" = orderid
			LIMIT 1
			INTO customerPP;
		
		RETURN QUERY SELECT qryResult, 
							qryReport, 
							CASE WHEN customerPP IS NULL THEN qryPrinterProfileId
								WHEN customerPP = 0 THEN qryPrinterProfileId
								ELSE customerPP END;
	END;	
$BODY$;