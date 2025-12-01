ALTER TABLE locations
ALTER COLUMN name SET NOT NULL,
ADD CONSTRAINT name_not_empty CHECK (name <> '');