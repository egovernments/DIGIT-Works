package org.egov.kafka;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.service.OrganisationContactDetailsStaffUpdateService;
import org.egov.service.StaffService;
import org.egov.web.models.Organisation.OrgContactUpdateDiff;
import org.egov.web.models.StaffPermission;
import org.egov.web.models.StaffPermissionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@Slf4j
public class Consumer {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrganisationContactDetailsStaffUpdateService organisationContactDetailsStaffUpdateService;

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

}
