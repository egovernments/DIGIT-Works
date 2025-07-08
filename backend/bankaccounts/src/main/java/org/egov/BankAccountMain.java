package org.egov;


import org.egov.tracer.config.TracerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({TracerConfiguration.class})
@SpringBootApplication
@ComponentScan(basePackages = {"org.egov", "org.egov.web.controllers", "org.egov.config"})
public class BankAccountMain {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(BankAccountMain.class, args);
    }

}
