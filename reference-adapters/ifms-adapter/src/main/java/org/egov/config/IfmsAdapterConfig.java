package org.egov.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Configuration
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class IfmsAdapterConfig {

    @Value("${payment.status.kafka.topic}")
    private String paymentStatusTopic;

    @Value("${ifms.jit.hostname}")
    private String ifmsJitHostName;

    @Value("${ifms.jit.authenticate.endpoint}")
    private String ifmsJitAuthEndpoint;

    @Value("${ifms.jit.request.data.endpoint}")
    private String ifmsJitRequestEndpoint;

    @Value("${ifms.jit.client.id}")
    private String ifmsJitClientId;

    @Value("${ifms.jit.client.secret}")
    private String ifmsJitClientSecret;

    @Value("${ifms.jit.public.key.path}")
    private String ifmsJitPublicKeyPath;

    @Value("${ifms.jit.public.key.file}")
    private String ifmsJitPublicKeyFile;

}
