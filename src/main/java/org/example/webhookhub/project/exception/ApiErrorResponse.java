package org.example.webhookhub.project.exception;

import java.time.Instant;

public record ApiErrorResponse(
        Instant timeStamp,
        int status,
        String error,
        String message,
        String path
) {
}
