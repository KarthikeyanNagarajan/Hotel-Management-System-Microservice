package com.karthik.hotelmanagement.model;

import lombok.Data;

@Data
public class BookingRequest {
	private int userId;
	private int hotelId;
	private int roomId;
	private String checkindate;
	private String checkoutdate;
}
