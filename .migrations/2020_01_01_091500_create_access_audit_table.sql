CREATE TABLE access_audit
(
    `access_id`     VARCHAR(36),
    `access`        VARCHAR(36) NOT NULL,
    `scope`         VARCHAR(36) NOT NULL,
    `method`        VARCHAR(36) NOT NULL,
    `path`          VARCHAR(36) NOT NULL,
    `created_at`    DATETIME(6) NOT NULL,

    INDEX idx_access_id (`access_id`),
    INDEX idx_access(`access`),
    INDEX idx_scope(`scope`),
    INDEX idx_created_at (`created_at`)
);

