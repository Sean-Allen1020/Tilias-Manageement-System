package com.xu.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "tilias.jwt")
@Data
@Component
public class JwtProperties {
    
    private String secretKey;
    private Long ttl;
}
