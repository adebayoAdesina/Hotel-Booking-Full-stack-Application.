package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.BookedRoom;
import com.example.demo.repository.BookedRoomRepository;
import com.example.demo.service.BookedRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookedRoomServiceImpl  implements BookedRoomService{

	private final BookedRoomRepository bookedRoomRepository;
	
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
		return null;
	}

	@Override
	public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
