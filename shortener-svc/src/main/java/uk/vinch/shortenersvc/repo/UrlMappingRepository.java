package uk.vinch.shortenersvc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.vinch.shortenersvc.model.UrlMapping;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findBySlug(String slug);
}