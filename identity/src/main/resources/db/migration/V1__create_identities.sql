-- V1__create_identities.sql

CREATE TABLE IF NOT EXISTS identities (
    id               UUID                         NOT NULL,
    email            VARCHAR(320)                 NOT NULL,
    name             VARCHAR(255)                 NOT NULL,
    password_hashed  VARCHAR(255)                 NOT NULL,
    status           VARCHAR(16)                  NOT NULL,
    activation_token VARCHAR(255),
    created_at       TIMESTAMP WITH TIME ZONE     NOT NULL,
    version          INTEGER                      NOT NULL DEFAULT 0,

    CONSTRAINT pk_identities PRIMARY KEY (id),
    CONSTRAINT ck_identities_status CHECK (status IN ('PENDING','ACTIVE'))
);

-- Unikalny e-mail (uwaga: jeśli email jest NULL, H2 pozwala na wiele NULL-i)
CREATE UNIQUE INDEX IF NOT EXISTS ux_identities_email
    ON identities (email);

-- Wygodny indeks do szukania po tokenie aktywacyjnym
CREATE INDEX IF NOT EXISTS ix_identities_activation_token
    ON identities (activation_token);
