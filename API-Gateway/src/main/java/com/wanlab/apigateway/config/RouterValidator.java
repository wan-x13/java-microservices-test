package com.wanlab.apigateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {
    public static final List<String> apiEndpoints = List.of(
            "/auth/login"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> apiEndpoints
            .stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
