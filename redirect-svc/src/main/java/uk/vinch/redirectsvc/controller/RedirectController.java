package uk.vinch.redirectsvc.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.vinch.redirectsvc.model.ClickEvent;
import uk.vinch.shortenersvc.model.UrlMapping;
import uk.vinch.shortenersvc.repo.UrlMappingRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;

@RestController
public class RedirectController {

    private final UrlMappingRepository repo;
    private final StringRedisTemplate redis;
    private final KafkaTemplate<String, ClickEvent> kafka;
    private final Duration cacheTtl;

    public RedirectController(UrlMappingRepository repo,
                              StringRedisTemplate redis,
                              KafkaTemplate<String, ClickEvent> kafka) {
        this.repo = repo;
        this.redis = redis;
        this.kafka = kafka;
        this.cacheTtl = Duration.ofMinutes(60);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Void> redirect(@PathVariable("slug") @NotBlank String slug) {

        String url = redis.opsForValue().get(slug);

        if (url == null) {
            url = repo.findBySlug(slug).map(UrlMapping::getOriginalUrl).orElse(null);
            if (url != null) {
                redis.opsForValue().set(slug, url, cacheTtl);
            }
        }

        if (url == null) {
            return ResponseEntity.notFound().build();
        }

        kafka.send("clicks", slug, new ClickEvent(slug, Instant.now()));

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, url)
                .build();
    }
}
