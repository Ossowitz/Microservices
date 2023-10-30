DROP TABLE IF EXISTS account;

CREATE SCHEMA IF NOT EXISTS processing;

SET search_path TO processing;

CREATE TABLE processing.account
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id       BIGINT     NOT NULL,
    currency_code VARCHAR(3) NOT NULL,
    balance       NUMERIC    NOT NULL
);