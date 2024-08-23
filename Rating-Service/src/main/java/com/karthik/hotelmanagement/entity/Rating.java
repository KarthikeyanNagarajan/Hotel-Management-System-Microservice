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
@Table(name = "rating_table")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rating_id")
	private int id;

	@Column(name = "user_id")
	private int userid;

	@Column(name = "hotel_id")
	private int hotelid;

	@Column(name = "user_name")
	private String username;

	@Column(name = "hotel_name")
	private String hotelname;

	@Column(name = "hotel_rating")
	private int rating;

	@Column(name = "remarks")
	private String remarks;
}
