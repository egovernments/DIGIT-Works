package org.egov.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.service.StaffService;
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
    private StaffService staffService;

    @KafkaListener(topics = "${organisation.contact.detail.update}")
    public void updateAttendanceStaff(Map<String, String> consumerRecord,
                                      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){

        try {
            if(consumerRecord.get("operation").equalsIgnoreCase("ADD")) {
                StaffPermission staffPermissionCreate = StaffPermission.builder().userId(consumerRecord.get("newIndividualId")).build();
                StaffPermissionRequest staffPermissionCreateRequest = StaffPermissionRequest.builder()
                        .staff(Collections.singletonList(staffPermissionCreate)).build();
                staffService.createAttendanceStaff(staffPermissionCreateRequest);
                StaffPermission staffPermissionRemove = StaffPermission.builder().userId(consumerRecord.get("oldIndividualId")).build();
                StaffPermissionRequest staffPermissionRemoveRequest = StaffPermissionRequest.builder()
                        .staff(Collections.singletonList(staffPermissionRemove)).build();
                staffService.deleteAttendanceStaff(staffPermissionRemoveRequest);
            }else{
                StaffPermission staffPermissionRemove = StaffPermission.builder().userId(consumerRecord.get("oldIndividualId")).build();
                StaffPermissionRequest staffPermissionRemoveRequest = StaffPermissionRequest.builder()
                        .staff(Collections.singletonList(staffPermissionRemove)).build();
                staffService.deleteAttendanceStaff(staffPermissionRemoveRequest);
            }

        }catch (Exception e){
            log.error("error in update staff",e);
        }
    }

}
