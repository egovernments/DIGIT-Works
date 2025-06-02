package org.egov.digit.expense.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.expense.config.Configuration;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

}
