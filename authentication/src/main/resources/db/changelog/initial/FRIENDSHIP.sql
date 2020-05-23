CREATE TABLE IF NOT EXISTS friendship (
    owner_id int8 NOT NULL,
    friend_id int8 NOT NULL,
    status varchar(50),
    CONSTRAINT friendship_pkey PRIMARY KEY (owner_id, friend_id),
    CONSTRAINT owner_fkey FOREIGN KEY (owner_id) REFERENCES users(id),
    CONSTRAINT friend_fkey FOREIGN KEY (friend_id) REFERENCES users(id)
);