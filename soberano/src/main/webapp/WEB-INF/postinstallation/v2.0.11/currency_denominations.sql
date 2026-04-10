-- Table: soberano.CurrencyDenomination

-- DROP TABLE IF EXISTS soberano."CurrencyDenomination";

CREATE TABLE IF NOT EXISTS soberano."CurrencyDenomination"
(
    "CurrencyDenominationHasCurrencyDenominationId" integer NOT NULL DEFAULT nextval('soberano."CurrencyDenomination_CurrencyDenominationHasCurrencyDenominatio"'::regclass),
    "This_is_identified_by_EntityTypeInstance_id" integer,
    "This_is_for_Amount" numeric,
    "This_is_of_Currency_with_CurrencyHasCurrencyId" integer,
    CONSTRAINT "PK_soberano.CurrencyDenomination" PRIMARY KEY ("CurrencyDenominationHasCurrencyDenominationId"),
    CONSTRAINT "UC_InternalUniquenessConstraint504_soberano.CurrencyDenominatio" UNIQUE ("This_is_identified_by_EntityTypeInstance_id"),
    CONSTRAINT "FK_FR_CurrencyDenominationIsOfCurrency" FOREIGN KEY ("This_is_of_Currency_with_CurrencyHasCurrencyId")
        REFERENCES soberano."Currency" ("CurrencyHasCurrencyId") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS soberano."CurrencyDenomination"
    OWNER to postgres;
-- Index: FKI_FK_FR_CurrencyDenominationIsOfCurrency_9827

-- DROP INDEX IF EXISTS soberano."FKI_FK_FR_CurrencyDenominationIsOfCurrency_9827";

CREATE INDEX IF NOT EXISTS "FKI_FK_FR_CurrencyDenominationIsOfCurrency_9827"
    ON soberano."CurrencyDenomination" USING btree
    ("This_is_of_Currency_with_CurrencyHasCurrencyId" ASC NULLS LAST)
    TABLESPACE pg_default;
    
-- Table: soberano.DepositIsMadeInQuantityOfCurrencyDenomination

-- DROP TABLE IF EXISTS soberano."DepositIsMadeInQuantityOfCurrencyDenomination";

CREATE TABLE IF NOT EXISTS soberano."DepositIsMadeInQuantityOfCurrencyDenomination"
(
    "Quantity" numeric,
    "DepositHasDepositId" integer NOT NULL,
    "CurrencyDenominationHasCurrencyDenominationId" integer NOT NULL,
    CONSTRAINT "PK_soberano.DepositIsMadeInQuantityOfCurrencyDenomination" PRIMARY KEY ("DepositHasDepositId", "CurrencyDenominationHasCurrencyDenominationId"),
    CONSTRAINT "FK_FT_soberano.CurrencyDenomination" FOREIGN KEY ("CurrencyDenominationHasCurrencyDenominationId")
        REFERENCES soberano."CurrencyDenomination" ("CurrencyDenominationHasCurrencyDenominationId") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT "FK_FT_soberano.Deposit" FOREIGN KEY ("DepositHasDepositId")
        REFERENCES soberano."Deposit" ("DepositHasDepositId") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS soberano."DepositIsMadeInQuantityOfCurrencyDenomination"
    OWNER to postgres;
-- Index: FKI_FK_FT_soberano.CurrencyDenomination_1446

-- DROP INDEX IF EXISTS soberano."FKI_FK_FT_soberano.CurrencyDenomination_1446";

CREATE INDEX IF NOT EXISTS "FKI_FK_FT_soberano.CurrencyDenomination_1446"
    ON soberano."DepositIsMadeInQuantityOfCurrencyDenomination" USING btree
    ("CurrencyDenominationHasCurrencyDenominationId" ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: FKI_FK_FT_soberano.Deposit_9845

-- DROP INDEX IF EXISTS soberano."FKI_FK_FT_soberano.Deposit_9845";

CREATE INDEX IF NOT EXISTS "FKI_FK_FT_soberano.Deposit_9845"
    ON soberano."DepositIsMadeInQuantityOfCurrencyDenomination" USING btree
    ("DepositHasDepositId" ASC NULLS LAST)
    TABLESPACE pg_default;
   
-- Table: soberano.WithdrawalIsMadeInQuantityOfCurrencyDenomination

-- DROP TABLE IF EXISTS soberano."WithdrawalIsMadeInQuantityOfCurrencyDenomination";

CREATE TABLE IF NOT EXISTS soberano."WithdrawalIsMadeInQuantityOfCurrencyDenomination"
(
    "Quantity" numeric,
    "CurrencyDenominationHasCurrencyDenominationId" integer NOT NULL,
    "WithdrawalHasWithdrawalId" integer NOT NULL,
    CONSTRAINT "PK_soberano.WithdrawalIsMadeInQuantityOfCurrencyDenomination" PRIMARY KEY ("CurrencyDenominationHasCurrencyDenominationId", "WithdrawalHasWithdrawalId"),
    CONSTRAINT "FK_FT_soberano.CurrencyDenomination" FOREIGN KEY ("CurrencyDenominationHasCurrencyDenominationId")
        REFERENCES soberano."CurrencyDenomination" ("CurrencyDenominationHasCurrencyDenominationId") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT "FK_FT_soberano.Withdrawal" FOREIGN KEY ("WithdrawalHasWithdrawalId")
        REFERENCES soberano."Withdrawal" ("WithdrawalHasWithdrawalId") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS soberano."WithdrawalIsMadeInQuantityOfCurrencyDenomination"
    OWNER to postgres;
-- Index: FKI_FK_FT_soberano.CurrencyDenomination_1467

-- DROP INDEX IF EXISTS soberano."FKI_FK_FT_soberano.CurrencyDenomination_1467";

CREATE INDEX IF NOT EXISTS "FKI_FK_FT_soberano.CurrencyDenomination_1467"
    ON soberano."WithdrawalIsMadeInQuantityOfCurrencyDenomination" USING btree
    ("CurrencyDenominationHasCurrencyDenominationId" ASC NULLS LAST)
    TABLESPACE pg_default;
-- Index: FKI_FK_FT_soberano.Withdrawal_8925

-- DROP INDEX IF EXISTS soberano."FKI_FK_FT_soberano.Withdrawal_8925";

CREATE INDEX IF NOT EXISTS "FKI_FK_FT_soberano.Withdrawal_8925"
    ON soberano."WithdrawalIsMadeInQuantityOfCurrencyDenomination" USING btree
    ("WithdrawalHasWithdrawalId" ASC NULLS LAST)
    TABLESPACE pg_default;

ALTER TABLE soberano.\"CurrencyDenomination\"\n"
								+ "  ADD CONSTRAINT \"CurrencyDenomination_This_is_identified_by_EntityTypeInstance_id_fkey\" FOREIGN KEY (\"This_is_identified_by_EntityTypeInstance_id\")\n"
								+ "      REFERENCES \"metamodel\".\"EntityTypeInstance\" (\"EntityTypeInstanceHasEntityTypeInstanceId\") MATCH SIMPLE\n"
								+ "      ON UPDATE CASCADE ON DELETE CASCADE;
								
INSERT INTO "metamodel"."LifeCycle" ("LifeCycleHasLifeCycleId",
											"This_has_Name",
											"This_is_for_EntityType_with_MeaningHasMeaningId")
							VALUES (32, 'CurrencyDenomination', '_92CA9E6F-735D-4913-AD41-CC81635161CF');
							
INSERT INTO "metamodel"."LifeCycleIsDeployedAcrossAuthority" ("LifeCycleHasLifeCycleId",
														"AuthorityHasAuthorityId")
							VALUES (32, 1);
							

							
