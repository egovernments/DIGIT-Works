package org.egov.web.notification.sms.consumer.contract;

import org.springframework.kafka.support.serializer.JsonDeserializer;

public class WorksSmsRequestDeserializer extends JsonDeserializer<WorksSMSRequest> {
    public WorksSmsRequestDeserializer() {
        super(WorksSMSRequest.class);
    }
}
