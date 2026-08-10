package com.movierating.movie_rating_backend.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .cors(Customizer.withDefaults())

                .sessionManagement(session -> session

                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                	    // Public APIs
                	    .requestMatchers(
                	            "/api/auth/**",
                	            "/api/genres/**",
                	            "/api/movies/search/**",
                	            "/api/movies/top-rated",
                	            "/api/movies/latest"
                	    ).permitAll()

                	    // Only ADMIN can modify movies
                	    .requestMatchers(
                	            org.springframework.http.HttpMethod.POST,
                	            "/api/movies/**"
                	    ).hasRole("ADMIN")

                	    .requestMatchers(
                	            org.springframework.http.HttpMethod.PUT,
                	            "/api/movies/**"
                	    ).hasRole("ADMIN")

                	    .requestMatchers(
                	            org.springframework.http.HttpMethod.DELETE,
                	            "/api/movies/**"
                	    ).hasRole("ADMIN")

                	    // Everyone can view movies
                	    .requestMatchers(
                	            org.springframework.http.HttpMethod.GET,
                	            "/api/movies/**"
                	    ).permitAll()

                	    .requestMatchers("/api/admin/**")
                	    .hasRole("ADMIN")

                	    .requestMatchers("/api/user/**")
                	    .hasAnyRole("USER", "ADMIN")

                	    .anyRequest()
                	    .authenticated()
                	)
                .authenticationProvider(authenticationProvider())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();

    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();

    }

}