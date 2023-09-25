package org.egov.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.config.IfmsAdapterConfig;
import org.egov.enc.SymmetricEncryptionService;
import org.egov.key.KeyGenerator;
import org.egov.web.models.jit.JITResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * This clas provides methods to encrypt/decrypt JIT responses.
 *
 */
@Component
public class JitRequestUtils {
    @Autowired
    IfmsAdapterConfig config;

    @Autowired
    ObjectMapper objectMapper;

    public Object getEncryptedRequestBody(String sekString, Object jitRequest) throws Exception {
        //Get SEK bytes
        byte[] sekBytes = Base64.getDecoder().decode(sekString);
        //Construct secret key using SEK bytes
        SecretKey sekSecretKey = new SecretKeySpec(sekBytes, "AES");
        // Convert the JSON object to a string
        String requestBody = objectMapper.writeValueAsString(jitRequest);
        byte[] plainBytes = requestBody.getBytes();
        //Encrypt the request body with the decrypted SEK
        String encReqBody = SymmetricEncryptionService.encrypt(plainBytes, sekSecretKey);
        System.out.println("Encrypted Req Body : " + encReqBody);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("encryptedPayload", encReqBody);

        SecretKey rek = KeyGenerator.genAES256Key();
        String decryptedRek = Base64.getEncoder().encodeToString(rek.getEncoded());
        System.out.println("Decrypted REK : " + decryptedRek);
        requestMap.put("decryptionRek", decryptedRek);

        byte[] rekBytes = rek.getEncoded();
        String encryptedRek = SymmetricEncryptionService.encrypt(rekBytes, sekSecretKey);
        System.out.println("Encrypted REK : " + encryptedRek);
        requestMap.put("encryptionRek", encryptedRek);

        return requestMap;
    }

    public JITResponse decryptResponse(String decryptedRek, String encryptedResponse) throws Exception {
        byte[] secret = Base64.getDecoder().decode(decryptedRek);
        SecretKey secretKey = new SecretKeySpec(secret, "AES");
        byte[] plainBytes = SymmetricEncryptionService.decrypt(encryptedResponse, secretKey);
        String plaintext = new String(plainBytes);
        JITResponse response = objectMapper.readValue(plaintext, JITResponse.class);
        System.out.println("Decrypted jit response : " + response);
        return response;
    }

}
