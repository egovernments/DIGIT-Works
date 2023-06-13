package org.egov.enc;

import org.egov.key.KeyGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SymmetricEncryptionServiceTest {

    private String baseURL;
    private String requestFilePath = "request.json";

    @BeforeAll
    public void init() {
        ClassLoader classLoader = this.getClass().getClassLoader();
//        baseURL = classLoader.getResource("").getFile();
        baseURL = "D:/egovernments/ifix-dev-bkp/reference-adapter/ifms-adapter/src/test/resources/";
    }

    @Test
    public void testDecryptSek() throws Exception {
        String appKey = "Todp9MQORzqQu7s/24Uq8U2hcpp75XooKAFZxv0johQ=";
        String ciphertext = "/pGQuLeJzrmXOUCfAqzRkeuY3gWf0hUcxO+Ho8Dmr586eToWjeut3FFJcKxcGKIW";
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(appKey), "AES");
        byte[] sek = SymmetricEncryptionService.decrypt(ciphertext, secretKey);
        String sekString = Base64.getEncoder().encodeToString(sek);
        System.out.println(sekString);
    }

    @Test
    public void testDecryptResponse() throws Exception {
        String rek = "RiMywvWvsDYYEeJEIbC+xCtm0Gd5akpikYIS9lnCt30=";
        String ciphertext = "JZBAfTZEW+GvsNN7KhvEs0t9lUo8B+YIvPemG9kDizeLEWUDsELyXW5CGikeqa8U";
        byte[] secret = Base64.getDecoder().decode(rek);
        SecretKey secretKey = new SecretKeySpec(secret, "AES");
        byte[] plainBytes = SymmetricEncryptionService.decrypt(ciphertext, secretKey);
        String plaintext = new String(plainBytes);
        System.out.println(plaintext);
    }

    @Test
    public void testEncrypt() throws Exception {
        String sekString = "xdGB1N4X0YvzsTQQn5iubiixHAZBicwKPr9BngrJ1hw=";
        byte[] sekBytes = Base64.getDecoder().decode(sekString);
        SecretKey sekSecretKey = new SecretKeySpec(sekBytes, "AES");
        final byte[] fileBytes = Files.readAllBytes(Paths.get(baseURL + "request.json"));
        String requestBody = new String(fileBytes);
        System.out.println(requestBody);
        byte[] plainBytes = requestBody.getBytes();
        //Encrypt the request body with the decrypted SEK
        String encReqBody = SymmetricEncryptionService.encrypt(plainBytes, sekSecretKey);
        System.out.println("Encrypted Req Body : " + encReqBody);
        //Construct the REK to be sent with the request packet
//        String rek = "e1ro8WIs7BsGYwHtBbTWAWrmR/yzJSCoksOaMmOO0SI=";
        SecretKey rek = KeyGenerator.genAES256Key();
//        byte[] rekBytes = Base64.getDecoder().decode(rek);
        System.out.println("Decrypted REK : " + Base64.getEncoder().encodeToString(rek.getEncoded()));
        byte[] rekBytes = rek.getEncoded();
        String encryptedRek = SymmetricEncryptionService.encrypt(rekBytes, sekSecretKey);
        System.out.println("Encrypted REK : " + encryptedRek);
    }

    @Test
    public void testVAEncrypt() throws Exception {
        String sekString = "cOmbKHRXc+1nSYS/UcckYilFotqtc+G5t/9tJwZJ9Q4=";
        //Get SEK bytes
        byte[] sekBytes = Base64.getDecoder().decode(sekString);
        //Construct secret key using SEK bytes
        SecretKey sekSecretKey = new SecretKeySpec(sekBytes, "AES");
        //Fetch the request body
//        final byte[] fileBytes = Files.readAllBytes(Paths.get(baseURL + "1VARequest.json"));
//        String requestBody = new String(fileBytes);
        String requestBody = "{\"serviceId\":\"VA\",\"params\":{\"hoa\":\"132217058003586410459082112\",\"fromDate\":\"2023-05-10 16:26:42\",\"ddoCode\":\"OLSHUD001\",\"granteCode\":\"GOHUDULBMPL0036\"}}";
        System.out.println(requestBody);
        byte[] plainBytes = requestBody.getBytes();
        //Encrypt the request body with the decrypted SEK
        String encReqBody = SymmetricEncryptionService.encrypt(plainBytes, sekSecretKey);
        System.out.println("Encrypted Req Body : " + encReqBody);
        //Construct the REK to be sent with the request packet
//        String rek = "e1ro8WIs7BsGYwHtBbTWAWrmR/yzJSCoksOaMmOO0SI=";
        SecretKey rek = KeyGenerator.genAES256Key();
//        byte[] rekBytes = Base64.getDecoder().decode(rek);
        System.out.println("Decrypted REK : " + Base64.getEncoder().encodeToString(rek.getEncoded()));
        byte[] rekBytes = rek.getEncoded();
        String encryptedRek = SymmetricEncryptionService.encrypt(rekBytes, sekSecretKey);
        System.out.println("Encrypted REK : " + encryptedRek);
    }

    @Test
    public void testPIEncrypt() throws Exception {
        String sekString = "cOmbKHRXc+1nSYS/UcckYilFotqtc+G5t/9tJwZJ9Q4=";
        //Get SEK bytes
        byte[] sekBytes = Base64.getDecoder().decode(sekString);
        //Construct secret key using SEK bytes
        SecretKey sekSecretKey = new SecretKeySpec(sekBytes, "AES");
        //Fetch the request body
        final byte[] fileBytes = Files.readAllBytes(Paths.get(baseURL + "2PIRequest.json"));
        String requestBody = new String(fileBytes);
        System.out.println(requestBody);
        byte[] plainBytes = requestBody.getBytes();
        //Encrypt the request body with the decrypted SEK
        String encReqBody = SymmetricEncryptionService.encrypt(plainBytes, sekSecretKey);
        System.out.println("Encrypted Req Body : " + encReqBody);
        //Construct the REK to be sent with the request packet
//        String rek = "e1ro8WIs7BsGYwHtBbTWAWrmR/yzJSCoksOaMmOO0SI=";
        SecretKey rek = KeyGenerator.genAES256Key();
//        byte[] rekBytes = Base64.getDecoder().decode(rek);
        System.out.println("Decrypted REK : " + Base64.getEncoder().encodeToString(rek.getEncoded()));
        byte[] rekBytes = rek.getEncoded();
        String encryptedRek = SymmetricEncryptionService.encrypt(rekBytes, sekSecretKey);
        System.out.println("Encrypted REK : " + encryptedRek);
    }

    @Test
    public void testPISEncrypt() throws Exception {
        String sekString = "cOmbKHRXc+1nSYS/UcckYilFotqtc+G5t/9tJwZJ9Q4=";
        //Get SEK bytes
        byte[] sekBytes = Base64.getDecoder().decode(sekString);
        //Construct secret key using SEK bytes
        SecretKey sekSecretKey = new SecretKeySpec(sekBytes, "AES");
        //Fetch the request body
        final byte[] fileBytes = Files.readAllBytes(Paths.get(baseURL + "3PISRequest.json"));
        String requestBody = new String(fileBytes);
        System.out.println(requestBody);
        byte[] plainBytes = requestBody.getBytes();
        //Encrypt the request body with the decrypted SEK
        String encReqBody = SymmetricEncryptionService.encrypt(plainBytes, sekSecretKey);
        System.out.println("Encrypted Req Body : " + encReqBody);
        //Construct the REK to be sent with the request packet
//        String rek = "e1ro8WIs7BsGYwHtBbTWAWrmR/yzJSCoksOaMmOO0SI=";
        SecretKey rek = KeyGenerator.genAES256Key();
//        byte[] rekBytes = Base64.getDecoder().decode(rek);
        System.out.println("Decrypted REK : " + Base64.getEncoder().encodeToString(rek.getEncoded()));
        byte[] rekBytes = rek.getEncoded();
        String encryptedRek = SymmetricEncryptionService.encrypt(rekBytes, sekSecretKey);
        System.out.println("Encrypted REK : " + encryptedRek);
    }
}