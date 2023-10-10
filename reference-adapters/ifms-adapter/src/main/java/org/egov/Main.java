package org.egov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Main {

    public static void main(String args[]) {
        SpringApplication.run(Main.class, args);

        String cacertsPath = System.getProperty("javax.net.ssl.trustStore");
        if (cacertsPath == null) {
            // If "javax.net.ssl.trustStore" property is not set, try to find the default cacerts file
            cacertsPath = System.getProperty("java.home") + "/lib/security/cacerts";
        }
        log.info("JVM cacerts is located at: " + cacertsPath);
        System.out.println("JVM cacerts is located at: " + cacertsPath);
    }

}
