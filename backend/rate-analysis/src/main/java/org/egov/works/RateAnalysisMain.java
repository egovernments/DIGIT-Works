package org.egov.works;


import org.egov.tracer.config.TracerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({TracerConfiguration.class})
@SpringBootApplication
@ComponentScan(basePackages = {"org.egov.works", "org.egov.works.web.controllers", "org.egov.works.config"})
public class RateAnalysisMain {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(RateAnalysisMain.class, args);
    }

}
