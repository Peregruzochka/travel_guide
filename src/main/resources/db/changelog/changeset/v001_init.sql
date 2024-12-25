CREATE TABLE cities(
    id              UUID                PRIMARY KEY,
    city_name       VARCHAR(128)        NOT NULL
);

CREATE TABLE sights(
    id              UUID                PRIMARY KEY,
    sight_name      VARCHAR(256)        NOT NULL,
    sight_type      VARCHAR(128)        NOT NULL,
    lan             DOUBLE PRECISION    NOT NULL,
    lon             DOUBLE PRECISION    NOT NULL,
    city_id         UUID                NOT NULL,

    CONSTRAINT fk_city_id FOREIGN KEY (city_id) REFERENCES cities(id)
);

CREATE TABLE users(
    id              UUID                PRIMARY KEY,
    username        VARCHAR(64)         NOT NULL
);

CREATE TABLE comments(
    id              UUID                PRIMARY KEY,
    content         VARCHAR(4096),
    grade           SMALLINT            NOT NULL,
    sight_id        UUID                NOT NULL,
    user_id         UUID                NOT NULL,
    created_at      TIMESTAMP           NOT NULL,

    CONSTRAINT fk_user_id   FOREIGN KEY (user_id)   REFERENCES users(id),
    CONSTRAINT fk_sight_id  FOREIGN KEY (sight_id)  REFERENCES sights(id)
);





