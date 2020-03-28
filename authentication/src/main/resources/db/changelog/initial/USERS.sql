CREATE TABLE IF NOT EXISTS users(
    id serial NOT NULL,
    username varchar(50) NOT NULL,
    password varchar(255) NOT NULL,
    active bool NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);