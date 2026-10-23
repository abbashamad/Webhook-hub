CREATE TABLE webhook_endpoints
(
    id         UUID PRIMARY KEY,
    project_id UUID          NOT NULL,
    url        VARCHAR(2048) NOT NULL,
    secret     VARCHAR(255)  NOT NULL,
    active     BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ   NOT NULL,

    CONSTRAINT fk_webhook_endpoints_project
        FOREIGN KEY (project_id)
            REFERENCES projects (id)
            ON DELETE CASCADE,

    CONSTRAINT uq_webhook_endpoints_project_url
        UNIQUE (project_id, url)
);

CREATE INDEX idx_webhook_endpoints_project_id
    ON webhook_endpoints (project_id);