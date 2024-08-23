package com.karthik.hotelmanagement.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.karthik.hotelmanagement.model.UserDto;

@FeignClient(name = "user-service", url="http://localhost:8085", path = "/userservice")
public interface UserBookingFeignClient {

	@GetMapping("/useradmin/getUserById/{id}")
	UserDto getUserById(@PathVariable("id") int id);
}
