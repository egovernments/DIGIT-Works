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
    IfmsAdapterConfig config;
    @Autowired
    ResourceLoader resourceLoader;

    PublicKey publicKey;

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

    public PublicKey getPublicKey() throws Exception {
        String pk = config.getIfmsJitPublic();
        String publicKeyPEM = pk
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
//
//        byte[] keyBytes = Base64.getDecoder().decode(config.getIfmsJitPublic());
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        return keyFactory.generatePublic(keySpec);
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
