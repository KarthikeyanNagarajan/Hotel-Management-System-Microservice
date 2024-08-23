package com.karthik.hotelmanagement.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.karthik.hotelmanagement.model.HotelRoomDto;

@FeignClient(name = "hotel-service", url = "http://localhost:8086", path = "/hotelservice")
public interface HotelBookingFeignClient {

	@GetMapping("/guest/getHotelAndRoomDetails/{hotelId}/{roomId}")
	HotelRoomDto getHotelAndRoomDetails(@PathVariable("hotelId") int hotelId, @PathVariable("roomId") int roomNumber);

	@PostMapping("/admin/updateRoomStatusByHotelRoomId/{hotelId}/{roomId}/{status}")
	void updateRoomStatusByHotelRoomId(@PathVariable("hotelId") int hotelId, @PathVariable("roomId") int roomId,
			@PathVariable("status") String status);
}
