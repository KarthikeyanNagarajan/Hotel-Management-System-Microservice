package com.karthik.hotelmanagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.karthik.hotelmanagement.entity.Hotel;
import com.karthik.hotelmanagement.entity.Room;
import com.karthik.hotelmanagement.service.HotelService;

@RestController
@RequestMapping("/hotelservice")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	// Hotels

	@GetMapping("/guest/getAllHotels")
	public ResponseEntity<List<Hotel>> getAllHotels() {
		List<Hotel> hotels = hotelService.getAllHotels();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(hotels);
	}

	@GetMapping("/guest/getHotelById/{hotelId}")
	public ResponseEntity<Hotel> getHotelById(@PathVariable int hotelId) {
		Hotel hotel = hotelService.getHotelById(hotelId);
		if (hotel != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(hotel);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@GetMapping("/guest/getHotelsByLocation/{location}")
	public ResponseEntity<List<Hotel>> getHotelsByLocation(@PathVariable String location) {
		List<Hotel> hotels = hotelService.getHotelsByLocation(location);
		if (hotels != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(hotels);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@GetMapping("/guest/getHotelByName/{name}")
	public ResponseEntity<Hotel> getHotelByName(@PathVariable String name) {
		Hotel hotel = hotelService.getHotelByName(name);
		if (hotel != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(hotel);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PostMapping("/admin/saveHotel")
	public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel) {
		Hotel hotelDto = hotelService.saveHotel(hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(hotelDto);
	}

	@DeleteMapping("/admin/deleteHotelById/{hotelId}")
	public ResponseEntity<Hotel> deleteHotelById(@PathVariable int hotelId) {
		Hotel hotel = hotelService.deleteHotelById(hotelId);
		if (hotel != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(hotel);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	// Rooms

	@GetMapping("/guest/getAllRoomsByHotelId/{hotelId}")
	public ResponseEntity<List<Room>> getAllRoomsByHotelId(@PathVariable int hotelId) {
		List<Room> rooms = hotelService.getAllHotelRooms(hotelId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(rooms);
	}

	@GetMapping("/guest/getRoomByRoomId/{roomId}")
	public ResponseEntity<Room> getRoomByRoomId(@PathVariable int roomId) {
		Room room = hotelService.getRoomById(roomId);
		if (room != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(room);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@GetMapping("/guest/getAvailableRoomsByHotelId/{hotelId}")
	public ResponseEntity<List<Room>> getAvailableRoomsByHotelId(@PathVariable int hotelId) {
		List<Room> rooms = hotelService.getAvailableRoomsInHotel(hotelId);
		if (rooms != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(rooms);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@GetMapping("/admin/getBookedRoomsByHotelId/{hotelId}")
	public ResponseEntity<List<Room>> getBookedRoomsByHotelId(@PathVariable int hotelId) {
		List<Room> rooms = hotelService.getBookedRoomsInHotel(hotelId);
		if (rooms != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(rooms);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@GetMapping("/guest/getHotelAndRoomDetails/{hotelId}/{roomId}")
	public ResponseEntity<Hotel> getHotelAndRoomDetails(@PathVariable int hotelId, @PathVariable int roomId) {
		Hotel hotelDto = hotelService.getHotelAndRoomDetails(hotelId, roomId);
		if (hotelDto != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(hotelDto);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PostMapping("/admin/updateRoomStatusByHotelRoomId/{hotelId}/{roomId}/{status}")
	public void updateRoomStatusByHotelRoomId(@PathVariable int hotelId, @PathVariable int roomId,
			@PathVariable String status) {
		hotelService.updateRoomStatus(hotelId, roomId, status);
	}

	@PostMapping("/admin/saveRoomByHotelId/{hotelId}")
	public ResponseEntity<Room> saveRoomByHotelId(@PathVariable int hotelId, @RequestBody Room hotel) {
		Room roomDto = hotelService.saveRoom(hotelId, hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(roomDto);
	}

	@DeleteMapping("/admin/deleteRoomByRoomId/{roomId}")
	public ResponseEntity<Room> deleteRoomByRoomId(@PathVariable int roomId) {
		Room roomDto = hotelService.deleteRoomById(roomId);
		return ResponseEntity.status(HttpStatus.CREATED).body(roomDto);
	}
}
