package dev.damianlis.identity.event;

import java.util.UUID;

public record IdentityActivatedEvent(UUID id, IdentityStatus status) {
}
