package dev.damianlis.identity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//TODO repo przygotowane pod projekcje
public interface IdentityQueryRepository extends JpaRepository<Identity, UUID> {


}
