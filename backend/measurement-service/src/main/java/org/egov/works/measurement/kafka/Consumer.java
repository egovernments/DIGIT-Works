package org.egov.works.measurement.kafka;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.measurement.config.Configuration;
import org.egov.works.measurement.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Component
public class Consumer {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Producer producer;
    @Autowired
    private Configuration configuration;

//    @Autowired
//    private EstimateServiceConfiguration serviceConfiguration;

    /*
     * Uncomment the below line to start consuming record from kafka.topics.consumer
     * Value of the variable kafka.topics.consumer should be overwritten in application.properties
     */
    //@KafkaListener(topics = {"kafka.topics.consumer"})
    @KafkaListener(topics = {"${measurement.kafka.create.topic}","${measurement.kafka.update.topic}","${measurement.kafka.enrich.create.topic}"})
    public void listen(final HashMap<String, Object> record , @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws IOException {
        MeasurementRequest measurementRequest = mapper.convertValue(record,MeasurementRequest.class);
//        System.out.println(topic+"______________________");
        List<HashMap<String, Object>> accumulatedDataList = new ArrayList<>();
        List<Measurement> measurements = measurementRequest.getMeasurements();
        RequestInfo requestInfo = measurementRequest.getRequestInfo();

        for (Measurement measurement : measurements) {
            String referenceId = measurement.getReferenceId();
            ContractCriteria req = ContractCriteria.builder().requestInfo(requestInfo).tenantId(measurementRequest.getMeasurements().get(0).getTenantId()).contractNumber(referenceId).build();
            String searchContractUrl = "https://works-qa.digit.org/contract/v1/_search";

            ContractResponse contractResponse = restTemplate.postForEntity(searchContractUrl, req, ContractResponse.class).getBody();
            HashMap<String, Object> mergedData = new HashMap<>();
            mergedData.put("measurement", measurement);
            mergedData.put("contract", contractResponse);
            accumulatedDataList.add(mergedData);

            System.out.println(mergedData);

        }
        producer.push(configuration.getEnrichMeasurementTopic(), accumulatedDataList);


        //TODO
    }

}
