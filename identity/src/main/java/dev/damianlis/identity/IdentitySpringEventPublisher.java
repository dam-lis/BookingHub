package dev.damianlis.identity;

import dev.damianlis.identity.event.IdentityActivatedEvent;
import dev.damianlis.identity.event.IdentityRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;

class IdentitySpringEventPublisher implements IdentityEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    IdentitySpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishIdentityRegisteredEvent(IdentityRegisteredEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void publishIdentityActivatedEvent(IdentityActivatedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
