package com.karthik.hotelmanagement.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.karthik.hotelmanagement.model.HotelRoomDto;

@FeignClient(name = "hotel-service", url="http://localhost:8086", path = "/hotelservice")
public interface HotelRatingFeignClient {

	@GetMapping("/guest/getHotelById/{hotelId}")
	HotelRoomDto getHotelById(@PathVariable("hotelId") int hotelId);

}
