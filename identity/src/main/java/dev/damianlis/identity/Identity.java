package dev.damianlis.identity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "identities")
class Identity {

    @Id
    private UUID id;

    private String email;

    private String name;

    private String passwordHashed;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String activationToken;

    private Instant createdAt;

    @Version
    private int version;

    private Identity() {}

    Identity(UUID id, String email, String name, String passwordHashed, Status status, String activationToken, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.passwordHashed = passwordHashed;
        this.status = status;
        this.activationToken = activationToken;
        this.createdAt = createdAt;
    }

    public void activate() {
        status = Status.ACTIVE;
        activationToken = null;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Status getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    enum Status {
        PENDING,
        ACTIVE
    }
}
