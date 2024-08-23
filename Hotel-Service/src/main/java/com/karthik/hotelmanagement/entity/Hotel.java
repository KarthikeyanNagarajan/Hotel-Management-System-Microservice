package com.karthik.hotelmanagement.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "hotel_table")
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_id")
	private int id;

	@Column(name = "hotel_name")
	private String name;

	@Column(name = "hotel_contact")
	private String contact;

	@Column(name = "hotel_location")
	private String location;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
	private List<Room> rooms;
}