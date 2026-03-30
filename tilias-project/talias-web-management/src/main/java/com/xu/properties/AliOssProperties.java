package com.xu.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "tilias.aliyun")
@Data
public class AliOssProperties {
    private String endpoint;
    private String bucketName;
    private String region;
}
