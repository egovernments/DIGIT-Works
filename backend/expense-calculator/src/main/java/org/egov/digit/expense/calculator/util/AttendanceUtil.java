package org.egov.digit.expense.calculator.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.expense.calculator.config.ExpenseCalculatorConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.works.services.common.models.attendance.AttendanceRegister;
import org.egov.works.services.common.models.attendance.AttendanceRegisterResponse;
import org.egov.works.services.common.models.musterroll.Status;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@Slf4j
public class AttendanceUtil {

    private final ExpenseCalculatorConfiguration config;
    private final RestTemplate restTemplate;

    public AttendanceUtil(ExpenseCalculatorConfiguration config, RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }


    public List<AttendanceRegister> fetchAttendanceRegister(String referenceId, String tenantId, RequestInfo requestInfo, String localityCode) {
        //TODO fix logs
        log.info("MusterRollValidator::Fetching attendance register with tenantId::" + tenantId
                + " and register ID: " +referenceId);
        String id = requestInfo.getUserInfo().getUuid();

        StringBuilder uri = new StringBuilder();
        uri.append(config.getAttendanceLogHost()).append(config.getAttendanceRegisterEndpoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("tenantId", tenantId).queryParam("referenceId", referenceId)
                .queryParam("localityCode", localityCode)
                .queryParam("status", Status.ACTIVE);
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        AttendanceRegisterResponse attendanceRegisterResponse = null;

        try {
            attendanceRegisterResponse = restTemplate.postForObject(uriBuilder.toUriString(), requestInfoWrapper,
                    AttendanceRegisterResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.error("MusterRollValidator::Error thrown from attendance register service::"
                    + httpClientOrServerExc.getStatusCode());
            throw new CustomException("ATTENDANCE_REGISTER_SERVICE_EXCEPTION",
                    "Error thrown from attendance register service::" + httpClientOrServerExc.getStatusCode());
        }

        if (attendanceRegisterResponse == null
                || CollectionUtils.isEmpty(attendanceRegisterResponse.getAttendanceRegister())) {
            log.error("MusterRollValidator::User with id::" + id + " is not enrolled in the attendance register::"
                    + referenceId);
            throw new CustomException("ACCESS_EXCEPTION",
                    "User is not enrolled in the attendance register and not authorized to fetch it");
        }
        return attendanceRegisterResponse.getAttendanceRegister();
    }
}
