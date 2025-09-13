package uk.vinch.redirectsvc.model;

import java.time.Instant;

public record ClickEvent(String slug, Instant ts) {}
