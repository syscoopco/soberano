CREATE OR REPLACE FUNCTION soberano."fn_Product_getAllWithStringIdForOrder"(
	typed text,
	nrows integer,
	loginname character varying)
    RETURNS TABLE("domainObjectId" integer, "domainObjectStringId" character varying, "domainObjectName" text, unit integer, "oneRunQuantity" numeric) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 100

AS $BODY$
	BEGIN
		RETURN QUERY SELECT * FROM soberano."fn_Product_getAllWithStringIdForOrder"(loginname) sq
						WHERE LOWER(sq."domainObjectName") LIKE '%' || LOWER(typed) || '%'
						ORDER BY "domainObjectName" ASC
						LIMIT nrows;
	END;	
$BODY$;