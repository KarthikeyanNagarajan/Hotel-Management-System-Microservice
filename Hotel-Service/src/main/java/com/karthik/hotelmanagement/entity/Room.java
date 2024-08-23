package com.karthik.hotelmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data
@Table(name = "room_table")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private int id;

	@Column(name = "room_price")
	private int roomPrice;

	@Column(name = "room_type")
	private String roomType;

	@Column(name = "room_status")
	private String Status;

	@ManyToOne
	@JoinColumn(name="hotel_id", nullable=false)
	@JsonIgnore
	private Hotel hotel;
}
