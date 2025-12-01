CREATE TABLE sessions (
                          id UUID PRIMARY KEY,
                          user_id INT NOT NULL,
                          expires_at TIMESTAMP NOT NULL,
                          CONSTRAINT fk_session_user
                              FOREIGN KEY (user_id)
                                  REFERENCES users (id)
                                  ON DELETE CASCADE
);
