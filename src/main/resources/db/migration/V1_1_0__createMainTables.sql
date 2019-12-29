CREATE TABLE IF NOT EXISTS school_year
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR NOT NULL,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    inserted_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS school_period
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR NOT NULL,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    inserted_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS schedule
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR NOT NULL,
    time_slot   VARCHAR NOT NULL,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    inserted_at TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION updated_at_now()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER auto_updated_at
    BEFORE UPDATE
    ON school_year
    FOR EACH ROW
EXECUTE PROCEDURE updated_at_now();
CREATE TRIGGER auto_updated_at
    BEFORE UPDATE
    ON school_period
    FOR EACH ROW
EXECUTE PROCEDURE updated_at_now();
CREATE TRIGGER auto_updated_at
    BEFORE UPDATE
    ON schedule
    FOR EACH ROW
EXECUTE PROCEDURE updated_at_now();