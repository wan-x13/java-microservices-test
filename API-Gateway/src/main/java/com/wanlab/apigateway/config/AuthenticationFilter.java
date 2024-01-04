package com.wanlab.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {
   private final RouterValidator routerValidator;
   private final JwtUtil jwtUtil;

   @Autowired
    public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil) {
        this.routerValidator = routerValidator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
       System.out.println(exchange);
       if(isAuthMissing(exchange.getRequest())){
           return onError(exchange, HttpStatus.UNAUTHORIZED);
       }
       ServerHttpRequest request = exchange.getRequest();
       if(!routerValidator.isSecured.test(request)){
           return chain.filter(exchange);
       }
       String authHeader = this.getAuthHeader(request);
       if(authHeader == null || !authHeader.startsWith("Bearer ")){
           return chain.filter(exchange);
       }
       String token = authHeader.substring(7);
       if(jwtUtil.isInvalid(token)){
           return chain.filter(exchange);
       }
       return chain.filter(exchange);
    }
    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private String getAuthHeader(ServerHttpRequest request){
        return request.getHeaders().getFirst("Authorization");
    }
}
