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
@Table(name = "payment_table")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "payment_id")
	private int id;
	
	@Column(name = "user_id")
	private int userid;

	@Column(name = "payment_amount")
	private int amount;

	@Column(name = "payment_status")
	private String status;
}