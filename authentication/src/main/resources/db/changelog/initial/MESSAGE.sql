CREATE TABLE IF NOT EXISTS message (
    id int8 NOT NULL,
    title varchar(20) NOT NULL,
    text varchar(500) NOT NULL,
    message_time timestamp,
    sender varchar(50) NOT NULL,
    receiver_id int8 NOT NULL,
    CONSTRAINT message_pkey PRIMARY KEY (id),
    CONSTRAINT receiver_fkey FOREIGN KEY (receiver_id) REFERENCES users(id)
);