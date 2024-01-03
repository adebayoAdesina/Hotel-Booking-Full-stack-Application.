package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.BookedRoom;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long>{

	List<BookedRoom> findByRoomId(Long id);
	BookedRoom findByBookingConfirmationCode(String confirmationCode);
}
