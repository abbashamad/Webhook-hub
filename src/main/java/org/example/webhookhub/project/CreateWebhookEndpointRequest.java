package org.example.webhookhub.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateWebhookEndpointRequest(
        @NotBlank(message = "Webhook URL is required")
        @Size(
                max = 2048,
                message = "Webhook URL must not exceed 2048 characters"
        )
        String url
) {
}
