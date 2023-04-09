package com.justcode.identityservice.config;

import com.justcode.identityservice.service.UserInfoUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
       // public UserDetailsService userDetailsService(){
      /*  UserDetails admin = User.withUsername("arun")
                .password(encoder.encode("arun"))
                .roles("ADMIN").build();
        UserDetails user = User.withUsername("varun")
                .password(encoder.encode("varun"))
                .roles("USER").build(); //role  should be in uppercase otherwise error
        return new InMemoryUserDetailsManager(admin,user);
   */
    //authentication by fetching from db
         return new UserInfoUserDetailsService();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     return   http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/products/welcome","/products/new").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/products/**")
                .authenticated()
                .and()
                .formLogin()
                .and()
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
