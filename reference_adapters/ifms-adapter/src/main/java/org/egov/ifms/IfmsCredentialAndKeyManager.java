package org.egov.ifms;

import lombok.Getter;
import org.egov.key.PublicKeyLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.security.PublicKey;

@Component
@Getter
public class IfmsCredentialAndKeyManager {
    private PublicKey publicKey;
    private String authToken;
    private SecretKey sessionKey;

    @Value("${ifms.jit.public.key.file}")
    private String publicKeyFilePath;

    @PostConstruct
    public void init() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        String publicKeyURL = classLoader.getResource(publicKeyFilePath).getFile();
        publicKey = PublicKeyLoader.getPublicKeyFromByteFile(publicKeyURL);
        System.out.println(new String(publicKey.getEncoded()));
    }

}
