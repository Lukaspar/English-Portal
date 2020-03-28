CREATE TABLE IF NOT EXISTS role (
    id serial NOT NULL ,
    name varchar(50) NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id)
);