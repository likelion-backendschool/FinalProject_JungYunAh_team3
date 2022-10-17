package com.example.app.config;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors().disable()
        .csrf().disable()
        .httpBasic().disable()
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/css/**","/js/**").permitAll()
        .antMatchers("/", "/member/join").permitAll()
        .anyRequest().hasAnyRole("MEMBER", "ADMIN");

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
