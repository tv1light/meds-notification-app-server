-- V1__init_schema.sql

CREATE TABLE IF NOT EXISTS users (
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR(12) NOT NULL UNIQUE,
    password VARCHAR(14) NOT NULL,
    CONSTRAINT chk_login_len    CHECK (char_length(login)    BETWEEN 4 AND 12),
    CONSTRAINT chk_password_len CHECK (char_length(password) BETWEEN 8 AND 14)
);

CREATE TABLE IF NOT EXISTS countries (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(56) NOT NULL UNIQUE,
    CONSTRAINT chk_country_len CHECK (char_length(name) BETWEEN 3 AND 56)
);

CREATE TABLE IF NOT EXISTS manufacturers (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL UNIQUE,
    country_id BIGINT REFERENCES countries(id),
    CONSTRAINT chk_manufacturer_len CHECK (char_length(name) BETWEEN 2 AND 50)
);

CREATE TABLE IF NOT EXISTS active_substances (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT chk_substance_len CHECK (char_length(name) BETWEEN 1 AND 50)
);

CREATE TABLE IF NOT EXISTS release_forms (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT chk_form_len CHECK (char_length(name) BETWEEN 2 AND 50)
);

CREATE TABLE IF NOT EXISTS medications (
    id                  BIGSERIAL PRIMARY KEY,
    trade_name          VARCHAR(50) NOT NULL,
    active_substance_id BIGINT REFERENCES active_substances(id),
    release_form_id     BIGINT REFERENCES release_forms(id),
    manufacturer_id     BIGINT REFERENCES manufacturers(id),
    dosage_value        NUMERIC(10,3) NOT NULL,
    dosage_unit         VARCHAR(10)   NOT NULL,
    CONSTRAINT chk_med_name_len CHECK (char_length(trade_name) BETWEEN 2 AND 50)
);
