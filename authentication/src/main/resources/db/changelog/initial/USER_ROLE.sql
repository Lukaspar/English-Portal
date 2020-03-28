CREATE TABLE IF NOT EXISTS user_role (
    user_id int8 NOT NULL,
    role_id int8 NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_fkey FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT role_fkey FOREIGN KEY (role_id) REFERENCES role(id)
);