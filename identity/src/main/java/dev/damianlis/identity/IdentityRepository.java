package dev.damianlis.identity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface IdentityRepository extends JpaRepository<Identity, UUID> {

    boolean existsByEmail(String email);

    Optional<Identity> findIdentityByEmail(String email);

    Optional<Identity> findByActivationToken(String activationToken);
}
