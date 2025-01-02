package org.egov.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.service.PaymentInstructionService;
import org.egov.service.VirtualAllotmentService;
import org.egov.web.models.jit.PaymentInstruction;
import org.egov.web.models.jit.SchedulerRequest;
import org.egov.web.models.bill.PaymentRequest;
import org.egov.service.IfmsService;
import org.egov.utils.AuthenticationUtils;
import org.egov.utils.JitRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    IfmsService ifmsService;

    @Autowired
    AuthenticationUtils authenticationUtils;

    @Autowired
    JitRequestUtils jitRequestUtils;

    @Autowired
    PaymentInstructionService paymentInstruction;

    @Autowired
    VirtualAllotmentService virtualAllotmentService;

    @Autowired
    private ResourceLoader resourceLoader;

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

    @RequestMapping(path = "/create-sanction-allotments", method = RequestMethod.POST)
    public ResponseEntity<Object> createSanctionAllotments(@RequestBody SchedulerRequest schedulerRequest) {
        try {
            virtualAllotmentService.generateVirtualAllotment(schedulerRequest.getRequestInfo());
            ResponseEntity<Object> responseEntity = new ResponseEntity<>("Generated", HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
