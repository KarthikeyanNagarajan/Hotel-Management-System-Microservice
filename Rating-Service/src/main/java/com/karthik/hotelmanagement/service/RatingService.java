package com.karthik.hotelmanagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.karthik.hotelmanagement.entity.Rating;
import com.karthik.hotelmanagement.feignclient.HotelRatingFeignClient;
import com.karthik.hotelmanagement.feignclient.UserRatingFeignClient;
import com.karthik.hotelmanagement.model.HotelRoomDto;
import com.karthik.hotelmanagement.model.UserDto;
import com.karthik.hotelmanagement.repository.RatingRepository;

@Service
public class RatingService {

	@Autowired
	private UserRatingFeignClient userRatingFeignClient;

	@Autowired
	private HotelRatingFeignClient hotelRatingFeignClient;

	@Autowired
	private RatingRepository ratingRepository;

	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}

	public List<Rating> getRatingsByUserId(int userId) {
		return ratingRepository.findByUserid(userId);
	}

	public List<Rating> getRatingsByHotelId(int hotelId) {
		return ratingRepository.findByHotelid(hotelId);
	}

	public Rating saveRating(Rating rating) {

		UserDto user = userRatingFeignClient.getUserById(rating.getUserid());
		HotelRoomDto hotel = hotelRatingFeignClient.getHotelById(rating.getHotelid());

		rating.setUsername(user.getName());
		rating.setHotelname(hotel.getName());
		return ratingRepository.save(rating);
	}

}
