package com.karthik.hotelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthik.hotelmanagement.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

}
