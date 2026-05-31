-- src/main/resources/db/migration/V2__medication_dosage_unit_check.sql
ALTER TABLE medications
    DROP CONSTRAINT IF EXISTS chk_dosage_unit,
    ADD CONSTRAINT chk_dosage_unit
        CHECK (dosage_unit IN ('MCG','MG','G','ML','MCU','IU','DOSE'));