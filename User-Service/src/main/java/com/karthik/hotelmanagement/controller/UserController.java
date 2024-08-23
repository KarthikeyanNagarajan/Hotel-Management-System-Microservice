package com.karthik.hotelmanagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.karthik.hotelmanagement.entity.User;
import com.karthik.hotelmanagement.entity.Wallet;
import com.karthik.hotelmanagement.service.UserService;

@RestController
@RequestMapping("/userservice")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/admin/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);
	}

	@GetMapping("/useradmin/getUserById/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		User user = userService.getUserById(id);
		if (user != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@GetMapping("/useradmin/getUserByName/{name}")
	public ResponseEntity<User> getUserByName(@PathVariable String name) {
		User user = userService.getUserByName(name);
		if (user != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PostMapping("/feign/saveuser")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User userDto = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
	}

	@GetMapping("/useradmin/getWalletByUserId/{id}")
	public ResponseEntity<Wallet> getWalletByUserId(@PathVariable int id) {

		Wallet wallet = userService.getWalletByUserId(id);
		if (wallet != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(wallet);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@GetMapping("/user/getWalletBalanceByUserId/{id}")
	public ResponseEntity<Integer> getWalletBalanceByUserId(@PathVariable int id) {
		int balance = userService.getUserBalance(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(balance);
	}

	@PostMapping("/user/updateBalanceByUserId/{id}/{amount}")
	public ResponseEntity<Wallet> updateBalanceByUserId(@PathVariable int id, @PathVariable int amount) {
		Wallet wallet = userService.updateBalance(id, amount);
		if (wallet != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(wallet);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
