package com.karthik.hotelmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginDto {

	private String username;
	private String password;
}
