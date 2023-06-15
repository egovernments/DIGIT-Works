package org.egov.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import org.egov.kafka.Producer;
import org.egov.web.models.TestObjectProducer;
import org.egov.service.IfmsService;
import org.egov.utils.AuthenticationUtils;
import org.egov.utils.JitRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private Producer producer;
//    @Autowired
//    @Qualifier("ifmsRestTemplate")
//    private RestTemplate ifmsRestTemplate;

    @Autowired
    IfmsService ifmsService;

    @Autowired
    AuthenticationUtils authenticationUtils;

    @Autowired
    JitRequestUtils jitRequestUtils;

    @RequestMapping(path = "/produce", method = RequestMethod.POST)
    public ResponseEntity<Object> produceRecord(@RequestBody TestObjectProducer testObjectProducer) {
        producer.push(testObjectProducer.getTopic(), testObjectProducer.getJson());
        return new ResponseEntity<>(TextNode.valueOf("Done"), HttpStatus.ACCEPTED);
    }

//    @RequestMapping(path = "/send", method = RequestMethod.POST)
//    public ResponseEntity<Object> send(@RequestBody Object object) {
//        return ifmsRestTemplate.postForEntity("http://localhost:8080/produce", object, Object.class);
//    }

    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody Object object) {

        try {
            Map<String, String> appKeys = ifmsService.getKeys();
            Map<String, String> authResponse = (Map<String, String>) ifmsService.authenticate(appKeys.get("encodedAppKey"));
            appKeys.put("authToken", authResponse.get("authToken"));
            appKeys.put("sek", authResponse.get("sek"));
            String decryptedSek = authenticationUtils.getDecryptedSek(appKeys.get("appKey"), authResponse.get("sek"));
            appKeys.put("decryptedSek", decryptedSek);
            // Convert the map to a ResponseEntity<Object>
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(appKeys, HttpStatus.OK);
            return responseEntity;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/request", method = RequestMethod.POST)
    public ResponseEntity<Object> request(@RequestBody Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonObject = objectMapper.convertValue(object, Map.class);
            Map<String, String> payload = (Map<String, String>) jitRequestUtils.getEncryptedRequestBody(String.valueOf(jsonObject.get("decryptedSek")), jsonObject.get("jitRequest"));
            String response = ifmsService.ifmsJITRequest(String.valueOf(jsonObject.get("authToken")), payload.get("encryptedPayload"), payload.get("encryptionRek"));
            Object decryptedResponse = jitRequestUtils.decryptResponse(payload.get("decryptionRek"), response);
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(decryptedResponse, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
