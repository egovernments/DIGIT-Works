package org.egov.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class IfmsAdapterConfig {

    @Value("${payment.status.kafka.topic}")
    private String paymentStatusTopic;

}
