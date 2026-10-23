package org.example.webhookhub.project;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/endpoints")
public class WebhookEndpointController {

    private final WebhookEndpointService webhookEndpointService;

    public WebhookEndpointController(WebhookEndpointService webhookEndpointService){
        this.webhookEndpointService = webhookEndpointService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateWebhookEndpointResponse createEndpoint(@PathVariable UUID projectId, @Valid @RequestBody CreateWebhookEndpointRequest request){

        return webhookEndpointService.createEndpoint(projectId,request);
    }

    @GetMapping
    public List<WebhookEndpointResponse> getEndpoint(@PathVariable UUID projectId){
        return webhookEndpointService.getEndpoints(projectId);
    }
}
