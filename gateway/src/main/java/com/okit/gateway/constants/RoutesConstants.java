package com.okit.gateway.constants;

import java.util.List;

public class RoutesConstants
{
    public static final String AUTH_PREFIX = "/api/v1/auth";
    public static final String PRODUCT_PREFIX = "/api/v1/resources/product";
    public static final String QUERY_PRODUCT = "/query";
    public static final String REGISTER_ENDPOINT = "/register";
    public static final String AUTHENTICATE_ENDPOINT = "/authenticate";
    public static final String VALIDATE_ENDPOINT = "/validate";
    public static final String JWT_SERVICE_ENDPOINT = "http://AUTH-CORE";
    public static final List<String> SWAGGER_UI = List.of(
            "/v3/api-docs/",
            "/swagger-ui/",
            "/v2/api-docs/",
            "/swagger-resources/"
    );
}
