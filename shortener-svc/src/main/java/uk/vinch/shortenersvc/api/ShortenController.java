package uk.vinch.shortenersvc.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uk.vinch.shortenersvc.model.UrlMapping;
import uk.vinch.shortenersvc.repo.UrlMappingRepository;

@RestController
@RequestMapping("/api")
@Validated
public class ShortenController {

    private final UrlMappingRepository repo;

    @Value("${app.base-url}")          // https://u.vinch.uk
    private String baseUrl;

    public ShortenController(UrlMappingRepository repo) {
        this.repo = repo;
    }

    // ----- DTO -----
    public record ShortenRequest(
            @NotBlank @Size(max = 2048) String url) {}

    public record ShortenResponse(String slug, String shortUrl) {}

    @PostMapping("/shorten")
    public ShortenResponse shorten(@RequestBody @Validated ShortenRequest req) {
        String slug = RandomStringUtils.randomAlphanumeric(6);
        UrlMapping mapping = new UrlMapping();
        mapping.setSlug(slug);
        mapping.setOriginalUrl(req.url());
        repo.save(mapping);
        return new ShortenResponse(slug, baseUrl + "/" + slug);
    }
}
