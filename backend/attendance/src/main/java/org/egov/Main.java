package org.egov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({ org.egov.tracer.config.TracerConfiguration.class ,
        org.egov.common.utils.MultiStateInstanceUtil.class})
@SpringBootApplication
@ComponentScan(basePackages = {"org.egov", "org.egov.web.controllers", "org.egov.config"})
public class Main {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

}
