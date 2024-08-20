ALTER TABLE store ADD location POINT;

UPDATE store
SET location = POINT(x,y);

ALTER TABLE store
    DROP COLUMN x,
    DROP COLUMN y;

ALTER TABLE store DROP COLUMN x, DROP COLUMN y;