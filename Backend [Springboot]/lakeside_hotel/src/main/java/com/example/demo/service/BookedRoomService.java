package com.example.demo.service;

import java.util.List;

import com.example.demo.model.BookedRoom;

public interface BookedRoomService {

	List<BookedRoom> getAllBookingsByRoomId(Long id);

	void cancelBooking(Long bookingId);

	String saveBooking(Long roomId, BookedRoom bookingRequest);

	BookedRoom findByBookingConfirmationCode(String confirmationCode);

	List<BookedRoom> getAllBookings();

}
