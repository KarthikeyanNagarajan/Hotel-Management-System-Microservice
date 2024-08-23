package com.karthik.hotelmanagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.karthik.hotelmanagement.entity.Booking;
import com.karthik.hotelmanagement.model.BookingRequest;
import com.karthik.hotelmanagement.model.BookingResponse;
import com.karthik.hotelmanagement.service.BookingService;

@RestController
@RequestMapping("/bookingservice")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@GetMapping("/admin/getAllBookings")
	public ResponseEntity<List<Booking>> getAllBookings() {
		List<Booking> bookings = bookingService.getAllBookings();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookings);
	}

	@GetMapping("/useradmin/getBookingById/{id}")
	public ResponseEntity<Booking> getBookingById(@PathVariable("id") int id) {
		Booking booking = bookingService.getBookingById(id);
		if (booking != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(booking);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PostMapping("/user/createBooking")
	public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
		BookingResponse bookingResponse = bookingService.createBooking(request);
		return ResponseEntity.status(HttpStatus.OK).body(bookingResponse);
	}

	@PostMapping("/useradmin/cancelBookingById/{id}")
	public ResponseEntity<Booking> cancelBookingById(@PathVariable("id") int id) {
		Booking booking = bookingService.cancelBooking(id);
		if (booking != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(booking);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
