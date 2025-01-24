package org.egov.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
public class MusterRollServiceConfiguration {

    @Value("${app.timezone}")
    private String timeZone;

    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;
    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;
    @Value("${egov.mdms.v2.host}")
    private String mdmsV2Host;
    @Value("${egov.mdms.v2.search.endpoint}")
    private String mdmsV2EndPoint;

    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;
    @Value("${egov.idgen.path}")
    private String idGenPath;

    //Idgen name
    @Value("${egov.idgen.musterroll.number.name}")
    private String idgenMusterRollNumberName;

    //Workflow config
    @Value("${musterroll.workflow.module.name}")
    private String musterRollWFModuleName;
    @Value("${musterroll.workflow.business.service}")
    private String musterRollWFBusinessService;
    @Value("${egov.workflow.host}")
    private String wfHost;
    @Value("${egov.workflow.transition.path}")
    private String wfTransitionPath;
    @Value("${egov.workflow.businessservice.search.path}")
    private String wfBusinessServiceSearchPath;
    @Value("${egov.workflow.processinstance.search.path}")
    private String wfProcessInstanceSearchPath;

    //Topic
    @Value("${musterroll.kafka.create.topic}")
    private String saveMusterRollTopic;
    @Value("${musterroll.kafka.update.topic}")
    private String updateMusterRollTopic;
    @Value("${musterroll.kafka.calculate.topic}")
    private String calculateMusterRollTopic;

    //search config
    @Value("${musterroll.default.offset}")
    private Integer musterDefaultOffset;
    @Value("${musterroll.default.limit}")
    private Integer musterDefaultLimit;
    @Value("${musterroll.search.max.limit}")
    private Integer musterMaxLimit;
    @Value("${muster.restricted.search.roles}")
    private String restrictedSearchRoles;

    //Attendance service
    @Value("${works.attendance.log.host}")
    private String attendanceLogHost;
    @Value("${works.attendance.log.search.endpoint}")
    private String attendanceLogEndpoint;
    @Value("${works.attendance.register.search.endpoint}")
    private String attendanceRegisterEndpoint;
    @Value("${works.attendance.register.update.endpoint}")
    private String attendanceRegisterUpdateEndpoint;
    @Value("${works.attendance.register.search.limit}")
    private String attendanceRegisterSearchLimit;

    //Contract Service
    @Value("${works.contract.host}")
    private String contractServiceHost;
    @Value("${works.contract.endpoint}")
    private String contractServiceEndpoint;

    //Organisation Service
    @Value("${works.organisation.host}")
    private String organisationServiceHost;
    @Value("${works.organisation.endpoint}")
    private String organisationServiceEndpoint;

    //Localization Service
    @Value("${egov.localization.host}")
    private String localizationServiceHost;
    @Value("${egov.localization.search.endpoint}")
    private String localizationServiceEndpoint;

    //Notification Topic
    @Value("${kafka.topics.notification.sms}")
    private String smsNotificationTopic;

    @Value("${notification.sms.enabled:false}")
    private boolean sendNotificationEnabled;

    //Expense Service
    @Value("${works.expense.calculator.host}")
    private String expenseCalculatorServiceHost;
    @Value("${works.expense.calculator.endpoint}")
    private String expenseCalculatorServiceEndpoint;

    //Individual service
    @Value("${works.individual.host}")
    private String individualHost;
    @Value("${works.individual.search.endpoint}")
    private String individualSearchEndpoint;

    //Bankaccounts service
    @Value("${works.bankaccounts.host}")
    private String bankaccountsHost;
    @Value("${works.bankaccounts.search.endpoint}")
    private String bankaccountsSearchEndpoint;

    //contract service code
    @Value("${works.contract.service.code}")
    private String contractServiceCode;

    @Value("${musterroll.update.recompute.attendance.enabled:true}")
    private boolean recomputeAttendanceEnabled;

    @Value("${musterroll.workflow.enabled:true}")
    private boolean musterRollWorkflowEnabled;

    @Value("${musterroll.noworkflow.create.status}")
    private String musterRollNoWorkflowCreateStatus;

    @Value("${musterroll.individual.entry.roles.enabled:false}")
    private boolean individualEntryRolesEnabled;

    @Value("${musterroll.validate.start.date.monday.enabled:true}")
    private boolean validateStartDateMondayEnabled;

    @Value("${musterroll.validate.attendance.register.enabled:false}")
    private boolean validateAttendanceRegisterEnabled;

    @Value("${musterroll.set.default.duration.enabled:false}")
    private boolean musterRollSetDefaultDurationEnabled;

    @Value("${musterroll.default.duration.days:6}")
    private int musterRollDefaultDuration;

    @Value("${musterroll.add.bank.account.details.enabled:true}")
    private boolean addBankAccountDetails;

    @Value("${musterroll.update.attendance.register.review.status.enabled:true}")
    private boolean updateAttendanceRegisterReviewStatusEnabled;

    @Value("${egov.user.host}")
    private String userHost;

    @Value("${egov.user.context.path}")
    private String userContextPath;

    @Value("${egov.user.create.path}")
    private String userCreateEndpoint;

    @Value("${egov.user.search.path}")
    private String userSearchEndpoint;

    @Value("${egov.user.update.path}")
    private String userUpdateEndpoint;

    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

}
