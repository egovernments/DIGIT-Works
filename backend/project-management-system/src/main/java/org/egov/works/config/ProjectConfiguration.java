package org.egov.works.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.egov.tracer.config.TracerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Data
@Import({TracerConfiguration.class})
@NoArgsConstructor
@AllArgsConstructor
public class ProjectConfiguration {

    @Value("${app.timezone}")
    private String timeZone;
    //MDMS
    @Value("${egov.mdms.host}")
    private String mdmsHost;
    @Value("${egov.mdms.search.endpoint}")
    private String mdmsEndPoint;
    //Topic
    @Value("${project.management.system.kafka.create.topic}")
    private String saveProjectTopic;
    @Value("${project.management.system.kafka.update.topic}")
    private String updateProjectTopic;
    //search config
    @Value("${project.max.offset}")
    private Integer maxOffset;
    @Value("${project.max.limit}")
    private Integer maxLimit;
    //Idgen Config
    @Value("${egov.idgen.host}")
    private String idGenHost;
    @Value("${egov.idgen.path}")
    private String idGenPath;
    //id format name
    @Value("${egov.idgen.project.number.name}")
    private String idgenProjectNumberName;

}
