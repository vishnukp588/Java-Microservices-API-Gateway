package com.spyzi.microservice.apigateway.configuration;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * @author vishnu.kp
 */
@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routLocBuilder)
    {
       /* Function<PredicateSpec, Buildable<Route>> routFunction
                = p-> p.path("/get").
                filters(f->f.addRequestHeader("MyHeader","MyURI")
                        .addRequestHeader("Param","MyValue")).
                uri("http://httpbin.org:80");
        return routLocBuilder.routes().route(routFunction).build();*/

        return routLocBuilder.routes().route(p-> p.path("/get").
        filters(f->f.addRequestHeader("MyHeader","MyURI")
                .addRequestHeader("Param","MyValue")).
        uri("http://httpbin.org:80")).
                route(p->p.path("/currency-exchange/**").
                        uri("lb://currency-exchange-service")).
                route(p->p.path("/currency-conversion-feign/**").
                        uri("lb://currency-conversion-service")).
                route(p->p.path("/currency-conversion-new/**").
                        filters(f->f.rewritePath("/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion-service")).build();
    }
}
