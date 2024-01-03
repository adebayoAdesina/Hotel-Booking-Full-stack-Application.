 package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.InvalidBookingRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.BookedRoom;
import com.example.demo.model.Room;
import com.example.demo.response.BookingResponse;
import com.example.demo.response.RoomResponse;
import com.example.demo.service.BookedRoomService;
import com.example.demo.service.RoomService;
import com.example.demo.service.impl.BookedRoomServiceImpl;
import com.example.demo.service.impl.RoomServiceImpl;

@RestController
@RequestMapping("api/v1/bookings")
@CrossOrigin
public class BookedRoomController {
	private final BookedRoomService bookedRoomService;
	private final RoomService roomService;
	
	
	public BookedRoomController(BookedRoomService bookedRoomService, RoomService roomService) {
		super();
		this.bookedRoomService = bookedRoomService;
		this.roomService = roomService;
	}

@GetMapping("all-bookings")
	public ResponseEntity<List<BookingResponse>> getAllBookings() {
		List<BookedRoom> bookings = bookedRoomService.getAllBookings();
		List<BookingResponse> bookingResponses = new ArrayList<>();
		for(BookedRoom booking : bookings) {
			BookingResponse bookingResponse = getBookingResponse(booking);
			bookingResponses.add(bookingResponse);
		}
		return new ResponseEntity<List<BookingResponse>>(bookingResponses, HttpStatus.OK);
		
	}



	@GetMapping("/confirmation/{confirmationCode}")
	public ResponseEntity<?>getBookingsByConfirmationCode(String confirmationCode) {
		try {
			BookedRoom booking = bookedRoomService.findByBookingConfirmationCode(confirmationCode);
			BookingResponse bookingResponse = getBookingResponse(booking);
			return ResponseEntity.ok(bookingResponse);
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping("/room/{roomId}/booking")
	public ResponseEntity<?> saveBooking(@PathVariable Long roomId, @RequestBody BookedRoom bookingRequest) {
		try {
			String confirmationCodeString = bookedRoomService.saveBooking(roomId, bookingRequest);
			return ResponseEntity.ok("Room booked successfully ! Your booking confirmation code is :" + confirmationCodeString);
		} catch (InvalidBookingRequestException e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}
	
	@DeleteMapping("/booking/{bookingId}/delete")
	public void cancelBooking(Long bookingId) {
		bookedRoomService.cancelBooking(bookingId);
	}
	
	private BookingResponse getBookingResponse(BookedRoom booking) {
	Room theRoom = roomService.getRoomId(booking.getRoom().getId()).get();
	RoomResponse room = new RoomResponse(theRoom.getId(), theRoom.getRoomType(), theRoom.getRoomPrice());
	
	return new BookingResponse(
			booking.getBookingId(), 
			booking.getCheckInDate(),
			booking.getCheckOutDate(),
			booking.getGuestFullName(),
			booking.getGuestEmail(),
			booking.getNumberOfAdults(),
			booking.getNumberOfChildren(),
			booking.getTotalNumberOfGuest(),
			booking.getBookingConfirmationCode(),
			room
			);
}
}
