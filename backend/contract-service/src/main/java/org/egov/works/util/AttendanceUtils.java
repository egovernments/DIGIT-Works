package org.egov.works.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.works.config.ContractServiceConfiguration;
import org.egov.works.repository.ServiceRequestRepository;
import org.egov.works.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class AttendanceUtils {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ServiceRequestRepository restRepo;

    @Autowired
    private ContractServiceConfiguration configs;

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
        String contractNumber = contract.getContractNumber();
        String registerName = "Reg-"+contractNumber;
        AttendanceRegister registerToCreate = AttendanceRegister.builder()
                .tenantId(tenantId)
                .startDate(startDate)
                .endDate(endDate)
                .status(Status.ACTIVE)
                .name(registerName)
                .build();

        return AttendanceRegisterRequest.builder()
                .attendanceRegister(Collections.singletonList(registerToCreate))
                .requestInfo(requestInfo)
                .build();
    }
}