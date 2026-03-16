package com.xu;

import com.xu.properties.AliOssProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties(AliOssProperties.class)
public class TaliasWebManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaliasWebManagementApplication.class, args);
    }

}
