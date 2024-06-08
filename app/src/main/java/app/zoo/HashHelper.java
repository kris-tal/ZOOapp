package app.zoo;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelper {
    public static String hashPassword(String str) {
        Integer hash = 0;
        int prime = 31;
        int mod = 1_000_000_007;

        for (int i = 0; i < str.length(); i++) {
            hash = (hash * prime + str.charAt(i)) % mod;
        }

        return hash.toString();
    }
}
