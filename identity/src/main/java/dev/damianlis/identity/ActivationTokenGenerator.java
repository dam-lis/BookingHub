package dev.damianlis.identity;

import java.security.SecureRandom;
import java.util.HexFormat;

class ActivationTokenGenerator {

    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * @return 64-znakowy token hex (32 bajty losowe)
     */
    public String generate() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return HexFormat.of().formatHex(bytes);
    }
}
