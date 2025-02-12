-- V2__create_webhooks_table.sql

CREATE TABLE webhooks (
    id SERIAL PRIMARY KEY,
    endpoint VARCHAR(255) NOT NULL
);