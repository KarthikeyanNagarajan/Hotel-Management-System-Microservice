package com.karthik.hotelmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.karthik.hotelmanagement.entity.Role;
import com.karthik.hotelmanagement.entity.User;
import com.karthik.hotelmanagement.model.JwtResponse;
import com.karthik.hotelmanagement.model.UserLoginDto;
import com.karthik.hotelmanagement.model.UserRegisterDto;
import com.karthik.hotelmanagement.service.JwtUtils;
import com.karthik.hotelmanagement.service.UserInfoService;

@RestController
public class AuthController {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserInfoService userService;
	
	@PostMapping("/register")
	public String register(@RequestBody UserRegisterDto userDto) throws Exception {

		userService.register(userDto);
		return "User Registered";
	}

	@PostMapping("/getToken")
	public JwtResponse authenticate(@RequestBody UserLoginDto loginRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		} catch (Exception e) {
			throw new Exception("Bad Credentials");
		}

		UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
		String jwtToken = jwtUtils.generateToken(userDetails);
		return new JwtResponse(jwtToken);
	}	

	@GetMapping("/getUsersCredentials")
	public List<User> getUsersCredentials() {

		return userService.getUsers();
	}

	@GetMapping("/getUserRoles")
	public List<Role> getUserRoles() {

		return userService.getRoles();
	}

	@GetMapping("/usersTest")
	public String user() {

		return "Hello from Users";
	}

	@GetMapping("/adminsTest")
	public String admin() {

		return "Hello from Admins";
	}
}
