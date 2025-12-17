package org.egov.works.measurement.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.works.measurement.config.MBServiceConfiguration;
import org.egov.works.measurement.web.models.Measurement;
import org.egov.works.measurement.web.models.MeasurementRequest;
import org.egov.works.services.common.models.contract.ContractCriteria;
import org.egov.works.services.common.models.contract.ContractResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Component
public class MBServiceConsumer {
    private final ObjectMapper mapper;


    private final RestTemplate restTemplate;

    private final MBServiceProducer MBServiceProducer;

    private final MBServiceConfiguration MBServiceConfiguration;

    @Autowired
    public MBServiceConsumer(ObjectMapper mapper, RestTemplate restTemplate, MBServiceProducer MBServiceProducer, MBServiceConfiguration MBServiceConfiguration) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.MBServiceProducer = MBServiceProducer;
        this.MBServiceConfiguration = MBServiceConfiguration;
    }


    @KafkaListener(topics = {"${measurement-service.kafka.create.topic}","${measurement-service.kafka.update.topic}"})
    public void listen(final HashMap<String, Object> record , @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        MeasurementRequest measurementRequest = mapper.convertValue(record,MeasurementRequest.class);

        List<HashMap<String, Object>> accumulatedDataList = new ArrayList<>();
        List<Measurement> measurements = measurementRequest.getMeasurements();
        RequestInfo requestInfo = measurementRequest.getRequestInfo();

        for (Measurement measurement : measurements) {
            String referenceId = measurement.getReferenceId();

            //function to fetch the contract response
            ContractCriteria req = ContractCriteria.builder().requestInfo(requestInfo).tenantId(measurement.getTenantId()).contractNumber(referenceId).build();

            String searchContractUrl = MBServiceConfiguration.getContractHost() + MBServiceConfiguration.getContractPath();

            ContractResponse contractResponse = restTemplate.postForEntity(searchContractUrl, req, ContractResponse.class).getBody(); // inside try Catch
            //merging the contract response with the measurement response
            HashMap<String, Object> mergedData = new HashMap<>();
            mergedData.put("contract", contractResponse.getContracts().get(0));
            mergedData.put("measurement", measurement);

            accumulatedDataList.add(mergedData);

        }

        //pushing into new topic after enriching
        MBServiceProducer.push(MBServiceConfiguration.getEnrichMeasurementTopic(), accumulatedDataList);
    }
}
