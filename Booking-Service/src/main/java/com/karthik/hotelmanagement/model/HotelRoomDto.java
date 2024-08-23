package com.karthik.hotelmanagement.model;

import java.util.List;

import lombok.Data;

@Data
public class HotelRoomDto {
	private String name;
	private String contact;
	private String location;
	private List<RoomDto> rooms;
}
