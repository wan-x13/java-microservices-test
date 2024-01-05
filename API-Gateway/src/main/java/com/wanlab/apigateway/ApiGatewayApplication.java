package com.wanlab.apigateway;

import com.wanlab.apigateway.config.AuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    private final AuthenticationFilter filter;

    public ApiGatewayApplication(AuthenticationFilter filter) {
        this.filter = filter;
    }

    public static void main(String[] args) {

        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/api/v1/auth/**")
                        .uri("http://localhost:9080"))
                .route("article-service", r->r.path("/api/v1/articles/**")
                        .filters(f->f.filter(filter))
                        .uri("http://localhost:9020"))
                .build();
    }


}
