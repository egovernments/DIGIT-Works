package org.egov.works.measurement;


import org.egov.tracer.config.TracerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({TracerConfiguration.class})
@SpringBootApplication
@ComponentScan(basePackages = {"org.egov.works.measurement", "org.egov.works.measurement.web.controllers", "org.egov.works.measurement.config"})
public class MBServiceApplication {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(MBServiceApplication.class, args);
    }

}
