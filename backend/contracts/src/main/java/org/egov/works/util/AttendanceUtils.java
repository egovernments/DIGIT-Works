package org.egov.works.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.services.common.models.attendance.AttendanceRegister;
import org.egov.works.services.common.models.attendance.AttendanceRegisterRequest;
import org.egov.works.services.common.models.attendance.AttendanceRegisterResponse;
import org.egov.works.services.common.models.attendance.Status;
import org.egov.works.web.models.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.egov.works.util.ContractServiceConstants.*;

@Component
@Slf4j
public class AttendanceUtils {
    private final ObjectMapper mapper;

    private final ServiceRequestRepository restRepo;

    private final ContractServiceConfiguration configs;

    private final CommonUtil commonUtil;

    @Autowired
    public AttendanceUtils(ObjectMapper mapper, ServiceRequestRepository restRepo, ContractServiceConfiguration configs, CommonUtil commonUtil) {
        this.mapper = mapper;
        this.restRepo = restRepo;
        this.configs = configs;
        this.commonUtil = commonUtil;
    }

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
        String projectName = findValue(additionalDetails,PROJECT_NAME_CONSTANT);
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
        JSONObject registerAdditionalDetails = new JSONObject();
        registerAdditionalDetails.put(CONTRACT_ID_CONSTANT,contract.getContractNumber());
        registerAdditionalDetails.put(ORG_NAME_CONSTANT,findValue(additionalDetails,ORG_NAME_CONSTANT));
        registerAdditionalDetails.put(OFFICER_IN_CHARGE_CONSTANT,findValue(additionalDetails,OFFICER_IN_CHARGE_ID_CONSTANT));
        registerAdditionalDetails.put(WARD_CONSTANT,findValue(additionalDetails,WARD_CONSTANT));
        registerAdditionalDetails.put(PROJECT_ID_CONSTANT,findValue(additionalDetails,PROJECT_ID_CONSTANT));
        registerAdditionalDetails.put(PROJECT_NAME_CONSTANT,findValue(additionalDetails,PROJECT_NAME_CONSTANT));
        registerAdditionalDetails.put(PROJECT_TYPE_CONSTANT,findValue(additionalDetails,PROJECT_TYPE_CONSTANT));
        registerAdditionalDetails.put(MDMS_WORKS_LOCALITY,findValue(additionalDetails,MDMS_WORKS_LOCALITY));
        registerAdditionalDetails.put(MDMS_WORKS_PROJECT_DESC,findValue(additionalDetails,MDMS_WORKS_PROJECT_DESC));
        registerAdditionalDetails.put(EXECUTING_AUTHORITY_CONSTANT,contract.getExecutingAuthority());

        return registerAdditionalDetails;
    }

    private String findValue( Object additionalDetails,String findValueOf){
        Optional<String> valueOptional = commonUtil.findValue(additionalDetails, findValueOf);
        if (valueOptional.isPresent()) {
            return valueOptional.get();
        } else {
            return null;
        }
    }
}
