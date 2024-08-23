package com.karthik.hotelmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "booking_table")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private int id;
	
	@Column(name = "user_id")
	private int userid;
	
	@Column(name = "hotel_id")
	private int hotelid;
	
	@Column(name = "room_id")
	private int roomid;
	
	@Column(name = "payment_id")
	private int paymentid;
	
	@Column(name = "room_type")
	private String roomtype;
	
	@Column(name = "booked_days")
	private int noOfDays;

	@Column(name = "booking_amount")
	private int amount;

	@Column(name = "booking_checkindate")
	private String checkindate;

	@Column(name = "booking_checkoutdate")
	private String checkoutdate;

	@Column(name = "booking_status")
	private String bookingStatus;
}