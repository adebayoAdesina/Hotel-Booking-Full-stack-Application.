package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.InvalidBookingRequestException;
import com.example.demo.model.BookedRoom;
import com.example.demo.model.Room;
import com.example.demo.repository.BookedRoomRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.BookedRoomService;
import com.example.demo.service.RoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookedRoomServiceImpl  implements BookedRoomService{

	private final BookedRoomRepository bookedRoomRepository;
	private final RoomService roomService;
	
	@Override
	public List<BookedRoom> getAllBookings() {
		// TODO Auto-generated method stub
		return bookedRoomRepository.findAll();
	}
	@Override
	public List<BookedRoom> getAllBookingsByRoomId(Long id) {
		// TODO Auto-generated method stub
		return bookedRoomRepository.findByRoomId(id);
	}

	@Override
	public void cancelBooking(Long bookingId) {
		// TODO Auto-generated method stub
		bookedRoomRepository.deleteById(bookingId);
	}

	@Override
	public String saveBooking(Long roomId, BookedRoom bookingRequest) {
		// TODO Auto-generated method stub
		if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
			throw new InvalidBookingRequestException("Check-in date must come before check-out date");
		}
		Room room = roomService.getRoomId(roomId).get();
		List<BookedRoom> existingBookings = room.getBookings();
		boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
		if (roomIsAvailable) {
			room.addBooking(bookingRequest);
			bookedRoomRepository.save(bookingRequest);
		} else {
			throw new InvalidBookingRequestException("Sorry, This room has been booked for the selected dates;");
		}
		return bookingRequest.getBookingConfirmationCode();
	}

	private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
		// TODO Auto-generated method stub
		return existingBookings.stream().noneMatch(existingBooking -> 
					bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
						|| bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckInDate())
						|| (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
						&& 	bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
								
						&& 	bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
								
						&& 	bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))
						
						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
						&& 	bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))
						
						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
						&& 	bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
				);
	}
	@Override
	public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
		// TODO Auto-generated method stub
		return bookedRoomRepository.findByBookingConfirmationCode(confirmationCode);
	}

}
