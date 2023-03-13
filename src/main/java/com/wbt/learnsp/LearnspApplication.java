package com.wbt.learnsp;

import com.wbt.learnsp.model.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {AppConfig.class})
public class LearnspApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnspApplication.class, args);
    }

}
