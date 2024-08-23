package com.karthik.hotelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthik.hotelmanagement.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
