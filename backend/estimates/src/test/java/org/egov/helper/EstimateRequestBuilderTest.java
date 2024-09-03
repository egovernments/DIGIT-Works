package org.egov.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.models.Workflow;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.request.User;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.services.common.models.common.Address;
import org.egov.web.models.*;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.egov.common.models.project.AddressType.CORRESPONDENCE;

@Slf4j
public class EstimateRequestBuilderTest {

    private EstimateRequest estimateRequest;

    private Object mdmsResponse;

    public static EstimateRequestBuilderTest builder() {
        return new EstimateRequestBuilderTest();
    }

    public EstimateRequest withEstimateForCreateSuccess() {
        List<AmountDetail> amountDetails = new ArrayList<>();
        AmountDetail amountDetail = AmountDetail.builder().type("GST").amount(2345.0).build();
        amountDetails.add(amountDetail);

        List<EstimateDetail> estimateDetails = new ArrayList<>();
        EstimateDetail estimateDetail = EstimateDetail.builder()
                .sorId("sor id")
                .category("OVERHEAD").name("GST")
                .amountDetail(amountDetails).build();
        EstimateDetail estimateDetail1 = EstimateDetail.builder()
                .sorId("sor id")
                .category("SOR").name("xx").uom("KG").unitRate(100.0)
                .amountDetail(amountDetails).build();
        estimateDetails.add(estimateDetail);
        estimateDetails.add(estimateDetail1);

        Address address = Address.builder().city("Bangalore").pincode("560108").tenantId("pb.amritsar").build();

        Estimate estimate = Estimate.builder().tenantId("pb.amritsar")
                .projectId("95ba2d55-b7a4-41e2-8598-19a83d63c9a4")
                .name("School Construction")
                .estimateDetails(estimateDetails)
                .executingDepartment("DEPT_1")
                .status(Estimate.StatusEnum.ACTIVE)
                .address(address).build();

        List<String> assignees = new ArrayList<>();
        assignees.add("88ba2d55-b7a4-41e2-8598-19a83d63c9a9");
        Workflow workflow = Workflow.builder().action("CREATE").assignes(assignees).build();

        this.estimateRequest = EstimateRequest.builder()
                .requestInfo(getRequestInfo())
                .estimate(estimate)
                .workflow(workflow).build();
        return this.estimateRequest;
    }

    public EstimateRequest withEstimateForCreateExceptionForProjectId() {
        List<AmountDetail> amountDetails = new ArrayList<>();
        AmountDetail amountDetail = AmountDetail.builder().amount(2345.0).build();
        amountDetails.add(amountDetail);

        List<EstimateDetail> estimateDetails = new ArrayList<>();
        EstimateDetail estimateDetail = EstimateDetail.builder()
                .sorId("sor id")
                .amountDetail(amountDetails).build();
        estimateDetails.add(estimateDetail);

        Address address = Address.builder().city("Bangalore").pincode("560108").tenantId("pb.amritsar").build();

        Estimate estimate = Estimate.builder().tenantId("pb.amritsar")
                .name("School Construction")
                .estimateDetails(estimateDetails)
                .executingDepartment("DEPT_1")
                .status(Estimate.StatusEnum.ACTIVE)
                .address(address).build();

        List<String> assignees = new ArrayList<>();
        assignees.add("88ba2d55-b7a4-41e2-8598-19a83d63c9a9");
        Workflow workflow = Workflow.builder().action("CREATE").assignes(assignees).build();

        this.estimateRequest = EstimateRequest.builder()
                .requestInfo(getRequestInfo())
                .estimate(estimate)
                .workflow(workflow).build();
        return this.estimateRequest;
    }

    public EstimateRequest withEstimateForCreateExceptionForAmountDetail() {
        List<EstimateDetail> estimateDetails = new ArrayList<>();
        EstimateDetail estimateDetail = EstimateDetail.builder()
                .sorId("sor id")
                .build();
        estimateDetails.add(estimateDetail);

        Address address = Address.builder().city("Bangalore").pincode("560108").tenantId("pb.amritsar").build();

        Estimate estimate = Estimate.builder().tenantId("pb.amritsar")
                .projectId("95ba2d55-b7a4-41e2-8598-19a83d63c9a4")
                .name("School Construction")
                .estimateDetails(estimateDetails)
                .executingDepartment("DEPT_1")
                .status(Estimate.StatusEnum.ACTIVE)
                .address(address).build();

        List<String> assignees = new ArrayList<>();
        assignees.add("88ba2d55-b7a4-41e2-8598-19a83d63c9a9");
        Workflow workflow = Workflow.builder().action("CREATE").assignes(assignees).build();

        this.estimateRequest = EstimateRequest.builder()
                .requestInfo(getRequestInfo())
                .estimate(estimate)
                .workflow(workflow).build();
        return this.estimateRequest;
    }

    public RequestInfo getRequestInfo() {
        Role role = new Role(1L, "Estimate creator", "EST_CREATOR", "pb.amritsar");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        User userInfo = User.builder().id(172L).uuid("5ce80dd3-b1c0-42fd-b8f6-a2be456db31c").userName("8070102021").name("test3").mobileNumber("8070102021")
                .emailId("xyz@egovernments.org").type("EMPLOYEE").roles(roles).build();
        RequestInfo requestInfo = RequestInfo.builder().apiId("estimate-service").msgId("search with from and to values").userInfo(userInfo).build();
        return requestInfo;
    }

    public ResponseInfo getResponseInfo_Success() {
        ResponseInfo responseInfo = ResponseInfo.builder().apiId("estimate-service").ver(null).ts(null).resMsgId(null).msgId("search with from and to values")
                .status("successful").build();
        return responseInfo;
    }

    public static Object getMdmsResponse() {
        Object mdmsResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            ClassPathResource resource = new ClassPathResource("src/test/resources/EstimateMDMSData.json");
            String absoluteFilePath = resource.getPath();
            byte[] bytes = Files.readAllBytes(Paths.get(absoluteFilePath));
            String exampleRequest = new String(bytes, StandardCharsets.UTF_8);
            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("EstimateServiceTest::getMdmsResponse::Exception while parsing mdms json");
        }
        return mdmsResponse;
    }

    public static Object getProjectSearchResponse() {

        Object projectResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("src/test/resources/projectSearch.json");
            String absoluteFilePath = resource.getPath();
            byte[] bytes = Files.readAllBytes(Paths.get(absoluteFilePath));
            String exampleRequest = new String(bytes, StandardCharsets.UTF_8);
            projectResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("EstimateServiceTest::getProjectSearchResponse::Exception while parsing project search response json");
        }
        return projectResponse;
    }

//    public static AttendanceLogResponse getAttendanceLogResponse() {
//        List<AttendanceLog> logs = new ArrayList<>();
//        AttendanceLog attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
//                .time(new BigDecimal("1670211000000")).type("ENTRY")
//                .build();
//        logs.add(attendanceLog);
//        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
//                .time(new BigDecimal("1670225400000")).type("EXIT")
//                .build();
//        logs.add(attendanceLog);
//        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
//                .time(new BigDecimal("1670297400000")).type("ENTRY")
//                .build();
//        logs.add(attendanceLog);
//        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
//                .time(new BigDecimal("1670304600000")).type("EXIT")
//                .build();
//        logs.add(attendanceLog);
//        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174445")
//                .time(new BigDecimal("1670297400000")).type("ENTRY")
//                .build();
//        logs.add(attendanceLog);
//        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174445")
//                .time(new BigDecimal("1670329800000")).type("EXIT")
//                .build();
//        logs.add(attendanceLog);
//        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
//                .time(new BigDecimal("1670383800000")).type("ENTRY")
//                .build();
//        logs.add(attendanceLog);
//        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
//                .time(new BigDecimal("1670405400000")).type("EXIT")
//                .build();
//        logs.add(attendanceLog);
//
//        AttendanceLogResponse attendanceLogResponse = AttendanceLogResponse.builder().attendance(logs).build();
//        return attendanceLogResponse;
//    }
}
