package dev.damianlis.identity.dto;

import java.time.Instant;
import java.util.UUID;

public record IdentityRegisterResult(UUID id, String name, String email, IdentityStatus status, Instant createdAt) {
}
