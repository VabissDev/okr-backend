package com.vabiss.okrbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/demo", "/auth/**").permitAll()
                        .requestMatchers("/demo2", "/reset-pwd-email").hasRole("USER")
                        .requestMatchers("/users/**").hasRole("USER")
                        .requestMatchers("/invitation/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/workspaces/**").hasRole("ADMIN") //+
                        .requestMatchers(HttpMethod.POST, "/workspaces").hasRole("LEADER") //+
                        .requestMatchers("/workspaces/**").hasRole("USER") //+
                        .requestMatchers(HttpMethod.GET, "/organizations/**").hasRole("USER") //+
                        .requestMatchers("/organizations/**").hasRole("ADMIN") //+
                        .anyRequest().permitAll()
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
