package org.digit.exchange.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;
import lombok.*;


@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String name;
    private String domain;
    private Map<String, String> routes;
}
