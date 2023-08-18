package org.egov.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.egov.config.IfmsAdapterConfig;
import org.egov.enc.AsymmetricEncryptionService;
import org.egov.enc.SymmetricEncryptionService;
import org.egov.key.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * This class houses utility methods to read auth related artifacts and keys.
 *
 */
@Component
public class AuthenticationUtils {

    @Autowired
    private IfmsAdapterConfig config;
    @Autowired
    private ResourceLoader resourceLoader;

    private PublicKey publicKey;

    AuthenticationUtils(){}

    @PostConstruct
    public void initialize() throws Exception {
        InputStream publicKeyFile = null;
        try {
            Resource resource = resourceLoader.getResource("classpath:publicKey");
            publicKeyFile = resource.getInputStream();
            byte[] publicKeyBytes = getBytesFromInputStream(publicKeyFile);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException, NoSuchAlgorithmException, IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteStream.write(buffer, 0, bytesRead);
        }
        return byteStream.toByteArray();
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
