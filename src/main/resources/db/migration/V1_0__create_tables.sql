DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS client;

CREATE TABLE client
(
    id       UUID NOT NULL,
    name     VARCHAR(20),
    email    VARCHAR(255),
    password VARCHAR(255),
    status   VARCHAR(255),
    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE task
(
    id          UUID    NOT NULL,
    title       VARCHAR(255),
    description VARCHAR(255),
    completed   BOOLEAN NOT NULL,
    created_at  date,
    client_id   UUID,
    CONSTRAINT pk_task PRIMARY KEY (id)
);

ALTER TABLE task
    ADD CONSTRAINT FK_TASK_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);