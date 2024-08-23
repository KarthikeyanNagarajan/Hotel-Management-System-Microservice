package com.karthik.hotelmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.karthik.hotelmanagement.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

	List<Rating> findByUserid(int userId);

	List<Rating> findByHotelid(int hotelId);

}
