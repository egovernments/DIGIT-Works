package org.digit.exchange.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.digit.exchange.service.ExchangeService;
import org.digit.exchange.web.models.RequestMessageWrapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExchangeConsumer {

    private ExchangeService exchangeService;
    private ObjectMapper objectMapper;

    public ExchangeConsumer(ExchangeService exchangeService, ObjectMapper objectMapper) {
        this.exchangeService = exchangeService;
        this.objectMapper = objectMapper;

    }

    @KafkaListener(topics = {"${digit.exchange.topic}"})
    public void listen(final String receivedObject,  @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        // write business logic after consuming from kafka topic

        RequestMessageWrapper requestMessageWrapper = null;
//        restTemplate.postForObject("{{digit.exchange.url}}", null, String.class);
        try {
            requestMessageWrapper = objectMapper.readValue(receivedObject, RequestMessageWrapper.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        exchangeService.send(requestMessageWrapper);
    }

}
