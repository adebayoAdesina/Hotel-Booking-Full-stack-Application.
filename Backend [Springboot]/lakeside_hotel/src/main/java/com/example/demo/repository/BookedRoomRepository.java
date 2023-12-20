package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.BookedRoom;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long>{

}
