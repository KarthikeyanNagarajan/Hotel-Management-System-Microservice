package com.karthik.hotelmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthik.hotelmanagement.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

	public List<Hotel> findByLocation(String location);

	public Optional<Hotel> findByName(String name);
}
