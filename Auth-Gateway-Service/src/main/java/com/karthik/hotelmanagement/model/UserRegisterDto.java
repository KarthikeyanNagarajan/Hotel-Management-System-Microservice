package com.karthik.hotelmanagement.model;

import lombok.Data;

@Data
public class UserRegisterDto {

	private int userid;
	private String name;
	private String password;
	private String email;
	private String phoneno;
}
