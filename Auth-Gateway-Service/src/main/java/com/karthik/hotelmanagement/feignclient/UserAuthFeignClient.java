package com.karthik.hotelmanagement.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.karthik.hotelmanagement.model.UserRegisterDto;

@FeignClient(name = "user-service", path = "/userservice")
public interface UserAuthFeignClient {

	@PostMapping("/feign/saveuser")
	UserRegisterDto saveUser(@RequestBody UserRegisterDto userDto);
}
