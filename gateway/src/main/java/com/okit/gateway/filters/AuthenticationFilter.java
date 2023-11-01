package com.okit.gateway.filters;

import com.okit.gateway.config.RoutesConfiguration;
import com.okit.gateway.dto.ValidateRequest;
import com.okit.gateway.dto.ValidateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.okit.gateway.constants.RoutesConstants.*;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter
{
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        ServerHttpRequest request = exchange.getRequest();

        if(routesConfiguration.isSecured.test(request))
        {
            List<String> headers = request.getHeaders().getOrEmpty("Authorization");

            if(headers.isEmpty())
            {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String authHeader = headers.get(0);

            if(authHeader == null || !authHeader.startsWith("Bearer "))
            {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String token = authHeader.substring(7);
            final ValidateRequest validateRequest = ValidateRequest.builder().token(token).build();
            final String endpoint = JWT_SERVICE_ENDPOINT + AUTH_PREFIX + VALIDATE_ENDPOINT;

            return webClientBuilder.build().post()
                    .uri(endpoint)
                    .body(BodyInserters.fromValue(validateRequest))
                    .retrieve()
                    .bodyToMono(ValidateResponse.class)
                    .flatMap(res -> {
                        if(res == null || !res.isValid())
                        {
                            return onError(exchange, HttpStatus.FORBIDDEN);
                        }

                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("email", res.getEmail())
                                .build();

                        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                        return chain.filter(mutatedExchange);
                    });
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus)
    {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

    @Autowired
    private final RoutesConfiguration routesConfiguration;

    @Autowired
    private final WebClient.Builder webClientBuilder;
}