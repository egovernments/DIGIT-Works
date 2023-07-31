package org.egov.ifms.jit.enc;

import org.junit.jupiter.api.TestInstance;

import java.security.PublicKey;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AsymmetricEncryptionServiceTest {

    PublicKey publicKey;
    String baseURL;
    /*
    @BeforeAll
    public void init() throws Exception {
//        ClassLoader classLoader = this.getClass().getClassLoader();
//        baseURL = classLoader.getResource("").getFile();
        String path = "D:/egovernments/digit-works-bkp2/reference-adapters/ifms-adapter/src/test/resources/";
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
*/

}