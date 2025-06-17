package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.web.models.PaymentTransferRequest;
import org.egov.digit.expense.web.models.PaymentTransferResponse;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static org.egov.digit.expense.config.Constants.*;

@Slf4j
@Component
public class MTNUtil {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Configuration config;

    @Autowired
    public MTNUtil(RestTemplate restTemplate,
                           ObjectMapper objectMapper,
                           Configuration config) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.config = config;
    }

    private String getAccessToken() {
        String url = UriComponentsBuilder
                .fromHttpUrl(config.getBaseUrlMTN()+config.getTokenEndpointMTN())
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(MTN_SUBSCRIPTION_KEY_HEADER_NAME, config.getSubscriptionKeyMTN());
        headers.set(MTN_AUTHORIZATION_HEADER_NAME, config.getAuthorizationMTN());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String accessToken = JsonPath.read(response.getBody(), "$.access_token");
                log.info("Access token retrieved successfully");
                return accessToken;
            } else {
                throw new CustomException("TOKEN_FETCH_FAILED",
                        "Failed to fetch token. Status: " + response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Exception while fetching access token", e);
            throw new CustomException("TOKEN_FETCH_EXCEPTION", e.getMessage());
        }
    }

    private boolean isAccountHolderActive(String msisdn, String accessToken) {

        String url = UriComponentsBuilder
                .fromHttpUrl(config.getBaseUrlMTN() + config.getAccountEndpointMTN().replace("{id}",msisdn))
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(MTN_SUBSCRIPTION_KEY_HEADER_NAME, config.getSubscriptionKeyMTN());
        headers.set(MTN_TARGET_ENVIRONMENT_HEADER_NAME, config.getTargetEnvironmentMTN());
        headers.set(MTN_AUTHORIZATION_HEADER_NAME, MTN_ACCESS_TOKEN_TYPE + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return JsonPath.read(response.getBody(), "$.result");
            } else {
                throw new CustomException("MTN_ACCOUNT_STATUS_ERROR",
                        "Unexpected response status: " + response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error checking MTN account status for MSISDN {}", msisdn, e);
            throw new CustomException("MTN_ACCOUNT_STATUS_EXCEPTION", e.getMessage());
        }
    }

    private ObjectNode getBasicUserInfo(String msisdn, String accessToken) {
        String url = UriComponentsBuilder
                .fromHttpUrl(config.getBaseUrlMTN() + config.getBasicUserInfoEndpointMTN().replace("{id}", msisdn))
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(MTN_SUBSCRIPTION_KEY_HEADER_NAME, config.getSubscriptionKeyMTN());
        headers.set(MTN_TARGET_ENVIRONMENT_HEADER_NAME, config.getTargetEnvironmentMTN());
        headers.set(MTN_AUTHORIZATION_HEADER_NAME, MTN_ACCESS_TOKEN_TYPE + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (ObjectNode) objectMapper.readTree(response.getBody());
            } else {
                throw new CustomException("MTN_BASIC_USERINFO_ERROR",
                        "Unexpected response status: " + response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error fetching basic user info for MSISDN {}", msisdn, e);
            throw new CustomException("MTN_BASIC_USERINFO_EXCEPTION", e.getMessage());
        }
    }

    public String getNameIfActive(String msisdn) {
        String accessToken;
        try {
            accessToken = getAccessToken();
        } catch (CustomException e) {
            log.error("Failed to retrieve access token", e);
            throw new CustomException("MTN_TOKEN_FAILURE", "Could not retrieve MTN access token: " + e.getMessage());
        }

        boolean isActive;
        try {
            isActive = isAccountHolderActive(msisdn, accessToken);
        } catch (CustomException e) {
            log.error("Failed to verify account status for MSISDN {}", msisdn, e);
            throw new CustomException("MTN_ACCOUNT_VALIDATION_FAILED", "Account validation failed for MSISDN: " + msisdn);
        }

        if (!isActive) {
            log.warn("Inactive MTN account for MSISDN: {}", msisdn);
            throw new CustomException("MTN_ACCOUNT_INACTIVE", "Account is not active for MSISDN: " + msisdn);
        }

        try {
            ObjectNode userInfo = getBasicUserInfo(msisdn, accessToken);
            if (userInfo.hasNonNull(MTN_USER_GIVEN_NAME_FIELD)) {
                String name = userInfo.get(MTN_USER_GIVEN_NAME_FIELD).asText();
                if (userInfo.hasNonNull(MTN_USER_FAMILY_NAME_FIELD)) {
                    name = name + " " + userInfo.get(MTN_USER_FAMILY_NAME_FIELD).asText();
                }
                return name;
            } else {
                log.error("Missing 'given_name' in user info response for MSISDN: {}", msisdn);
                throw new CustomException("MTN_USERINFO_MISSING_NAME", "Given name is missing in user info for MSISDN: " + msisdn);
            }
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error fetching basic user info for MSISDN {}", msisdn, e);
            throw new CustomException("MTN_USERINFO_FETCH_FAILED", "Could not retrieve user info: " + e.getMessage());
        }
    }

    private void initiateTransfer(PaymentTransferRequest paymentTransferRequest, String referenceId, String accessToken) {

        String url = UriComponentsBuilder
                .fromHttpUrl(config.getBaseUrlMTN() + config.getTransferEndpointMTN())
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(MTN_SUBSCRIPTION_KEY_HEADER_NAME, config.getSubscriptionKeyMTN());
        headers.set(MTN_TARGET_ENVIRONMENT_HEADER_NAME, config.getTargetEnvironmentMTN());
        headers.set(MTN_AUTHORIZATION_HEADER_NAME, MTN_ACCESS_TOKEN_TYPE + accessToken);
        headers.set(MTN_X_REFERENCE_HEADER_NAME, referenceId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> requestEntity;
        try {
            String requestBody = objectMapper.writeValueAsString(paymentTransferRequest);
            requestEntity = new HttpEntity<>(requestBody, headers);
        } catch (Exception e) {
            log.error("Error serializing transfer request body", e);
            throw new CustomException("MTN_REQUEST_SERIALIZATION_ERROR", e.getMessage());
        }

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.ACCEPTED) {
                log.info("Transfer initiated successfully. Reference ID: {}", referenceId);
            } else {
                log.error("Transfer failed. Status: {}, response: {}", response.getStatusCode(), response);
                throw new CustomException("MTN_TRANSFER_FAILED", "Unexpected response status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Exception while initiating transfer", e);
            e.printStackTrace();
            throw new CustomException("MTN_TRANSFER_EXCEPTION", e.getMessage());
        }
    }

    public void transferIfAccountIsActive(PaymentTransferRequest paymentTransferRequest,String referenceId) {

        String accessToken = getAccessToken();

        if (!isAccountHolderActive(paymentTransferRequest.getPayee().getPartyId(), accessToken)) {
            log.warn("Transfer aborted: MTN account {} is not active", paymentTransferRequest.getPayee().getPartyId());
            throw new CustomException("MTN_ACCOUNT_INACTIVE", "The recipient account is not active for transfers");
        }

        try {
            initiateTransfer(
                    paymentTransferRequest,
                    referenceId,
                    accessToken
                    );
        } catch (CustomException e) {
            log.error("MTN transfer failed for MSISDN {} with reference {}", paymentTransferRequest.getPayee().getPartyId(), referenceId, e);
            throw e;
        }
    }

    public PaymentTransferResponse getTransferStatus(String referenceId) {

        String accessToken = getAccessToken();
        String url = UriComponentsBuilder
                .fromHttpUrl(config.getBaseUrlMTN() + config.getTransferStatusEndpointMTN().replace("{referenceId}", referenceId))
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(MTN_SUBSCRIPTION_KEY_HEADER_NAME, config.getSubscriptionKeyMTN());
        headers.set(MTN_TARGET_ENVIRONMENT_HEADER_NAME, config.getTargetEnvironmentMTN());
        headers.set(MTN_AUTHORIZATION_HEADER_NAME, MTN_ACCESS_TOKEN_TYPE + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("Transfer status retrieved for referenceId {}", referenceId);
                return objectMapper.readValue(response.getBody(), PaymentTransferResponse.class); // Or parse specific fields if needed
            } else {
                throw new CustomException("MTN_TRANSFER_STATUS_ERROR",
                        "Unexpected response status while fetching transfer status: " + response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error while retrieving transfer status for referenceId {}", referenceId, e);
            throw new CustomException("MTN_TRANSFER_STATUS_EXCEPTION", e.getMessage());
        }
    }

}
