package org.egov.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import digit.models.coremodels.IdGenerationRequest;
import digit.models.coremodels.IdGenerationResponse;
import digit.models.coremodels.IdRequest;
import digit.models.coremodels.IdResponse;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.config.Configuration;
import org.egov.repository.ServiceRequestRepository;
import org.egov.tracer.model.CustomException;
import org.egov.tracer.model.ServiceCallException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserIndividualMigrationUtil {

    @Autowired
    private Configuration config;
    @Autowired
    private ServiceRequestRepository restRepo;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public void migrate(RequestInfo requestInfo) {
        log.info("Starting migration");

        String contactDetailsQuery = "SELECT id, tenant_id, org_id, contact_name, contact_mobile_number, contact_email, individual_id FROM eg_org_contact_detail WHERE individual_id IS NULL;";

        List<Map<String,Object>> orgContactDetails = jdbcTemplate.queryForList(contactDetailsQuery);

        log.info("Fetched Org details");
        for (Map<String,Object> orgContactDetail : orgContactDetails) {

            String individualUuid = (String) orgContactDetail.get("id");
            String contact_mobile_number = (String) orgContactDetail.get("contact_mobile_number");
            String tenant_id = (String) orgContactDetail.get("tenant_id");
            if (tenant_id.contains("."))
                tenant_id = tenant_id.split("\\.")[0];
            Map<String, String> encryptRequestMap = new HashMap<>();
            encryptRequestMap.put("contact_mobile_number", contact_mobile_number);
            encryptRequestMap.put("tenant_id", tenant_id);
            log.info("Calling for encryption");
            Map<String, String> encryptedValues = encryptionDecryptionUtil(encryptRequestMap, true);
            String encryptedMobileNumber = encryptedValues.get("contact_mobile_number");

            log.info("Encrypted response :: " + encryptedMobileNumber);
            String userDetailsQuery =  "SELECT userdata.title, userdata.salutation, userdata.dob, userdata.locale, userdata.username, userdata" +
                    ".password, userdata.pwdexpirydate,  userdata.mobilenumber, userdata.altcontactnumber, userdata.emailid, userdata.createddate, userdata" +
                    ".lastmodifieddate,  userdata.createdby, userdata.lastmodifiedby, userdata.active, userdata.name, userdata.gender, userdata.pan, userdata.aadhaarnumber, userdata" +
                    ".type,  userdata.version, userdata.guardian, userdata.guardianrelation, userdata.signature, userdata.accountlocked, userdata.accountlockeddate, userdata" +
                    ".bloodgroup, userdata.photo, userdata.identificationmark,  userdata.tenantid, userdata.id, userdata.uuid, userdata.alternatemobilenumber, ur.role_code as role_code, ur.role_tenantid as role_tenantid \n" +
                    "\tFROM eg_user userdata LEFT OUTER JOIN eg_userrole_v1 ur ON userdata.id = ur.user_id AND userdata.tenantid = ur.user_tenantid WHERE userdata.username = '"+encryptedMobileNumber+"' AND userdata.type = 'CITIZEN';";

            log.info("Fetching user details");

            List<Map<String,Object>> userDetails = jdbcTemplate.queryForList(userDetailsQuery);
            log.info("Fetched user details");
            if (userDetails == null || userDetails.isEmpty()) {
                log.info("Userdetails not found for encrypted number :: "+encryptedMobileNumber);
                continue;
            }
            Map<String, Object> userDetail = userDetails.get(0);

            Map<String, String> decrypt = new HashMap<>();

            String userName = (String) userDetail.get("username");
            String mobileNumber = (String) userDetail.get("mobilenumber");
            String emailId = (String) userDetail.get("emailid");
            Boolean isSystemUserActive = (Boolean) userDetail.get("active");
            String name = (String) userDetail.get("name");
            String type = (String) userDetail.get("type");
            String tenantId = (String) userDetail.get("tenantid");
            Long userId = (Long) userDetail.get("id");
            String userUuid = (String) userDetail.get("uuid");
            log.info("Generating user roles");
            Role role = populateRole(userDetail);
            log.info("Generated user roles");
            decrypt.put("username", userName);
            decrypt.put("mobilenumber", mobileNumber);
            decrypt.put("emailid", emailId);
            decrypt.put("name", name);
            log.info("Calling for decryption");
            Map<String, String> decryptedValues = encryptionDecryptionUtil(decrypt, false);

            String decryptedUsername = decryptedValues.get("username");

            List<Role> roles = Collections.singletonList(role);
            PGobject roleJson = getPGObject(roles);
            String lastModifiedBy = requestInfo.getUserInfo().getUuid();

            Long lastModifiedTime = System.currentTimeMillis();
            String individualInsertQuery = null;
            log.info("Updating individual details for individualUuid :: "+individualUuid);
            individualInsertQuery = "UPDATE individual SET userId="+userId+", userUuid='"+userUuid+"', isSystemUserActive="+isSystemUserActive+", username='"+decryptedUsername+"', type='"+type+"', roles='"+roleJson+"', lastmodifiedby='"+lastModifiedBy+"', lastmodifiedtime="+lastModifiedTime+" WHERE id='"+individualUuid+"';";
            jdbcTemplate.update(individualInsertQuery);

            log.info("Updating staff details for individualUuid :: "+individualUuid+" from userUuid :: "+userUuid);
            String attendanceRegisterQuery = "UPDATE eg_wms_attendance_staff set individual_id='"+individualUuid+"' where individual_id='"+userUuid+"';";
            jdbcTemplate.update(attendanceRegisterQuery);

            log.info("Setting individualUuid in org_contact_details table :: "+individualUuid);
            String organisationInsertQuery = "UPDATE eg_org_contact_detail set individual_id='"+individualUuid+"' where contact_mobile_number='"+contact_mobile_number+"';";
            jdbcTemplate.update(organisationInsertQuery);
        }
        log.info("Ending migration....");
    }
    private Role populateRole(Map<String, Object> userDetail)  {
        String code = (String) userDetail.get("role_code");
        if (code == null) {
            return null;
        }
        return Role.builder()
                .tenantId((String) userDetail.get("role_tenantid"))
                .name("Organization admin")
                .code(code)
                .build();
    }
    private Map<String, String> encryptionDecryptionUtil(Map<String, String> encryptionDecryptionMap, Boolean isEncryption) {
        JsonNode request = null;
        StringBuilder uri = new StringBuilder();
        Map<String,Object> encryptionRequestMap = new HashMap<>();
        Map<String, List<Object>> requestMap = new HashMap<>();

        if (isEncryption == true){
            log.info("Encrypting mobile number");
            uri.append(config.getEncryptionHost()).append(config.getEncryptionEndpoint());
            Map<String,String> valueMap = new HashMap<>();
            valueMap.put("contact_mobile_number",encryptionDecryptionMap.get("contact_mobile_number"));
            encryptionRequestMap.put("tenantId",encryptionDecryptionMap.get("tenant_id"));
            encryptionRequestMap.put("type","Normal");
            encryptionRequestMap.put("value",valueMap);

            requestMap.put("encryptionRequests",Collections.singletonList(encryptionRequestMap));

        }else {
            log.info("Decrypting Encrypted values");
            uri.append(config.getEncryptionHost()).append(config.getDecryptionEndpoint());
            request = mapper.createObjectNode()
                    .put("mobilenumber",encryptionDecryptionMap.get("mobilenumber"))
                    .put("name",encryptionDecryptionMap.get("name"))
                    .put("emailid",encryptionDecryptionMap.get("emailid"))
                    .put("username",encryptionDecryptionMap.get("username"));
            requestMap = mapper.convertValue(request,Map.class);
        }
        Object response = fetchResult(uri, requestMap);
        log.info("Got response from encryption service");
        JsonNode responseMap = mapper.valueToTree(response);
        JsonNode encryptedOrDecryptedValue = null;
        if (responseMap.isArray()) {
            encryptedOrDecryptedValue = responseMap.get(0);
        }else {
            encryptedOrDecryptedValue = responseMap;
        }
        Map<String,String> encryptedOrDecryptedMap = mapper.convertValue(encryptedOrDecryptedValue, Map.class);
        log.info("Successfully mapped encryption service");
        return encryptedOrDecryptedMap;
    }
    public Object fetchResult(StringBuilder uri, Object request) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Object response = null;
        try {
            response = restTemplate.postForObject(uri.toString(), request, Object.class);
        } catch (HttpClientErrorException e) {
            log.error("External Service threw an Exception: ", e);
            throw new ServiceCallException(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Exception while fetching from searcher: ", e);
        }
        return response;
    }

    public PGobject getPGObject(Object role) {
        String value = null;
        try {
            value = mapper.writeValueAsString(role);
        } catch (JsonProcessingException e) {
            throw new CustomException();
        }

        PGobject json = new PGobject();
        json.setType("jsonb");
        try {
            json.setValue(value);
        } catch (SQLException e) {
            throw new CustomException("", "");
        }
        return json;
    }

}
