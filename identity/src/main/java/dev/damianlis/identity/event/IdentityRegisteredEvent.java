package dev.damianlis.identity.event;

import java.time.Instant;
import java.util.UUID;

public record IdentityRegisteredEvent(UUID id, String name, String email, IdentityStatus status, Instant createdAt) {
}
