package com.xu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TaliasWebManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaliasWebManagementApplication.class, args);
    }

}
