package org.egov.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.request.User;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.web.models.*;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MusterRollRequestBuilderTest {

    private MusterRollRequest builder;

    private Object mdmsResponse;

    private AttendanceLogResponse attendanceLogResponse;

    public static MusterRollRequestBuilderTest builder(){
        return new MusterRollRequestBuilderTest();
    }

    public MusterRollRequest withMusterForCreateSuccess(){
        MusterRoll musterRoll = MusterRoll.builder().tenantId("pb.amritsar").registerId("95ba2d55-b7a4-41e2-8598-19a83d63c9a4").startDate(new BigDecimal("1670178600000")).endDate(new BigDecimal("1670697000000")).build();
        Workflow workflow = Workflow.builder().action("SUBMIT").build();
        this.builder = MusterRollRequest.builder().musterRoll(musterRoll).requestInfo(getRequestInfo()).workflow(workflow).build();
        return this.builder;
    }

    public MusterRollRequest withMusterForCreateValidationSuccess(){
        MusterRoll musterRoll = MusterRoll.builder().tenantId("pb.amritsar").registerId("95ba2d55-b7a4-41e2-8598-19a83d63c9a4").startDate(new BigDecimal("1670178600000")).endDate(new BigDecimal("1670697000000")).build();
        Workflow workflow = Workflow.builder().action("SUBMIT").build();
        this.builder = MusterRollRequest.builder().musterRoll(musterRoll).requestInfo(getRequestInfo()).workflow(workflow).build();
        return this.builder;
    }

    public MusterRollRequest withMusterForCreateException(){
        MusterRoll musterRoll = MusterRoll.builder().tenantId("pb.amritsar").startDate(new BigDecimal("1669919400000")).endDate(new BigDecimal("1670697000000")).build();
        this.builder = MusterRollRequest.builder().musterRoll(musterRoll).requestInfo(getRequestInfo()).build();
        return this.builder;
    }

    public RequestInfo getRequestInfo(){
        Role role = new Role(1L,"Organization staff","ORG_STAFF","pb.amritsar");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        User userInfo = User.builder().id(172L).uuid("5ce80dd3-b1c0-42fd-b8f6-a2be456db31c").userName("8070102021").name("test3").mobileNumber("8070102021")
                .emailId("xyz@egovernments.org").type("EMPLOYEE").roles(roles).build();
        RequestInfo requestInfo = RequestInfo.builder().apiId("muster-service").msgId("search with from and to values").userInfo(userInfo).build();
        return requestInfo;
    }

    public ResponseInfo getResponseInfo_Success() {
        ResponseInfo responseInfo = ResponseInfo.builder().apiId("muster-roll-service").ver(null).ts(null).resMsgId(null).msgId("search with from and to values")
                .status("successful").build();
        return responseInfo;
    }

    public static Object getMdmsResponse() {

        Object mdmsResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/test/resources/MusterRollMDMSData.json");
            String exampleRequest = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("CalculationServiceTest::getMdmsResponse::Exception while parsing mdms json");
        }
        return  mdmsResponse;
    }

    public static Object getTenantMdmsResponse() {

        Object mdmsResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/test/resources/TenantMDMSData.json");
            String exampleRequest = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            mdmsResponse = objectMapper.readValue(exampleRequest, Object.class);
        } catch (Exception exception) {
            log.error("CalculationServiceTest::getMdmsResponse::Exception while parsing mdms json");
        }
        return  mdmsResponse;
    }

    public static AttendanceLogResponse getAttendanceLogResponse() {
        List<AttendanceLog> logs = new ArrayList<>();
        AttendanceLog attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
                .time(new BigDecimal("1670211000000")).type("ENTRY")
                .build();
        logs.add(attendanceLog);
        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
                .time(new BigDecimal("1670225400000")).type("EXIT")
                .build();
        logs.add(attendanceLog);
        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
                .time(new BigDecimal("1670297400000")).type("ENTRY")
                .build();
        logs.add(attendanceLog);
        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
                .time(new BigDecimal("1670304600000")).type("EXIT")
                .build();
        logs.add(attendanceLog);
        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174445")
                .time(new BigDecimal("1670297400000")).type("ENTRY")
                .build();
        logs.add(attendanceLog);
        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174445")
                .time(new BigDecimal("1670329800000")).type("EXIT")
                .build();
        logs.add(attendanceLog);
        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
                .time(new BigDecimal("1670383800000")).type("ENTRY")
                .build();
        logs.add(attendanceLog);
        attendanceLog = AttendanceLog.builder().individualId("123e4567-e89b-12d3-a456-426614174444")
                .time(new BigDecimal("1670405400000")).type("EXIT")
                .build();
        logs.add(attendanceLog);

        AttendanceLogResponse attendanceLogResponse = AttendanceLogResponse.builder().attendance(logs).build();
        return attendanceLogResponse;
    }

    public static IndividualBulkResponse getIndividualResponse() {
        List<Individual> individuals = new ArrayList<>();
        Individual individual = Individual.builder().id("123e4567-e89b-12d3-a456-426614174444")
                .build();
        individuals.add(individual);

        IndividualBulkResponse response = IndividualBulkResponse.builder().individual(individuals).build();
        return response;
    }

    public static BankAccountResponse getBankDetailsResponse() {
        List<BankAccount> accounts = new ArrayList<>();
        BankAccount bankAccount = BankAccount.builder().referenceId("123e4567-e89b-12d3-a456-426614174444")
                .build();
        accounts.add(bankAccount);

        BankAccountResponse response = BankAccountResponse.builder().bankAccounts(accounts).build();
        return response;
    }

    public static AttendanceRegisterResponse getAttendanceRegisterResponse() {
        List<AttendanceRegister> attendanceRegisterList = new ArrayList<>();
        AttendanceRegister attendanceRegister = AttendanceRegister.builder().id("196dc78f-54eb-4462-a924-f9e753834228").build();
        attendanceRegisterList.add(attendanceRegister);
        AttendanceRegisterResponse response = AttendanceRegisterResponse.builder().attendanceRegister(attendanceRegisterList).build();
        return response;
    }
}
