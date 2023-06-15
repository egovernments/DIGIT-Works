package org.egov.utils;

import org.egov.config.IfmsAdapterConfig;
import org.egov.enc.AsymmetricEncryptionService;
import org.egov.enc.SymmetricEncryptionService;
import org.egov.key.KeyGenerator;
import org.egov.key.PublicKeyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

@Component
public class AuthenticationUtils {

    @Autowired
    IfmsAdapterConfig config;

    PublicKey publicKey;

    AuthenticationUtils() throws Exception {
//        String path = config.getIfmsJitPublicKeyPath();
//        String fileName = config.getIfmsJitPublicKeyFile();
        String path = "D:/egovernments/digit-works-bkp-3/reference-adapter/ifms-adapter/src/main/resources/";
        String fileName = "publicKey";
        publicKey = PublicKeyLoader.getPublicKeyFromByteFile(path + fileName);
    }

    public String genEncryptedAppKey() throws NoSuchAlgorithmException {
        SecretKey secretKey = KeyGenerator.genAES256Key();
        String appKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        return appKey;
    }

    public String genEncodedAppKey(String appKey) {
        byte[] plainBytes = Base64.getDecoder().decode(appKey);
        byte[] cipherBytes = new byte[0];
        try {
            cipherBytes = AsymmetricEncryptionService.encrypt(plainBytes, publicKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String ciphertext = Base64.getEncoder().encodeToString(cipherBytes);
        return ciphertext;
    }

    public String getDecryptedSek(String appKey, String encSek) throws Exception {
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(appKey), "AES");
        byte[] sek = SymmetricEncryptionService.decrypt(encSek, secretKey);
        String decryptedSek = Base64.getEncoder().encodeToString(sek);
        System.out.println(decryptedSek);
        return decryptedSek;
    }

}
