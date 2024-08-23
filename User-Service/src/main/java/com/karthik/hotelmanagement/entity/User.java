package com.karthik.hotelmanagement.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "user_table")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;

	@Column(name = "user_name")
	private String name;

	@Column(name = "user_email")
	private String email;

	@Column(name = "user_phoneno")
	private String phoneno;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Wallet wallet;

}