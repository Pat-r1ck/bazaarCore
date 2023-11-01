package com.okit.gateway.config;

import com.okit.gateway.filters.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration
{
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder)
    {
        return builder.routes()
                .route("AUTH-CORE", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://AUTH-CORE"))
                .route("PROFILE-SERVICE", r -> r.path("/api/v1/profile/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://PROFILE-SERVICE")
                )
                .build();
    }

    @Autowired
    private final AuthenticationFilter filter;
}
