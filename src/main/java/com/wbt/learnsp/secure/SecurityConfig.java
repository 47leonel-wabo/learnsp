package com.wbt.learnsp.secure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(
                User.withDefaultPasswordEncoder()
                        .username("leon")
                        .password("leon")
                        .roles("USER")
                        .build()
        );
        userDetailsManager.createUser(
                User.withDefaultPasswordEncoder()
                        .username("leona")
                        .password("leona")
                        .roles("ADMIN")
                        .build()
        );

        return userDetailsManager;
    }

}
