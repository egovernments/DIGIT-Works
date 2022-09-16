package org.egov.works.config;

import lombok.*;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Import({TracerConfiguration.class})
@Component
public class LOIConfiguration {


    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;

    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    @Value("${loi.default.limit}")
    private Integer defaultLimit;

    @Value("${loi.default.offset}")
    private Integer defaultOffset;

    @Value("${loi.search.max.limit}")
    private Integer maxLimit;
}