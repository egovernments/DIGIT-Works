package org.egov.key;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KeyGeneratorTest {
/*
    @Test
    public void testGenSek() throws NoSuchAlgorithmException {
        SecretKey secretKey = KeyGenerator.genAES256Key();
        String key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println(key);
    }
 */

}