package com.AuthService.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.AuthService.Repository.UserRepo;
import com.AuthService.Service.JWTServcie;
import com.AuthService.Service.UserDetailsServiceImpl;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig 
{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JWTServcie jwtServcie;

    @Bean
    public JWTAuthFilter jwtAuthFilter(JWTServcie jwtServcie,UserDetailsServiceImpl userDetailsServiceImpl)
    {
        return new JWTAuthFilter(jwtServcie, userDetailsServiceImpl);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JWTServcie jwtServcie)throws SecurityException,Exception
    {
        return http.csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth->auth
                            .requestMatchers("/auth/v1/login", "/auth/v1/refreshToken", "/auth/v1/signup").permitAll()
                            .anyRequest().authenticated()
                            )
                    .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtAuthFilter(jwtServcie,userDetailsServiceImpl),UsernamePasswordAuthenticationFilter.class)
                    .authenticationProvider(authProvider())
                    .build();        
    }

    @Bean
    public AuthenticationProvider authProvider()
    {
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception
    {
        return config.getAuthenticationManager();
    }
}
