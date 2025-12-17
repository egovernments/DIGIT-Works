package org.egov.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {


    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;
    @Value("${egov.idgen.path}")
    private String idGenPath;
    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;
    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;

    @Value("${egov.mdms.host.v2}")
    private String mdmsHostForSor;

    //    MdmsV2 create endpoint
    @Value("${egov.mdms.v2.create.endpoint}")
    private String mdmsSorEndPoint;

    //    MdmsV2 search endpoint
    @Value("${egov.mdms.v2.search.endpoint}")
    private String mdmsSorSearchEndPoint;

    //    MdmsV2 update endpoint
    @Value("${egov.mdms.v2.update.endpoint}")
    private String mdmsSorUpdateEndPoint;


    //id format
    @Value("${egov.idgen.sor.number.name}")
    private String idgenSorName;
    @Value("${egov.idgen.sor.number.format}")
    private String idgenSorFormat;






}

