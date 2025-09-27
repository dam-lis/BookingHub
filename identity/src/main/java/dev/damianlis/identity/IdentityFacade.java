package dev.damianlis.identity;

import dev.damianlis.identity.dto.IdentityActivateResult;
import dev.damianlis.identity.dto.IdentityRegisterResult;
import dev.damianlis.identity.event.IdentityActivatedEvent;
import dev.damianlis.identity.event.IdentityRegisteredEvent;
import dev.damianlis.identity.exception.ActivationTokenNotFoundException;
import dev.damianlis.identity.exception.IdentityExistException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

public class IdentityFacade {

    private final IdentityRepository identityRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivationTokenGenerator activationTokenGenerator;
    private final IdentityEventPublisher identityEventPublisher;

    IdentityFacade(
            IdentityRepository identityRepository,
            PasswordEncoder passwordEncoder,
            ActivationTokenGenerator activationTokenGenerator,
            IdentityEventPublisher identityEventPublisher
    ) {
        this.identityRepository = identityRepository;
        this.passwordEncoder = passwordEncoder;
        this.activationTokenGenerator = activationTokenGenerator;
        this.identityEventPublisher = identityEventPublisher;
    }

    @Transactional
    public IdentityRegisterResult register(String email, String name, String rawPassword) {
        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);
        if (identityRepository.existsByEmail(normalizedEmail)) {
           throw new IdentityExistException(normalizedEmail);
        }
        UUID id = UUID.randomUUID();
        String passwordHashed = passwordEncoder.encode(rawPassword);
        String activationToken = activationTokenGenerator.generate();
        Instant createdAt = Instant.now();

        Identity identity = new Identity(
                id,
                normalizedEmail,
                name,
                passwordHashed,
                Identity.Status.PENDING,
                activationToken, createdAt);
        identityRepository.save(identity);

        identityEventPublisher.publishIdentityRegisteredEvent(createIdentityRegisteredEvent(identity));
        return createRegisterResult(identity);
    }

    @Transactional
    public IdentityActivateResult activate(String activationToken) {
        Identity identity = identityRepository
                .findByActivationToken(activationToken)
                .orElseThrow(() -> new ActivationTokenNotFoundException(activationToken));

        identity.activate();

        identityEventPublisher.publishIdentityActivatedEvent(createIdentityActivatedEvent(identity));
        return createActivateResult(identity);
    }

    private IdentityRegisteredEvent createIdentityRegisteredEvent(Identity identity) {
        var identityStatus = dev.damianlis.identity.event.IdentityStatus.valueOf(identity.getStatus().name());
        return new IdentityRegisteredEvent(
                identity.getId(),
                identity.getName(),
                identity.getEmail(),
                identityStatus,
                identity.getCreatedAt()
        );
    }

    private IdentityRegisterResult createRegisterResult(Identity identity) {
        var identityStatus = dev.damianlis.identity.dto.IdentityStatus.valueOf(identity.getStatus().name());
        return new IdentityRegisterResult(
                identity.getId(),
                identity.getName(),
                identity.getEmail(),
                identityStatus,
                identity.getCreatedAt()
        );
    }

    private IdentityActivatedEvent createIdentityActivatedEvent(Identity identity) {
        var identityStatus = dev.damianlis.identity.event.IdentityStatus.valueOf(identity.getStatus().name());
        return new IdentityActivatedEvent(identity.getId(), identityStatus);
    }

    private IdentityActivateResult createActivateResult(Identity identity) {
        var identityStatus = dev.damianlis.identity.dto.IdentityStatus.valueOf(identity.getStatus().name());
        return new IdentityActivateResult(identity.getId(), identityStatus);
    }
}
