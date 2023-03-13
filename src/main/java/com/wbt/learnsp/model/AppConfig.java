package com.wbt.learnsp.model;

import com.wbt.learnsp.user.UserAccount;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.config")
public record AppConfig(String header, String intro, List<UserAccount> accounts) {
}
