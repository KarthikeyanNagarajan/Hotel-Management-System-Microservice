package com.karthik.hotelmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.karthik.hotelmanagement.entity.Hotel;
import com.karthik.hotelmanagement.entity.Room;
import com.karthik.hotelmanagement.entity.util.RoomStatus;
import com.karthik.hotelmanagement.repository.HotelRepository;
import com.karthik.hotelmanagement.repository.RoomRepository;

@Service
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private RoomRepository roomRepository;

	// Hotel

	public List<Hotel> getAllHotels() {
		return hotelRepository.findAll();
	}

	public Hotel getHotelById(int hotelId) {
		return hotelRepository.findById(hotelId).orElse(null);
	}

	public List<Hotel> getHotelsByLocation(String location) {
		List<Hotel> hotels = hotelRepository.findByLocation(location);
		if (hotels != null) {
			return hotels;
		}
		return null;
	}

	public Hotel getHotelByName(String name) {
		return hotelRepository.findByName(name).orElse(null);
	}

	public Hotel saveHotel(Hotel hotel) {
		return hotelRepository.save(hotel);
	}

	public Hotel deleteHotelById(int hotelId) {
		Hotel hotel = getHotelById(hotelId);
		if (hotel != null) {
			hotelRepository.deleteById(hotelId);
		}
		return hotel;
	}

	// Room

	public List<Room> getAllHotelRooms(int hotelId) {
		Hotel hotel = getHotelById(hotelId);
		List<Room> rooms = new ArrayList<>();
		if (hotel != null) {
			rooms = hotel.getRooms();
		}
		return rooms;
	}

	public Room getRoomById(int roomId) {
		return roomRepository.findById(roomId).orElse(null);
	}

	public List<Room> getAvailableRoomsInHotel(int hotelId) {
		List<Room> availableRooms = new ArrayList<>();
		Hotel hotel = getHotelById(hotelId);
		List<Room> rooms = new ArrayList<>();
		if (hotel != null) {
			rooms = hotel.getRooms();
			availableRooms = rooms.stream().filter(r -> r.getStatus().equals(RoomStatus.AVAILABLE.toString()))
					.collect(Collectors.toList());
		}

		return availableRooms;
	}

	public List<Room> getBookedRoomsInHotel(int hotelId) {
		List<Room> bookedRooms = new ArrayList<>();
		Hotel hotel = getHotelById(hotelId);
		List<Room> rooms = new ArrayList<>();
		if (hotel != null) {
			rooms = hotel.getRooms();
			bookedRooms = rooms.stream().filter(r -> r.getStatus().equals(RoomStatus.BOOKED.toString()))
					.collect(Collectors.toList());
		}

		return bookedRooms;
	}

	public Hotel getHotelAndRoomDetails(int hotelId, int roomId) {
		Hotel hotel = getHotelById(hotelId);
		if (hotel != null) {
			Optional<Room> roomOpt = hotel.getRooms().stream().filter(r -> r.getId() == roomId).findAny();
			if (roomOpt.isPresent()) {

				List<Room> roomDto = new ArrayList<>();
				roomDto.add(roomOpt.get());

				Hotel hotelDto = new Hotel();
				hotelDto.setName(hotel.getName());
				hotelDto.setLocation(hotel.getLocation());
				hotelDto.setContact(hotel.getContact());
				hotelDto.setRooms(roomDto);
				return hotelDto;
			}
		}

		return null;
	}

	public void updateRoomStatus(int hotelId, int roomId, String status) {
		Hotel hotel = getHotelById(hotelId);
		if (hotel != null) {
			Optional<Room> roomOpt = hotel.getRooms().stream().filter(r -> r.getId() == roomId).findAny();
			if (roomOpt.isPresent()) {
				if (roomOpt.get() != null) {
					roomOpt.get().setStatus(status);
					saveRoom(hotelId, roomOpt.get());
				}
			}
		}
	}

	public Room saveRoom(int hotelId, Room room) {
		Hotel hotel = getHotelById(hotelId);
		if (hotel != null) {
			hotel.getRooms().add(room);
			room.setHotel(hotel);
			hotelRepository.save(hotel);
		}

		return room;
	}

	public Room deleteRoomById(int roomId) {
		Room room = getRoomById(roomId);
		if (room != null) {
			roomRepository.deleteById(roomId);
			return room;
		}

		return null;
	}
}
