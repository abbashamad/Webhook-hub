package org.example.webhookhub.endpoint;

import jakarta.persistence.*;
import org.example.webhookhub.project.Project;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "webhook_endpoints")
public class WebhookEndpoint {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false, length = 2048)
    private String url;

    @Column(nullable = false)
    private String secret;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public WebhookEndpoint(Project project, String url, String secret){
        this.id = UUID.randomUUID();
        this.project = project;
        this.url = url;
        this.secret = secret;
        this.active = true;
        this.createdAt = Instant.now();
    }

    public WebhookEndpoint() {

    }

    public UUID getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public String getUrl() {
        return url;
    }

    public String getSecret() {
        return secret;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
