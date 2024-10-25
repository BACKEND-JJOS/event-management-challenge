CREATE TABLE "event"
(
    id       VARCHAR(255) PRIMARY KEY,
    name     VARCHAR(255),
    date     VARCHAR(255),
    location VARCHAR(255)
);

CREATE TABLE "user"
(
    id VARCHAR(255) PRIMARY KEY
);

CREATE TABLE "event_user "
(
    id       SERIAL PRIMARY KEY,
    event_id VARCHAR(255) REFERENCES event (id),
    user_id  VARCHAR(255) REFERENCES "user" (id),
    UNIQUE (event_id, user_id) -- Para evitar duplicados
);
