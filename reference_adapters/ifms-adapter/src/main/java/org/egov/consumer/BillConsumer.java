package org.egov.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.models.BillDemandResponse;
import org.egov.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class BillConsumer {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BillService billService;

    @KafkaListener(topics = {"${bill.kafka.topic}"})
    public void listen(HashMap<String, Object> record) {
        BillDemandResponse billDemandResponse = objectMapper.convertValue(record, BillDemandResponse.class);
        billService.processBillDemandRequest(billDemandResponse);
    }

}
