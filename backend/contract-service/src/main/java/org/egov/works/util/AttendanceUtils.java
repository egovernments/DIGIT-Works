package org.egov.works.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.egov.works.util.ContractServiceConstants.*;

@Component
@Slf4j
public class AttendanceUtils {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private ContractServiceConfiguration configs;

    @Autowired
    private CommonUtil commonUtil;

    public String createAttendanceRegister(ContractRequest contractRequest) {
        AttendanceRegisterRequest attendanceRegisterRequest = getRegisterRequest(contractRequest);
        StringBuilder url = getRegisterRequestURL();
        final Object responseObj = restRepo.fetchResult(url, attendanceRegisterRequest);
        AttendanceRegisterResponse response = mapper.convertValue(responseObj, AttendanceRegisterResponse.class);
        final List<AttendanceRegister> attendanceRegister = response.getAttendanceRegister();
        if (CollectionUtils.isEmpty(attendanceRegister)) {
            throw new CustomException("ATTENDANCE_REGISTER_ERROR", "No register created");
        }
        return attendanceRegister.get(0).getRegisterNumber();
    }

    private StringBuilder getRegisterRequestURL() {
        return new StringBuilder(configs.getAttendanceHost()).append(configs.getAttendanceRegisterPath());

    }

    private AttendanceRegisterRequest getRegisterRequest(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        RequestInfo requestInfo = contractRequest.getRequestInfo();
        String tenantId = contract.getTenantId();
        BigDecimal startDate = contract.getStartDate();
        BigDecimal endDate = contract.getEndDate();
        Object additionalDetails = contract.getAdditionalDetails();
        String projectName = commonUtil.findValue(additionalDetails, PROJECT_NAME_CONSTANT).get();
        JSONObject registerAdditionalDetails = getRegisterAdditionalDetails(contractRequest);

        AttendanceRegister registerToCreate = null;
        try {
            registerToCreate = AttendanceRegister.builder()
                    .tenantId(tenantId)
                    .startDate(startDate)
                    .endDate(endDate)
                    .status(Status.ACTIVE)
                    .name(projectName)
                    .referenceId(contract.getContractNumber())
                    .serviceCode(configs.getServiceCode())
                    .additionalDetails(mapper.readValue(registerAdditionalDetails.toString(), Object.class))
                    .build();
        } catch (JsonProcessingException e) {
            throw new CustomException("JSON_PROCESSING_ERROR","Failed to set additionalDetail object in register");
        }

        return AttendanceRegisterRequest.builder()
                .attendanceRegister(Collections.singletonList(registerToCreate))
                .requestInfo(requestInfo)
                .build();
    }

    private JSONObject getRegisterAdditionalDetails(ContractRequest contractRequest) {
        Contract contract = contractRequest.getContract();
        Object additionalDetails = contract.getAdditionalDetails();
        String ward = commonUtil.findValue(additionalDetails, WARD_CONSTANT).get();
        String orgName = commonUtil.findValue(additionalDetails, ORG_NAME_CONSTANT).get();
        String projectId = commonUtil.findValue(additionalDetails, PROJECT_ID_CONSTANT).get();
        String projectName = commonUtil.findValue(additionalDetails, PROJECT_NAME_CONSTANT).get();
        String projectType = commonUtil.findValue(additionalDetails, PROJECT_TYPE_CONSTANT).get();
        String officerInCharge = commonUtil.findValue(additionalDetails, OFFICER_IN_CHARGE_ID_CONSTANT).get();
        String locality = commonUtil.findValue(additionalDetails, MDMS_WORKS_LOCALITY).get();
        String projectDesc = commonUtil.findValue(additionalDetails, MDMS_WORKS_PROJECT_DESC).get();

        JSONObject registerAdditionalDetails = new JSONObject();
        registerAdditionalDetails.put("contractId",contract.getContractNumber());
        registerAdditionalDetails.put("orgName",orgName);
        registerAdditionalDetails.put("officerInCharge",officerInCharge);
        registerAdditionalDetails.put("ward",ward);
        registerAdditionalDetails.put("projectId",projectId);
        registerAdditionalDetails.put("projectName",projectName);
        registerAdditionalDetails.put("projectType",projectType);
        registerAdditionalDetails.put("locality",locality);
        registerAdditionalDetails.put("projectDesc",projectDesc);

        return registerAdditionalDetails;
    }

}