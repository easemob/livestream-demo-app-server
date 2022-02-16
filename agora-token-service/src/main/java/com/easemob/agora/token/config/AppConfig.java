package com.easemob.agora.token.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "easemob.agora.cdn")
public class AppConfig {

    private String appId;

    private String appCert;
}
