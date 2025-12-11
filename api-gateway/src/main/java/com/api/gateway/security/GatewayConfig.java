package com.api.gateway.security;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public GatewayConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

//    @Bean
//    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
//
//        return builder.routes()
//                .route("user-service", r -> r.path("/users/**")
//                        .filters(f -> f.filter(jwtFilter))
//                        .uri("lb://USER-SERVICE"))
//                .build();
//    }
}

