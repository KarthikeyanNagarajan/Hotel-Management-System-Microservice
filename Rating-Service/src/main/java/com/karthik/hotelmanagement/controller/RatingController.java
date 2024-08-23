package com.karthik.hotelmanagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.karthik.hotelmanagement.entity.Rating;
import com.karthik.hotelmanagement.service.RatingService;

@RestController
@RequestMapping("/ratingservice")
public class RatingController {

	@Autowired
	private RatingService ratingService;

	@GetMapping("/guest/getAllRatings")
	public ResponseEntity<List<Rating>> getAllRatings() {
		List<Rating> ratings = ratingService.getAllRatings();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ratings);
	}

	@GetMapping("/useradmin/getRatingsByUserId/{userid}")
	public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable int userid) {
		List<Rating> ratings = ratingService.getRatingsByUserId(userid);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ratings);
	}

	@GetMapping("/useradmin/getRatingsByHotelId/{hotelid}")
	public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable int hotelid) {
		List<Rating> ratings = ratingService.getRatingsByHotelId(hotelid);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ratings);
	}

	@PostMapping("/user/saveRating")
	public ResponseEntity<Rating> saveRating(@RequestBody Rating rating) {
		Rating ratingDto = ratingService.saveRating(rating);
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingDto);
	}
}
