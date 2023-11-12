package com.okit.gateway.config;

import com.okit.gateway.constants.RoutesConstants;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RoutesConfiguration
{
    public static final List<String> OPEN_API_ENDPOINTS = List.of(
            RoutesConstants.AUTH_PREFIX + RoutesConstants.REGISTER_ENDPOINT,
            RoutesConstants.AUTH_PREFIX + RoutesConstants.AUTHENTICATE_ENDPOINT,
            RoutesConstants.AUTH_PREFIX + RoutesConstants.VALIDATE_ENDPOINT,
            RoutesConstants.PRODUCT_PREFIX + RoutesConstants.QUERY_PRODUCT
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> OPEN_API_ENDPOINTS.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
