package dev.damianlis.identity;

import dev.damianlis.identity.event.IdentityActivatedEvent;
import dev.damianlis.identity.event.IdentityRegisteredEvent;

interface IdentityEventPublisher {

    void publishIdentityRegisteredEvent(IdentityRegisteredEvent event);

    void publishIdentityActivatedEvent(IdentityActivatedEvent event);
}
