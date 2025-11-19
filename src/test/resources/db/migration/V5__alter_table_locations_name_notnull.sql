ALTER TABLE locations
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE locations
    ADD CONSTRAINT name_not_empty CHECK (name <> '');
