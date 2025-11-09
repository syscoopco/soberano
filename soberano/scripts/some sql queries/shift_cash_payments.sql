			/*A query fragment to iterate through cash payments with or without changes, within a time span shift.
			The query includes insertion in a shift closure report.
			
			Receivable lines are commented to include receivables payments during the time span. Just comment them
			out for excluding receivable payments.*/
			
						
			report := report || '=== ' || 'PAGOS CASH' || ' ===' || chr(13) || chr(13);
			report := report || 'tt_TIME_tt' || chr(13) || chr(13) || 'tt_ORDER_tt' || chr(13) || 'tt_AMOUNT_tt' || chr(13) || 'tt_CURRENCY_tt' || chr(13);
			report := report || '----- ' || chr(13);
			
			FOR itemRecord IN SELECT orderdepos.ord, 
									orderdepos.transferTime,
									orderdepos.transferedAmount || ' ' || orderdepos.currency paymentamount,
									'VUELTO: ' || CASE WHEN orderchanges.transferedAmount IS NULL THEN '$0.0' ELSE orderchanges.transferedAmount END
										|| ' ' || CASE WHEN orderchanges.currency IS NULL THEN 'CUP'::text ELSE orderchanges.currency END change
								FROM (SELECT depo."This_is_for_Order_with_OrderHasOrderId" ord,
											to_char(eti."This_is_created_at_Timestamp", 'HH24:MI') transferTime,
											CAST(depoamnt."Amount" AS money) transferedAmount,
											curr."This_has_Currency_code" currency
										FROM soberano."Deposit" depo
											INNER JOIN soberano."DepositAmount" depoamnt
												ON depo."DepositHasDepositId" = depoamnt."DepositHasDepositId"
													AND depo."This_is_for_Order_with_OrderHasOrderId" IS NOT NULL
													
													--AND depo."This_is_for_Receivable_with_ReceivableHasReceivableId" IS NULL
													AND depoamnt."Amount" > 0
											INNER JOIN metamodel."EntityTypeInstance" eti
												ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = depo."This_is_identified_by_EntityTypeInstance_id"
													AND eti."This_is_created_at_Timestamp" > previousClosureTime 
													AND eti."This_is_created_at_Timestamp" <= closureTime
											INNER JOIN soberano."Currency" curr
												ON curr."CurrencyHasCurrencyId" = depoamnt."CurrencyHasCurrencyId"
													AND curr."Currency_is_cash") orderdepos
								LEFT JOIN (SELECT withd."This_is_for_Order_with_OrderHasOrderId" ord,
												to_char(eti."This_is_created_at_Timestamp", 'HH24:MI') transferTime,
												CAST(withdamnt."Amount" AS money) transferedAmount,
												curr."This_has_Currency_code" currency
											FROM soberano."Withdrawal" withd
												INNER JOIN soberano."WithdrawalAmount" withdamnt
													ON withd."WithdrawalHasWithdrawalId" = withdamnt."WithdrawalHasWithdrawalId"
														AND withd."This_is_for_Order_with_OrderHasOrderId" IS NOT NULL
														
														--AND withd."This_is_for_Receivable_with_ReceivableHasReceivableId" IS NULL
												INNER JOIN metamodel."EntityTypeInstance" eti
													ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = withd."This_is_identified_by_EntityTypeInstance_id"
												INNER JOIN soberano."Currency" curr
													ON curr."CurrencyHasCurrencyId" = withdamnt."CurrencyHasCurrencyId"
														AND curr."Currency_is_cash") orderchanges
							ON orderdepos.ord = orderchanges.ord ORDER BY orderdepos.currency, orderdepos.ord LOOP
				
				report := report || itemRecord.transferTime || chr(13);
				report := report || itemRecord.ord || chr(13);				
				report := report || itemRecord.paymentamount || chr(13);
				report := report || itemRecord.change || chr(13) || chr(13);
			
			END LOOP;
			
			report := report || '=== ' || 'FIN DE PAGOS EN EFECTIVO' || ' ===' || chr(13) || chr(13);