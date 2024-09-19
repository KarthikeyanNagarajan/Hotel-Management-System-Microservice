package com.karthik.hotelmanagement.config;

import java.time.Duration;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class RouteConfig {

	@Bean
	public RouterFunction<ServerResponse> userServiceRoute() {
		return GatewayRouterFunctions.route("user-service")
				.route(RequestPredicates.path("/userservice/**"), HandlerFunctions.http())
				.filter(LoadBalancerFilterFunctions.lb("user-service"))
				.filter(CircuitBreakerFilterFunctions.circuitBreaker(config -> config.setId("defaultCircuitBreaker").setFallbackUri("forward:/fallback/user")))
				.build();
	}

	@Bean
	public RouterFunction<ServerResponse> hotelServiceRoute() {
		return GatewayRouterFunctions.route("hotel-service")
				.route(RequestPredicates.path("/hotelservice/**"), HandlerFunctions.http())
				.filter(LoadBalancerFilterFunctions.lb("hotel-service"))
				.filter(CircuitBreakerFilterFunctions.circuitBreaker(config -> config.setId("defaultCircuitBreaker").setFallbackUri("forward:/fallback/hotel")))
				.build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> ratingServiceRoute() {
		return GatewayRouterFunctions.route("rating-service")
				.route(RequestPredicates.path("/ratingservice/**"), HandlerFunctions.http())
				.filter(LoadBalancerFilterFunctions.lb("rating-service"))
				.filter(CircuitBreakerFilterFunctions.circuitBreaker(config -> config.setId("defaultCircuitBreaker").setFallbackUri("forward:/fallback/rating")))
				.build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> paymentServiceRoute() {
		return GatewayRouterFunctions.route("payment-service")
				.route(RequestPredicates.path("/paymentservice/**"), HandlerFunctions.http())
				.filter(LoadBalancerFilterFunctions.lb("payment-service"))
				.filter(CircuitBreakerFilterFunctions.circuitBreaker(config -> config.setId("defaultCircuitBreaker").setFallbackUri("forward:/fallback/payment")))
				.build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> bookingServiceRoute() {
		return GatewayRouterFunctions.route("booking-service")
				.route(RequestPredicates.path("/bookingservice/**"), HandlerFunctions.http())
				.filter(LoadBalancerFilterFunctions.lb("booking-service"))
				.filter(CircuitBreakerFilterFunctions.circuitBreaker(config -> config.setId("defaultCircuitBreaker").setFallbackUri("forward:/fallback/booking")))
				.build();
	}
	
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCircuitBreakerCustomizer() {
	    return factory -> factory
	        .configureDefault(
	            id -> new Resilience4JConfigBuilder(id)
	                .circuitBreakerConfig(
	                    CircuitBreakerConfig.custom()
	                    .slidingWindowType(SlidingWindowType.COUNT_BASED)
	                    .slidingWindowSize(5)
	                    .minimumNumberOfCalls(2)
	                    .build())
	                .timeLimiterConfig(TimeLimiterConfig.custom()
	                    .timeoutDuration(Duration.ofSeconds(60)).build())
	                .build());
	}
}
