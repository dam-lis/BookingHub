package dev.damianlis.identity.exception;

public class IdentityExistException extends RuntimeException {

    public IdentityExistException(String email) {
        super("Identity with email=" + email + " exists");
    }
}
