package com.project3.quanlynhahang.config;

import com.project3.quanlynhahang.utils.WebPath;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig{

    private final CustomUserDetailsService customUserDetailsService;

    private static final String[] PERMIT_ALL_URLS = new String[] {
            WebPath.CLIENT.API_CLIENT_BASE_PATH + "/auth/**",

            WebPath.ADMIN.API_ADMIN_BASE_PATH + "/auth/**",
    };

    private static final String[] EXPLICIT_AUTH_URLS = new String[]{
            WebPath.CLIENT.API_CLIENT_BASE_PATH + "/update-info",

            WebPath.ADMIN.API_ADMIN_BASE_PATH + "/create-account",
            WebPath.ADMIN.API_ADMIN_MENU_PATH + "/**",
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(EXPLICIT_AUTH_URLS)
//                    .authenticated()
                    .permitAll()
                    .requestMatchers(PERMIT_ALL_URLS)
                    .permitAll()
                    .anyRequest()
                    .permitAll())
            .authenticationProvider(authenticationProvider());
        return http.build();
    }

}
