package org.example.webhookhub.project;

import java.time.Instant;
import java.util.UUID;

public record WebhookEndpointResponse(
        UUID id,
        UUID projectId,
        String url,
        boolean active,
        Instant createdAt
) {
}
