--this is to be used at the end of fn_Order_collect
--the variable neworderid integer must be declared

--customization: se abre otro pedido automáticamente si no hay ningún otro abierto en el mismo mostrador del que se está cerrando
IF qryResult = 0 
	AND NOT EXISTS(SELECT *
		FROM soberano."CounterOrder" counterorder
			INNER JOIN soberano."Order" ord
				ON counterorder."OrderHasOrderId" = ord."OrderHasOrderId"
					AND ord."OrderHasOrderId" != orderid
			INNER JOIN metamodel."EntityTypeInstance" eti
				ON ord."This_is_identified_by_EntityTypeInstance_id" = eti."EntityTypeInstanceHasEntityTypeInstanceId"
			LEFT JOIN soberano."OrderProcessRun" opr
				ON opr."OrderHasOrderId" = ord."OrderHasOrderId"
		WHERE "This_is_in_Stage_with_StageHasStageId" = 3 
			AND "This_has_ordered_Runs" IS NULL) THEN

	SELECT soberano."fn_Order_create"(	'',
									ARRAY["This_has_Counter_code"]::character varying[],
									null,
									null,
									'soberano.user.top') 
		FROM soberano."CounterOrder" counterorder
			INNER JOIN soberano."Counter" counter
				ON counter."CounterHasCounterId" = counterorder."CounterHasCounterId"
					AND counterorder."OrderHasOrderId" = orderid
		INTO neworderid;

END IF;		

------------------------------------

RETURN QUERY SELECT CASE WHEN qryResult = 0 AND neworderid IS NOT NULL AND neworderid > 0 THEN neworderid 
						ELSE qryResult END, 
					qryReport, 
					CASE WHEN customerPP IS NULL THEN qryPrinterProfileId
						WHEN customerPP = 0 THEN qryPrinterProfileId
						ELSE customerPP END;