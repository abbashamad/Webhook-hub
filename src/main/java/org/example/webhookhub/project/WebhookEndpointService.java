package org.example.webhookhub.project;

import org.example.webhookhub.endpoint.WebhookEndpoint;
import org.example.webhookhub.project.exception.BadRequestException;
import org.example.webhookhub.project.exception.ConflictException;
import org.example.webhookhub.project.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class WebhookEndpointService {

    private final WebhookEndpointRepository webhookEndpointRepository;
    private final ProjectRepository projectRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    public WebhookEndpointService(
            WebhookEndpointRepository webhookEndpointRepository,
            ProjectRepository projectRepository
    ) {
        this.webhookEndpointRepository = webhookEndpointRepository;
        this.projectRepository = projectRepository;
    }

    public CreateWebhookEndpointResponse createEndpoint(
            UUID projectId,
            CreateWebhookEndpointRequest request
    ) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found")
                );

        validateUrl(request.url());

        if (webhookEndpointRepository
                .existsByProject_IdAndUrl(projectId, request.url())) {

            throw new ConflictException(
                    "Webhook endpoint already exists for this project"
            );
        }

        String secret = generateSecret();

        WebhookEndpoint endpoint = new WebhookEndpoint(
                project,
                request.url(),
                secret
        );

        WebhookEndpoint savedEndpoint =
                webhookEndpointRepository.save(endpoint);

        return new CreateWebhookEndpointResponse(
                savedEndpoint.getId(),
                savedEndpoint.getProject().getId(),
                savedEndpoint.getUrl(),
                savedEndpoint.getSecret(),
                savedEndpoint.isActive(),
                savedEndpoint.getCreatedAt()
        );
    }

    public List<WebhookEndpointResponse> getEndpoints(UUID projectId) {

        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found");
        }

        return webhookEndpointRepository
                .findAllByProject_IdOrderByCreatedAtDesc(projectId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private WebhookEndpointResponse mapToResponse(
            WebhookEndpoint endpoint
    ) {
        return new WebhookEndpointResponse(
                endpoint.getId(),
                endpoint.getProject().getId(),
                endpoint.getUrl(),
                endpoint.isActive(),
                endpoint.getCreatedAt()
        );
    }

    private String generateSecret() {

        byte[] bytes = new byte[32];

        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private void validateUrl(String url) {

        try {

            URI uri = new URI(url);

            String scheme = uri.getScheme();

            boolean validScheme =
                    "http".equalsIgnoreCase(scheme)
                            || "https".equalsIgnoreCase(scheme);

            if (!validScheme || uri.getHost() == null) {
                throw new BadRequestException(
                        "Webhook URL must be a valid HTTP or HTTPS URL"
                );
            }

        } catch (URISyntaxException exception) {

            throw new BadRequestException(
                    "Webhook URL must be a valid HTTP or HTTPS URL"
            );
        }
    }
}
