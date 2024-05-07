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
    /*
    private String baseURL;
    private String requestFilePath = "request.json";

    @BeforeAll
    public void init() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        baseURL = classLoader.getResource("").getFile();
    }

    @Test
    public void testDecryptSek() throws Exception {
        String appKey = "BL8WKMi6pst4vLW9vswHk7KRj15NrblVpRMHQlOgOOM=";
        String ciphertext = "TLVPoVJdfuj9D8rYz8NImvL8FVemg9ONmbSBkmYmbfLr+tp1iMfmXIlM5jnGrKT0";
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(appKey), "AES");
        byte[] sek = SymmetricEncryptionService.decrypt(ciphertext, secretKey);
        String sekString = Base64.getEncoder().encodeToString(sek);
        System.out.println(sekString);
    }

    @Test
    public void testDecryptResponse() throws Exception {
        String rek = "sqqwkxdHQaKowuPshVXaQpFLrbJz69PtJeLOv8hSGgg=";
        String ciphertext = "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";
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
        String sekString = "xqWZVU6xXVysFt+DUyvTDFwVTywPgGNCBz4g/YfuZRY=";
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
        String sekString = "xqWZVU6xXVysFt+DUyvTDFwVTywPgGNCBz4g/YfuZRY=";
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
        String sekString = "5H6ov3/jvmLwkeiyIrLo4E6etrPKd6JG+QE11bmjJIc=";
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

    @Test
    public void testPAGEncrypt() throws Exception {
        String sekString = "BvV99s/hdTnV+6MFdmVHPqPK6uY6mUAqBNcFyS1Ewnc=";
        //Get SEK bytes
        byte[] sekBytes = Base64.getDecoder().decode(sekString);
        //Construct secret key using SEK bytes
        SecretKey sekSecretKey = new SecretKeySpec(sekBytes, "AES");
        //Fetch the request body
        final byte[] fileBytes = Files.readAllBytes(Paths.get(baseURL + "4PAGRequest.json"));
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

     */

    @Test
    public void testIFMSDecryptResponse() throws Exception {
        String rek = "osFuJQQlPe3q0CoOemNrujjRRNwrEKpQbVpZf8JR6tc=";
        String ciphertext = "A7AYiOdLWeQrco4q6DW5WFqvuw5GaIrL4hZRJOXlJf9rLGZbc4ZwjiZwSjlAcNy9YAz3iGj3W7wopCEEI9tM/zwZC5jbW6BmJHoVVVnNpnDfJD+xyRDeRecyp5NPP/ApJaGtynzBa+Z5n8bIaZNLNKwXdDEr3efR1D2WPU87eveNGEc0yqupuDG3Iio11/1H4HUclc1sBVi4/LruwbIDpByB/DP5lr+5hV8AdbBMMZ1CIFG0tJI2vRXG6rKXTlPaHO2ePimy63k7Or+ZRkwUSA==";        byte[] secret = Base64.getDecoder().decode(rek);
        SecretKey secretKey = new SecretKeySpec(secret, "AES");
        byte[] plainBytes = SymmetricEncryptionService.decrypt(ciphertext, secretKey);
        String plaintext = new String(plainBytes);
        System.out.println(plaintext);
    }
    @Test
    public void testSecretEncryptRequest() throws Exception {
        String secretKey = "bssnx12vlsve5221";
        String encryptedRequest = "n9MBzNvRy99xbqS0ozAb0r+TI+jf/AcSkAoahafLbXZYOgVGtsNhlcNAOHDO7/i32ufBfCH0aUrPbKNYCAocJbSkf9XcIB1cTzpF0gX1nLlhuOeF0N8ZzfrqB4DMr7vZvENWm2uBEJUsq4RoNh5aMrbZHcyISPbd1X6OlRralNk/G3klXSKoBS87iu0YMJbe1f/aTOKpWjCA9lhiffLoudQ5OXs0NNoqq8i/vCPQLpyZsnCEO3rmSRzONljSC78X+PV3xjGF3HZUkuYu9wuquszZATEe01YLD/uSle9kyUxKgW6qnJcd+D7eCHZvYe6vAYLkM81YWf3f8qhcwn5i8R/7ydNHeza9m+ZEsk42Q5NzA7Rb6L8lLFG02gB95lv/i8GX2RpH64Sg8e4AAvI+Q/R8Sf8b1dIM3ecJhiW00Fg995HYnC85jlaq1XTmf9y3m0EZ91GB2XhN9nvP0gcpPS7F8UqgFJ2I4apG/ABSUsXDf5c3t94gGS0S1VZ+7rIQeh6DJgPZrwrm68XGIFHj9qXTkGcLfl2N3VA5HByNux8=";
        String decryptedRequest = SymmetricEncryptionService.decryptRequest(encryptedRequest, secretKey);
        System.out.println("decryptedRequest : " + decryptedRequest);
    }
}