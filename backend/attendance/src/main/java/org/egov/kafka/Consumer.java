package org.egov.kafka;



import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.service.AttendanceRegisterService;
import org.egov.service.OrganisationContactDetailsStaffUpdateService;
import org.egov.web.models.AttendanceTimeExtensionRequest;
import org.egov.web.models.Organisation.OrgContactUpdateDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class Consumer {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrganisationContactDetailsStaffUpdateService organisationContactDetailsStaffUpdateService;
    @Autowired
    private AttendanceRegisterService attendanceRegisterService;

    @KafkaListener(topics = "${organisation.contact.details.update.topic}")
    public void updateAttendanceStaff(String consumerRecord,
                                      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
        try {
            OrgContactUpdateDiff orgContactUpdateDiff = objectMapper.readValue(consumerRecord, OrgContactUpdateDiff.class);
            organisationContactDetailsStaffUpdateService.updateStaffPermissionsForContactDetails(orgContactUpdateDiff);
        } catch(Exception e){
            log.error("Error updating staff permissions for update in organisation contact details", e);
        }
    }

    @KafkaListener(topics = "${contracts.revision.topic}")
    public void updateEndDate(String consumerRecord, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            AttendanceTimeExtensionRequest attendanceTimeExtensionRequest = objectMapper.readValue(consumerRecord, AttendanceTimeExtensionRequest.class);
            attendanceRegisterService.updateEndDateForTimeExtension(attendanceTimeExtensionRequest);
        }catch (Exception e) {
            log.error("Error end date for contract");
        }
    }

}
