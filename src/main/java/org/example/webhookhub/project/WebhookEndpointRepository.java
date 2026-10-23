package org.example.webhookhub.project;

import org.example.webhookhub.endpoint.WebhookEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WebhookEndpointRepository extends JpaRepository<WebhookEndpoint, UUID> {

    List<WebhookEndpoint> findAllByProject_IdOrderByCreatedAtDesc(UUID projectId);

    boolean existsByProject_IdAndUrl(UUID projectId, String url);
}
