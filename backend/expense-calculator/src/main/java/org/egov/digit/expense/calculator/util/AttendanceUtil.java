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


    public List<AttendanceRegister> fetchAttendanceRegister(String referenceId, String tenantId, RequestInfo requestInfo,
                                                            String localityCode, boolean isChildrenRequired, Integer offset) {
        log.info("Fetching attendance register with tenantId::" + tenantId
                + " and offset::" + offset + " and batch size:: " +config.getRegisterBatchSize());
        String id = requestInfo.getUserInfo().getUuid();

        StringBuilder uri = new StringBuilder();
        uri.append(config.getAttendanceLogHost()).append(config.getAttendanceRegisterEndpoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("tenantId", tenantId).queryParam("referenceId", referenceId)
                .queryParam("localityCode", localityCode)
                .queryParam("isChildrenRequired", isChildrenRequired)
                .queryParam("status", Status.ACTIVE)
                .queryParam("offset", offset)
                .queryParam("limit", config.getRegisterBatchSize());
        RequestInfoWrapper requestInfoWrapper = RequestInfoWrapper.builder().requestInfo(requestInfo).build();

        AttendanceRegisterResponse attendanceRegisterResponse = null;

        try {
            attendanceRegisterResponse = restTemplate.postForObject(uriBuilder.toUriString(), requestInfoWrapper,
                    AttendanceRegisterResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.error("AttendanceUtil::Error thrown from attendance register service::"
                    + httpClientOrServerExc.getStatusCode());
            throw new CustomException("ATTENDANCE_REGISTER_SERVICE_EXCEPTION",
                    "Error thrown from attendance register service::" + httpClientOrServerExc.getStatusCode());
        }

        if (attendanceRegisterResponse == null || attendanceRegisterResponse.getAttendanceRegister() == null) {
            log.error("Error fetching registers::"
                    + referenceId);
            throw new CustomException("ERROR_FETCHING_ATTENDANCE_REGISTER",
                    "Error fetching attendance registers");
        }
        return attendanceRegisterResponse.getAttendanceRegister();
    }
}
