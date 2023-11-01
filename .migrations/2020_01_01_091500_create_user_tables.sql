CREATE TABLE users
(
    `id`         VARCHAR(36) PRIMARY KEY,
    `name`       VARCHAR(255) NOT NULL,
    `created_at` DATETIME(6)  NOT NULL,

    INDEX idx_name (`name`),
    INDEX idx_created_at (`created_at`)
);

CREATE TABLE user_emails
(
    `user_id`    VARCHAR(36)  NOT NULL,
    `email`      VARCHAR(255) NOT NULL,
    `created_at` DATETIME(6)  NOT NULL,
    `deleted_at` DATETIME(6),

    INDEX idx_user_id (`user_id`),
    INDEX idx_email (`email`)
);

CREATE TABLE user_accesses
(
    `id`         VARCHAR(36) PRIMARY KEY,
    `user_id`    VARCHAR(36) NOT NULL,
    `created_at` DATETIME(6) NOT NULL,
    `revoked_at` DATETIME(6) DEFAULT NULL,

    INDEX idx_user_id (`user_id`),
    INDEX idx_created_at (`created_at`)
);
