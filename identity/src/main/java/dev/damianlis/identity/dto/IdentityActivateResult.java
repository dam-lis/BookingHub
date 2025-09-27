package dev.damianlis.identity.dto;

import java.util.UUID;

public record IdentityActivateResult(UUID id, IdentityStatus status) {
}
