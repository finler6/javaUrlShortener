package uk.vinch.shortenersvc.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class UrlMapping {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 16)
    private String slug;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    private Instant createdAt = Instant.now();

    public UrlMapping() {}
    public Long getId() { return id; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
    public Instant getCreatedAt() { return createdAt; }
}