package dev.damianlis.identity;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class IdentityConfiguration {

    @Bean
    IdentityFacade identityService(IdentityRepository identityRepository,
                                   PasswordEncoder passwordEncoder,
                                   ApplicationEventPublisher applicationEventPublisher
    ) {
        var activationTokenGenerator = new ActivationTokenGenerator();
        var identityEventPublisher = new IdentitySpringEventPublisher(applicationEventPublisher);
        return new IdentityFacade(
                identityRepository,
                passwordEncoder,
                activationTokenGenerator,
                identityEventPublisher
        );
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(16);
    }

}
