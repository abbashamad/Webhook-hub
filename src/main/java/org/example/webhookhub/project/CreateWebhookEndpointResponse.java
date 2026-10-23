package org.example.webhookhub.project;

import java.time.Instant;
import java.util.UUID;

public record CreateWebhookEndpointResponse(
        UUID id,
        UUID projectId,
        String url,
        String ssecret,
        boolean active,
        Instant createdAt
) {
}
