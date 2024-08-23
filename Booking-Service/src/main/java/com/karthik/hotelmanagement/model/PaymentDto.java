package com.karthik.hotelmanagement.model;

import lombok.Data;

@Data
public class PaymentDto {

	private int id;
	private int amount;
	private String status;
}
