package dev.damianlis.identity.exception;

public class ActivationTokenNotFoundException extends RuntimeException {

    public ActivationTokenNotFoundException(String activationToken) {
        super("activationToken=" + activationToken + " not found");
    }
}
