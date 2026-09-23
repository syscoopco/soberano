ALTER TABLE IF EXISTS soberano."Configuration"
    ADD COLUMN "This_has_PrintTicketWhenCollectingPayment" boolean;

ALTER TABLE IF EXISTS soberano."CostCenter"
    ADD COLUMN "This_has_ShiftClosureHours" smallint;

ALTER TABLE IF EXISTS soberano."CostCenter"
    ADD COLUMN "This_has_ShiftClosureMinutes" smallint;

ALTER TABLE IF EXISTS soberano."Counter"
    ADD COLUMN "Payments_collected_at_This_are_deposited_in_CashRegister_with_C" integer;
    
ALTER TABLE IF EXISTS soberano."Counter"
    ADD COLUMN "This_is_stocked_by_CostCenter_with_CostCenterHasCostCenterId" integer;
    
ALTER TABLE IF EXISTS soberano."Worker"
    ADD COLUMN "This_operates_CashRegister_with_CashRegisterHasCashRegisterId" integer;
    
