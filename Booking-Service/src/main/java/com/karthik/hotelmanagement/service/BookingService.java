package com.karthik.hotelmanagement.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.karthik.hotelmanagement.entity.Booking;
import com.karthik.hotelmanagement.feignclient.HotelBookingFeignClient;
import com.karthik.hotelmanagement.feignclient.PaymentBookingFeignClient;
import com.karthik.hotelmanagement.feignclient.UserBookingFeignClient;
import com.karthik.hotelmanagement.model.BookingRequest;
import com.karthik.hotelmanagement.model.BookingResponse;
import com.karthik.hotelmanagement.model.HotelRoomDto;
import com.karthik.hotelmanagement.model.PaymentDto;
import com.karthik.hotelmanagement.model.UserDto;
import com.karthik.hotelmanagement.repository.BookingRepository;

@Service
public class BookingService {

	private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

	@Autowired
	private UserBookingFeignClient userBookingFeignClient;

	@Autowired
	private HotelBookingFeignClient hotelBookingFeignClient;

	@Autowired
	private PaymentBookingFeignClient paymentBookingFeignClient;

	@Autowired
	private BookingRepository bookingRepository;

	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	public Page<Booking> getAllBookingsPaged(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return bookingRepository.findAll(pageable);
	}

	public Booking getBookingById(int id) {
		return bookingRepository.findById(id).orElse(null);
	}

	public Booking saveBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	public BookingResponse createBooking(BookingRequest request) {

		BookingResponse bookingResponse = new BookingResponse();
		UserDto userDto = null;
		try {
			userDto = userBookingFeignClient.getUserById(request.getUserId());
			if (userDto != null) {

				logger.debug("User fetched for booking: {}", userDto);

				try {
					HotelRoomDto hotelRoomDto = hotelBookingFeignClient.getHotelAndRoomDetails(request.getHotelId(),
							request.getRoomId());
					if (hotelRoomDto != null) {

						logger.debug("Hotel room fetched for booking: {}", hotelRoomDto);

						if (!hotelRoomDto.getRooms().isEmpty()) {

							if (!hotelRoomDto.getRooms().get(0).getStatus().equals("BOOKED")) {

								hotelBookingFeignClient.updateRoomStatusByHotelRoomId(request.getHotelId(), request.getRoomId(),
										"BOOKING_IN_PROGRESS");

								int roomPrice = hotelRoomDto.getRooms().get(0).getRoomPrice();

								LocalDate date1 = LocalDate.parse(request.getCheckindate());
								LocalDate date2 = LocalDate.parse(request.getCheckoutdate());
								long day = Math.abs(ChronoUnit.DAYS.between(date1, date2));

								int totalAmount = roomPrice * (int) day;

								PaymentDto paymentDto = paymentBookingFeignClient.makePaymentByUserId(request.getUserId(),
										totalAmount);
								if (paymentDto != null && paymentDto.getStatus().equalsIgnoreCase("Payment Success")) {
									try {
										hotelBookingFeignClient.updateRoomStatusByHotelRoomId(request.getHotelId(), request.getRoomId(), "BOOKED");
										bookingResponse = createBookingResponse(request, hotelRoomDto, userDto, paymentDto, "BOOKED");

										Booking booking = new Booking();
										booking.setUserid(request.getUserId());
										booking.setHotelid(request.getHotelId());
										booking.setRoomid(request.getRoomId());
										booking.setPaymentid(paymentDto.getId());
										booking.setRoomtype(hotelRoomDto.getRooms().get(0).getRoomType());
										booking.setNoOfDays((int) day);
										booking.setAmount(totalAmount);
										booking.setCheckindate(request.getCheckindate());
										booking.setCheckoutdate(request.getCheckoutdate());
										booking.setBookingStatus("ROOM BOOKED");
										saveBooking(booking);
									} catch (Exception roomUpdateFailure) {
										bookingResponse = compensateFailedRoomConfirmation(request, hotelRoomDto, userDto, paymentDto, (int) day, totalAmount);
									}
								} else {
									bookingResponse = createBookingResponse(request, hotelRoomDto, userDto, paymentDto, "BOOKING FAILED");
									hotelBookingFeignClient.updateRoomStatusByHotelRoomId(request.getHotelId(), request.getRoomId(), "AVAILABLE");

									Booking booking = new Booking();
									booking.setUserid(request.getUserId());
									booking.setHotelid(request.getHotelId());
									booking.setRoomid(request.getRoomId());
									booking.setPaymentid(paymentDto.getId());
									booking.setRoomtype(hotelRoomDto.getRooms().get(0).getRoomType());
									booking.setNoOfDays((int) day);
									booking.setAmount(totalAmount);
									booking.setCheckindate(request.getCheckindate());
									booking.setCheckoutdate(request.getCheckoutdate());
									booking.setBookingStatus("BOOKING FAILED DUE TO PAYMENT FAILURE");
									saveBooking(booking);
								}
							} else {
								bookingResponse = createErrorResponse("Room already booked or Not Available");

								Booking booking = new Booking();
								booking.setUserid(request.getUserId());
								booking.setHotelid(request.getHotelId());
								booking.setRoomid(request.getRoomId());
								booking.setRoomtype(hotelRoomDto.getRooms().get(0).getRoomType());
								booking.setCheckindate(request.getCheckindate());
								booking.setCheckoutdate(request.getCheckoutdate());
								booking.setBookingStatus("Room already booked or Not Available");
								saveBooking(booking);
							}
						} else
							bookingResponse = createErrorResponse("Rooms are Empty");
					}
				} catch (Exception e) {
					bookingResponse = createErrorResponse("Hotel / Room does not exist");

					Booking booking = new Booking();
					booking.setUserid(request.getUserId());
					booking.setHotelid(request.getHotelId());
					booking.setRoomid(request.getRoomId());
					booking.setCheckindate(request.getCheckindate());
					booking.setCheckoutdate(request.getCheckoutdate());
					booking.setBookingStatus("Hotel / Room does not exist");
					saveBooking(booking);
				}
			}
		} catch (Exception e) {
			bookingResponse = createErrorResponse("User does not exist");

			Booking booking = new Booking();
			booking.setUserid(request.getUserId());
			booking.setHotelid(request.getHotelId());
			booking.setRoomid(request.getRoomId());
			booking.setCheckindate(request.getCheckindate());
			booking.setCheckoutdate(request.getCheckoutdate());
			booking.setBookingStatus("User does not exist");
			saveBooking(booking);
		}

		return bookingResponse;
	}

	private BookingResponse createBookingResponse(BookingRequest request, HotelRoomDto hotel, UserDto userDto,
			PaymentDto paymentDto, String status) {

		BookingResponse bookingResponse = new BookingResponse();
		bookingResponse.setUserName(userDto.getName());
		bookingResponse.setUserEmail(userDto.getEmail());
		bookingResponse.setHotelName(hotel.getName());
		bookingResponse.setHotelLocation(hotel.getLocation());
		bookingResponse.setRoomType(hotel.getRooms().get(0).getRoomType());
		bookingResponse.setRoomPrice(hotel.getRooms().get(0).getRoomPrice());
		bookingResponse.setCheckindate(request.getCheckindate());
		bookingResponse.setCheckoutdate(request.getCheckoutdate());
		bookingResponse.setTotalAmount(paymentDto.getAmount());
		bookingResponse.setPaymentId(paymentDto.getId());
		bookingResponse.setPaymentStatus(paymentDto.getStatus());
		bookingResponse.setBookingStatus(status);
		return bookingResponse;
	}

	private BookingResponse createErrorResponse(String status) {

		BookingResponse bookingResponse = new BookingResponse();
		bookingResponse.setBookingStatus(status);
		return bookingResponse;
	}

	public BookingResponse compensateFailedRoomConfirmation(BookingRequest request, HotelRoomDto hotelRoomDto, UserDto userDto, PaymentDto paymentDto, int noOfDays, int totalAmount) {
		
		boolean refunded = false;
		try {
			paymentBookingFeignClient.refundPaymentByPaymentId(paymentDto.getId());
			refunded = true;
		} catch (Exception refundFailure) {

		}

		boolean roomReleased = false;
		try {
			hotelBookingFeignClient.updateRoomStatusByHotelRoomId(request.getHotelId(), request.getRoomId(), "AVAILABLE");
			roomReleased = true;
		} catch (Exception releaseFailure) {
			
		}

		String status = "BOOKING FAILED - ROOM CONFIRMATION ERROR"
						+ (refunded ? ", PAYMENT REFUNDED" : ", REFUND FAILED - MANUAL ACTION NEEDED")
						+ (roomReleased ? ", ROOM RELEASED" : ", ROOM RELEASE FAILED - MANUAL ACTION NEEDED");

		Booking booking = new Booking();
		booking.setUserid(request.getUserId());
		booking.setHotelid(request.getHotelId());
		booking.setRoomid(request.getRoomId());
		booking.setPaymentid(paymentDto.getId());
		booking.setRoomtype(hotelRoomDto.getRooms().get(0).getRoomType());
		booking.setNoOfDays(noOfDays);
		booking.setAmount(totalAmount);
		booking.setCheckindate(request.getCheckindate());
		booking.setCheckoutdate(request.getCheckoutdate());
		booking.setBookingStatus(status);
		saveBooking(booking);

		return createBookingResponse(request, hotelRoomDto, userDto, paymentDto, status);
	}

	public Booking cancelBooking(int id) {
		Booking booking = getBookingById(id);
		if (booking != null) {
			int hotelId = booking.getHotelid();
			int roomId = booking.getRoomid();
			int paymentId = booking.getPaymentid();
			String bookingStatus = booking.getBookingStatus();

			if (bookingStatus.equalsIgnoreCase("ROOM BOOKED")) {
				hotelBookingFeignClient.updateRoomStatusByHotelRoomId(hotelId, roomId, "AVAILABLE");

				paymentBookingFeignClient.refundPaymentByPaymentId(paymentId);

				booking.setBookingStatus("Booking Cancelled And Payment Refunded");
			} else
				booking.setBookingStatus("No Reservations done in given Booking ID");
			booking = saveBooking(booking);
		}

		return booking;
	}

}
