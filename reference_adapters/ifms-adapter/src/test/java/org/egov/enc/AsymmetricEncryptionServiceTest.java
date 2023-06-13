package org.egov.enc;

import org.egov.key.KeyGenerator;
import org.egov.key.PublicKeyLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AsymmetricEncryptionServiceTest {

    PublicKey publicKey;
    String baseURL;

    @BeforeAll
    public void init() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
//        baseURL = classLoader.getResource("").getFile();
        String path = "D:/egovernments/ifix-dev-bkp/reference-adapter/ifms-adapter/src/main/resources/";
        publicKey = PublicKeyLoader.getPublicKeyFromByteFile(path + "publicKey");
    }

    @Test
    public void genAppKey() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {
        SecretKey secretKey = KeyGenerator.genAES256Key();
        String appKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println(appKey);
        String plaintext = appKey;
        byte[] plainBytes = Base64.getDecoder().decode(plaintext);
        byte[] cipherBytes = AsymmetricEncryptionService.encrypt(plainBytes, publicKey);
        String ciphertext = Base64.getEncoder().encodeToString(cipherBytes);
        System.out.println(ciphertext);
    }


}