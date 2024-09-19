package com.karthik.hotelmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

	@GetMapping("/user")
	public ResponseEntity<String> userFallback() {
		return ResponseEntity.internalServerError().body("UserService is down or Request TimeLimit Exceeded");
	}

	@GetMapping("/hotel")
	public ResponseEntity<String> hotelFallback() {

		return ResponseEntity.internalServerError().body("HotelService is down or Request TimeLimit Exceeded");
	}

	@GetMapping("/rating")
	public ResponseEntity<String> ratingFallback() {

		return ResponseEntity.internalServerError().body("RatingService is down or Request TimeLimit Exceeded");
	}

	@GetMapping("/payment")
	public ResponseEntity<String> paymentFallback() {

		return ResponseEntity.internalServerError().body("PaymentService is down or Request TimeLimit Exceeded");
	}

	@GetMapping("/booking")
	public ResponseEntity<String> bookingFallback() {

		return ResponseEntity.internalServerError().body("BookingService is down or Request TimeLimit Exceeded");
	}
}
