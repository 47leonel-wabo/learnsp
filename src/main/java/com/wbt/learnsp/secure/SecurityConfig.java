package com.wbt.learnsp.secure;

import com.wbt.learnsp.user.UserAccount;
import com.wbt.learnsp.user.UserAccountRepository;
import com.wbt.learnsp.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetails(final UserRepository repository) {
        return username -> repository.findByUsername(username).asUser();
    }

    @Bean
    CommandLineRunner initUsers(final UserAccountRepository userRepository) {
        return args -> {
            userRepository.save(new UserAccount("leno-util", "leno-pass", List.of(new SimpleGrantedAuthority("ROLE_USER"))));
            userRepository.save(new UserAccount("lena-util", "lena-pass", List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/", "/search").authenticated()
                .mvcMatchers(HttpMethod.GET, "/api/**").authenticated()
                .mvcMatchers(HttpMethod.POST, "/new-video", "/api/**").hasRole("ADMIN")
                .anyRequest().denyAll();
        httpSecurity.formLogin();
        httpSecurity.httpBasic();
        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }

}
