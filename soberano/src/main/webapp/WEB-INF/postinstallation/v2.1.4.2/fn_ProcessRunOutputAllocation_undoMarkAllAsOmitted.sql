CREATE OR REPLACE FUNCTION soberano."fn_ProcessRunOutputAllocation_undoMarkAllAsOmitted"(
	processrunid integer,
	loginname character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
	DECLARE
		entityTypeInstanceId integer;
		decisionId integer;
		productionLineName character varying;
		qryResult integer;
	BEGIN
		--default returning value. user has no right.
		qryResult := -1;
		
		SELECT DISTINCT objectdata."This_is_identified_by_EntityTypeInstance_id",
						decision."DecisionId",
						objectdata."This_has_Name"
			FROM (SELECT *, 
						ROW_NUMBER () OVER (ORDER BY "DecisionId" ASC) rownumber 
					FROM (SELECT DISTINCT "DecisionId", 
											"DecisionName"
							  	
							  	--important to use here top user, for all the decisions 
						  		--on production lines (lifecycle 29) get retrieved					
								FROM metamodel."fn_EntityTypeInstance_getDecisions"(29, 1, 'soberano.user.top')
								WHERE "DecisionName" LIKE 

									/************/
										'Manage allocation%'
									/************/

								ORDER BY "DecisionName") sq) decision
						INNER JOIN (SELECT "CostCenterHasCostCenterId",
										"This_uses_ProductionLine_with_ProductionLineHasProductionLineId",
								ROW_NUMBER () OVER (ORDER BY "CostCenterHasCostCenterId" ASC) rownumber
							FROM soberano."CostCenter" coc
								INNER JOIN metamodel."EntityTypeInstance" eti
				 					ON eti."EntityTypeInstanceHasEntityTypeInstanceId" = coc."This_is_identified_by_EntityTypeInstance_id"
				 			
										--cost center is enabled
										AND eti."This_is_in_Stage_with_StageHasStageId" = 2) cc
							ON cc.rownumber = decision.rownumber
						INNER JOIN soberano."ProcessRun" pr
							ON pr."This_is_executed_in_CostCenter_with_CostCenterHasCostCenterId" = cc."CostCenterHasCostCenterId"
						INNER JOIN soberano."ProcessRunOutputAllocation" proa
							ON proa."ProcessRunOutput_with_ProcessRunHasProcessRunId_is_allocated_by" = pr."ProcessRunHasProcessRunId"
								AND proa."ProcessRunOutput_with_ProcessRunHasProcessRunId_is_allocated_by" = processrunid
						INNER JOIN soberano."ProductionLine" objectdata
							ON proa."This_is_allocated_to_ProductionLine_with_ProductionLineHasProdu" = objectdata."ProductionLineHasProductionLineId"
								AND objectdata."ProductionLineHasProductionLineId" = cc."This_uses_ProductionLine_with_ProductionLineHasProductionLineId"
								AND "DecisionId" IN (SELECT "DecisionId" 
						 				FROM metamodel."fn_EntityTypeInstance_getDecisions"(
											objectdata."This_is_identified_by_EntityTypeInstance_id",
											loginname))
			INTO entityTypeInstanceId,
				decisionId,
				productionLineName;		
			
		--user has rights
		IF decisionId IS NOT NULL AND entityTypeInstanceId IS NOT NULL THEN
		
			UPDATE soberano."ProcessRunOutputAllocation" SET "ProcessRunOutputAllocation_is_omitted" = false
				WHERE "ProcessRunOutput_with_ProcessRunHasProcessRunId_is_allocated_by" = processrunid;

			--also unmark as omitted the process runs corresponding to additions of this process run
			UPDATE soberano."ProcessRunOutputAllocation" additionAllocation SET "ProcessRunOutputAllocation_is_omitted" = false
				FROM soberano."OrderProcessRun" additionProcessRun
				WHERE  additionProcessRun."ProcessRunHasProcessRunId" = additionAllocation."ProcessRunOutput_with_ProcessRunHasProcessRunId_is_allocated_by"
					AND additionProcessRun."This_is_an_addition_of_OrderProcessRun_with_ProcessRunHasProces" = processrunid;
		
			--make the decision
			PERFORM metamodel."fn_Vote_vote"(loginname,
												entityTypeInstanceId, 
												decisionId, 
												'tt_PROCESS_RUN_OUTPUT_ALLOCATIONS_UNDO_OMITTED_tt ' || chr(13) ||
											 	'tt_ID_tt ' || CAST(processrunid AS text) || chr(13) ||
											 	'tt_WORKER_tt ' || loginname);
		
			qryResult := 0;
		END IF;
		RETURN qryResult;
END;
$BODY$;