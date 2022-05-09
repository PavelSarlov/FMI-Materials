package com.fmi.materials.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

// annotation for configuration
@ConfigurationProperties(prefix = "config")
@Configuration
@Getter
public class AppConfig {

    private final LoggerConfig logger = new LoggerConfig();

    @ConfigurationProperties(prefix = "logger")
    @Data
    public class LoggerConfig {
        private String level;
    }
}