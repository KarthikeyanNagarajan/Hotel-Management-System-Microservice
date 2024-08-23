package com.karthik.hotelmanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_DEFAULT)
public class BookingResponse {
	private String userName;
	private String userEmail;
	private String hotelName;
	private String hotelLocation;
	private String roomType;
	private int roomPrice;
	private String checkindate;
	private String checkoutdate;
	private int totalAmount;
	private int paymentId;
	private String paymentStatus;
	private int bookingId;
	private String bookingStatus;
}
