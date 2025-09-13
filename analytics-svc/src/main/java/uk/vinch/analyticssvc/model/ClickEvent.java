package uk.vinch.analyticssvc.model;

import java.time.Instant;

public record ClickEvent(String slug, Instant ts) {}
