package com.karthik.hotelmanagement.model;

import lombok.Data;

@Data
public class UserDto {

	private int id;
	private String name;
	private String email;
	private String phoneno;
	private WalletDto wallet;
}
