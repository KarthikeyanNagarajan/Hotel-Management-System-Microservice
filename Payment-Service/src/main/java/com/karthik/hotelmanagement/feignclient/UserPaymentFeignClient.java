package com.karthik.hotelmanagement.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.karthik.hotelmanagement.model.UserDto;
import com.karthik.hotelmanagement.model.WalletDto;

@FeignClient(name = "user-service", url="http://localhost:8085", path = "/userservice")
public interface UserPaymentFeignClient {

	@GetMapping("/useradmin/getUserById/{id}")
	UserDto getUserById(@PathVariable("id") int id);
	
	@PostMapping("/user/updateBalanceByUserId/{id}/{amount}")
	WalletDto updateBalanceByUserId(@PathVariable("id") int id, @PathVariable("amount") int amount);
}
