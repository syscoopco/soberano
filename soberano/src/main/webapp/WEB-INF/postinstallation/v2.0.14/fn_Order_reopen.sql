CREATE OR REPLACE FUNCTION soberano."fn_Order_reopen"(
	orderid integer,
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    SET default_transaction_isolation='serializable'
AS $BODY$
	DECLARE
		qryResult integer;
		entityTypeInstanceId integer;
		objectCode character varying;
		decisionId integer;
		orderClosingTime timestamp with time zone;

		cashRegisterWhereOrderWasCollected integer;
	BEGIN
		--default returning value. user has no right.
		qryResult := -1;
		
		SELECT proc.*
			FROM (SELECT objectdata."This_is_identified_by_EntityTypeInstance_id", 
								objectdata."This_is_identified_by_Label",
								decision."DecisionHasDecisionId",
				  				vote."This_is_on_Timestamp"
							FROM soberano."Order" objectdata
				  				INNER JOIN "metamodel"."Vote" vote
									ON objectdata."This_is_identified_by_EntityTypeInstance_id" = vote."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"				
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
													'Reopen'
												/************/						   

										   )
							WHERE "OrderHasOrderId" = orderid
				  
				  			--only last decision time on order is taken.
				 			ORDER BY vote."VoteHasVoteId" DESC LIMIT 1) proc
			INTO entityTypeInstanceId,
				objectCode,
				decisionId,
				orderClosingTime;
				
		--user has rights
		IF decisionId IS NOT NULL AND entityTypeInstanceId IS NOT NULL THEN
		
			--the shift hasn't been closed
			IF NOT EXISTS(SELECT * FROM soberano."ShiftClosure" closure
								INNER JOIN "metamodel"."EntityTypeInstance" eti
									ON closure."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId"
							--or it has, but the closure was canceled
							WHERE "This_is_in_Stage_with_StageHasStageId" != 5
								--for the closure's shift
								AND "This_has_ClosureTime" >= orderClosingTime) THEN
				
				--reopening an order is a very high impact operation. it must be absolutely exceptional.
				--it must be done by a highly privileged user (reopener role), really motivated 
				--for the business to run strictly adhered to rules.
				--the cash register balances, unrelated to receivable payments, is also updated.

				SELECT "This_is_to_CashRegister_with_CashRegisterHasCashRegisterId" 
					FROM soberano."Deposit" 
					WHERE "This_is_for_Order_with_OrderHasOrderId" = orderid
					LIMIT 1
					INTO cashRegisterWhereOrderWasCollected;
				
				PERFORM soberano."fn_CashRegister_updateBalance"(1,
												 cashRegisterWhereOrderWasCollected,
												 "CurrencyHasCurrencyId",
												 "Amount")
					FROM soberano."WithdrawalAmount" wamnt
						INNER JOIN soberano."Withdrawal" w
							ON w."WithdrawalHasWithdrawalId" = wamnt."WithdrawalHasWithdrawalId"
								AND w."This_is_for_Order_with_OrderHasOrderId" = orderid;

				PERFORM soberano."fn_CashRegister_updateBalance"(2,
																 cashRegisterWhereOrderWasCollected,
																 "CurrencyHasCurrencyId",
																 "Amount")
					FROM soberano."DepositAmount" depoamnt
						INNER JOIN soberano."Deposit" depo
							ON depo."DepositHasDepositId" = depoamnt."DepositHasDepositId"
								AND depo."This_is_for_Order_with_OrderHasOrderId" = orderid;				
				
				DELETE FROM soberano."Deposit" WHERE "This_is_for_Order_with_OrderHasOrderId" = orderid;
				DELETE FROM soberano."Withdrawal" WHERE "This_is_for_Order_with_OrderHasOrderId" = orderid;
				DELETE FROM soberano."Receivable" WHERE "This_is_of_Order_with_OrderHasOrderId" = orderid;
				
				--remove order collecting votes. needed since they are used in shift closure report queries
				DELETE FROM metamodel."Vote" vote
					USING soberano."Order" ord
					WHERE ord."This_is_identified_by_EntityTypeInstance_id" = vote."This_is_on_EntityTypeInstance_with_EntityTypeInstanceHasEntityT"
						AND ord."OrderHasOrderId" = orderid
						AND vote."This_favors_Decision_with_DecisionHasDecisionId" = 3005;
				
				--removes stock changes corresponding to the order
				--but before, rollback stock upon reopening
				UPDATE soberano."Stock" stock 
					SET "Quantity" = COALESCE(sc."This_saves_previous_Quantity", 0),
						"This_has_Value" = COALESCE(sc."This_saves_previous_Value", 0)
					FROM (SELECT "This_saves_previous_Quantity",
								"This_saves_previous_Value",
								"This_is_called_with_Warehouse_with_WarehouseHasWarehouseId",
								"This_is_called_with_InventoryItem_with_InventoryItemHasInventor"
							FROM soberano."StockChange"
							WHERE "This_is_at_DateTime" = orderClosingTime
								AND "This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId"
									IN (SELECT "ProcessRunHasProcessRunId" 
											FROM soberano."OrderProcessRun"
											WHERE "OrderHasOrderId" = orderid)) sc
					WHERE stock."WarehouseHasWarehouseId" = sc."This_is_called_with_Warehouse_with_WarehouseHasWarehouseId"
						AND stock."InventoryItemHasInventoryItemCode" = sc."This_is_called_with_InventoryItem_with_InventoryItemHasInventor";
				
				DELETE FROM soberano."StockChange" 
					WHERE "This_is_triggered_by_ProcessRun_with_ProcessRunHasProcessRunId"
						IN (SELECT "ProcessRunHasProcessRunId" 
								FROM soberano."OrderProcessRun"
						   		WHERE "OrderHasOrderId" = orderid);
								
				DELETE FROM soberano."StockChange"
					WHERE "This_is_of_InventoryOperation_with_InventoryOperationHasInvento"
						IN (SELECT "InventoryOperationHasInventoryOperationId"
						   		FROM soberano."InventoryOperation"
									WHERE "This_is_of_Order_with_OrderHasOrderId" = orderid);
				
				--delete inventory operation that moves order's outputs to sales warehouse\n"
				DELETE FROM soberano."InventoryOperation"
					WHERE "This_is_of_Order_with_OrderHasOrderId" = orderid;

				--removes order's process run input and output values
				DELETE FROM soberano."ProcessRunInputValue" 
					WHERE "ProcessRunHasProcessRunId"
						IN (SELECT "ProcessRunHasProcessRunId" 
								FROM soberano."OrderProcessRun"
						   		WHERE "OrderHasOrderId" = orderid);
				DELETE FROM soberano."ProcessRunOutputValue" 
					WHERE "ProcessRunHasProcessRunId"
						IN (SELECT "ProcessRunHasProcessRunId" 
								FROM soberano."OrderProcessRun"
						   		WHERE "OrderHasOrderId" = orderid);
								
				--removes subprocess process runs
				DELETE FROM soberano."ProcessRun" pr
					USING soberano."OrderProcessRun" opr, soberano."ProcessIsASubprocessOfProcess" subp
					WHERE opr."ProcessRunHasProcessRunId" = pr."ProcessRunHasProcessRunId" 
						AND "OrderHasOrderId" = orderid
						AND pr."This_is_of_Process_with_ProcessHasProcessId" = subp."Subprocess_ProcessHasProcessId";								
				--force reopening (closed -> ongoing) all order's process runs
				UPDATE "metamodel"."EntityTypeInstance" 
					SET "This_is_in_Stage_with_StageHasStageId" = 3 --Ongoing stage
					WHERE "EntityTypeInstanceHasEntityTypeInstanceId"
						IN (SELECT "This_is_identified_by_EntityTypeInstance_id"
								FROM soberano."ProcessRun" pr
									INNER JOIN soberano."OrderProcessRun" opr
										ON opr."ProcessRunHasProcessRunId" = pr."ProcessRunHasProcessRunId"
						   		WHERE "OrderHasOrderId" = orderid);
								
				--recalculate inventory from order closing datetime				
				PERFORM soberano."fn_Stock_recalculateInventoryFrom"(orderClosingTime);
				
				--make the decision
				PERFORM metamodel."fn_Vote_vote"(loginname,
												entityTypeInstanceId, 
												decisionId,
												' tt_ORDER_tt ' || CAST(orderid AS text) || ' : ' || objectCode || chr(13) || ' tt_REOPENED_tt tt_BY_tt ' || loginname);
				
				qryResult := 0;
			ELSE
				qryResult := -2;
			END IF;		
		END IF;		
		RETURN qryResult;
	END;
$BODY$;