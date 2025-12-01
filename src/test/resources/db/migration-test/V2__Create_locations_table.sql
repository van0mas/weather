CREATE TABLE locations (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           user_id INT NOT NULL,
                           latitude DOUBLE,
                           longitude DOUBLE,
                           CONSTRAINT fk_user
                               FOREIGN KEY (user_id)
                                   REFERENCES users (id)
                                   ON DELETE CASCADE
);
