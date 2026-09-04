CREATE SEQUENCE IF NOT EXISTS revinfo_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE fragrance_collection
(
    id               UUID NOT NULL,
    version          BIGINT,
    created_on       TIMESTAMP(6) WITHOUT TIME ZONE,
    updated_on       TIMESTAMP(6) WITHOUT TIME ZONE,
    created_by       VARCHAR(255),
    last_modified_by VARCHAR(255),
    name             VARCHAR(255),
    brand            VARCHAR(255),
    concentration    VARCHAR(255),
    user_id          UUID NOT NULL,
    CONSTRAINT pk_fragrance_collection PRIMARY KEY (id)
);

CREATE TABLE revchanges
(
    rev        BIGINT NOT NULL,
    entityname VARCHAR(255)
);

CREATE TABLE revinfo
(
    rev      BIGINT NOT NULL,
    revtstmp BIGINT,
    CONSTRAINT pk_revinfo PRIMARY KEY (rev)
);

CREATE TABLE user_fragrance_types
(
    user_id        UUID         NOT NULL,
    fragrance_type VARCHAR(255) NOT NULL
);

CREATE TABLE users
(
    id               UUID NOT NULL,
    version          BIGINT,
    created_on       TIMESTAMP(6) WITHOUT TIME ZONE,
    updated_on       TIMESTAMP(6) WITHOUT TIME ZONE,
    created_by       VARCHAR(255),
    last_modified_by VARCHAR(255),
    first_name       VARCHAR(255),
    last_name        VARCHAR(255),
    email            VARCHAR(255),
    status           VARCHAR(255),
    city             VARCHAR(255),
    country          VARCHAR(255),
    province         VARCHAR(255),
    latitude         DECIMAL(9, 6),
    longitude        DECIMAL(9, 6),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT email_constraint UNIQUE (email);

ALTER TABLE fragrance_collection
    ADD CONSTRAINT FK_FRAGRANCE_COLLECTION_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE revchanges
    ADD CONSTRAINT fk_revchanges_on_default_tracking_modified_entities_changelog FOREIGN KEY (rev) REFERENCES revinfo (rev);

ALTER TABLE user_fragrance_types
    ADD CONSTRAINT fk_user_fragrance_types_on_users_entity FOREIGN KEY (user_id) REFERENCES users (id);