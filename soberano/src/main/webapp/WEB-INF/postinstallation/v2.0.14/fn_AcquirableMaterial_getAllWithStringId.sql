CREATE OR REPLACE FUNCTION soberano."fn_AcquirableMaterial_getAllWithStringId"(
	typed text,
	nrows integer,
	loginname character varying)
    RETURNS TABLE("domainObjectStringId" character varying, "domainObjectName" text) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 100

AS $BODY$
	BEGIN
		RETURN QUERY SELECT * FROM soberano."fn_AcquirableMaterial_getAllWithStringId"(loginname) sq
						WHERE LOWER(sq."domainObjectName") LIKE '%' || LOWER(typed) || '%'
						ORDER BY "domainObjectName" ASC
						LIMIT nrows;
	END;	
$BODY$;