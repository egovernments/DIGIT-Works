package org.egov.works.mukta.adapter;


import org.egov.tracer.config.TracerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({TracerConfiguration.class})
@SpringBootApplication
@ComponentScan(basePackages = {"org.egov.works.mukta.adapter", "org.egov.works.mukta.adapter.web.controllers", "org.egov.works.mukta.adapter.config"})
public class MuktaIFixAdapterMain {
    public static void main(String[] args) {
        SpringApplication.run(MuktaIFixAdapterMain.class, args);
    }

}
