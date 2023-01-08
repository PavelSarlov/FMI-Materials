CREATE TABLE subscriptions(
    id SERIAL,
    user_id INT NOT NULL,
    target_id INT NOT NULL,
    type VARCHAR(255) DEFAULT '',

    CONSTRAINT PK_subscriptions PRIMARY KEY(id),
    CONSTRAINT FK_subscriptions__users FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);
