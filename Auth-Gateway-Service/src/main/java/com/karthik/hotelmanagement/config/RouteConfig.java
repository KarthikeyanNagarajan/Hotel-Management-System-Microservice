package com.karthik.hotelmanagement.config;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouteConfig {

	@Bean
	public RouterFunction<ServerResponse> userServiceRoute() {
		return GatewayRouterFunctions.route("user-service")
				.route(RequestPredicates.path("/userservice/**"), HandlerFunctions.http("http://localhost:8085")).build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> hotelServiceRoute() {
		return GatewayRouterFunctions.route("hotel-service")
				.route(RequestPredicates.path("/hotelservice/**"), HandlerFunctions.http("http://localhost:8086")).build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> ratingServiceRoute() {
		return GatewayRouterFunctions.route("rating-service")
				.route(RequestPredicates.path("/ratingservice/**"), HandlerFunctions.http("http://localhost:8087")).build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> paymentServiceRoute() {
		return GatewayRouterFunctions.route("payment-service")
				.route(RequestPredicates.path("/paymentservice/**"), HandlerFunctions.http("http://localhost:8088")).build();
	}
	
	@Bean
	public RouterFunction<ServerResponse> bookingServiceRoute() {
		return GatewayRouterFunctions.route("booking-service")
				.route(RequestPredicates.path("/bookingservice/**"), HandlerFunctions.http("http://localhost:8089")).build();
	}

}
