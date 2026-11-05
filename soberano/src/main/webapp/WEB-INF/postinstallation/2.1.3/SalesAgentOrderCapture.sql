-- Table: soberano.SalesAgentOrderCapture

-- DROP TABLE IF EXISTS soberano."SalesAgentOrderCapture";

CREATE TABLE IF NOT EXISTS soberano."SalesAgentOrderCapture"
(
    "This_carries_a_Commission" double precision,
    "OrderHasOrderId" integer NOT NULL,
    "SalesAgent_ProviderHasProviderId" integer,
    CONSTRAINT "PK_soberano.SalesAgentOrderCapture" PRIMARY KEY ("OrderHasOrderId"),
    CONSTRAINT "FK_FT_SalesAgent" FOREIGN KEY ("SalesAgent_ProviderHasProviderId")
        REFERENCES soberano."Provider" ("ProviderHasProviderId") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT "FK_FT_soberano.Order" FOREIGN KEY ("OrderHasOrderId")
        REFERENCES soberano."Order" ("OrderHasOrderId") MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS soberano."SalesAgentOrderCapture"
    OWNER to postgres;
-- Index: FKI_FK_FT_SalesAgent_6171

-- DROP INDEX IF EXISTS soberano."FKI_FK_FT_SalesAgent_6171";

CREATE INDEX IF NOT EXISTS "FKI_FK_FT_SalesAgent_6171"
    ON soberano."SalesAgentOrderCapture" USING btree
    ("SalesAgent_ProviderHasProviderId" ASC NULLS LAST)
;
-- Index: FKI_FK_FT_soberano.Order_795

-- DROP INDEX IF EXISTS soberano."FKI_FK_FT_soberano.Order_795";

CREATE INDEX IF NOT EXISTS "FKI_FK_FT_soberano.Order_795"
    ON soberano."SalesAgentOrderCapture" USING btree
    ("OrderHasOrderId" ASC NULLS LAST)
;