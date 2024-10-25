CREATE TABLE "event"
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255),
    date     VARCHAR(255),
    location VARCHAR(255)
);

CREATE TABLE "user"
(
    id SERIAL PRIMARY KEY
);

CREATE TABLE "event_user"
(
    id       SERIAL PRIMARY KEY,
    event_id INTEGER REFERENCES "event" (id),
    user_id  INTEGER REFERENCES "user" (id),
    UNIQUE (event_id, user_id) -- Para evitar duplicados
);
