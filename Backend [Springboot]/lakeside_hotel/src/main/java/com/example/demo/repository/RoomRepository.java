package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	
	@Query(value = "SELECT  room_type FROM room",  nativeQuery = true)
	List<String>findDistinctRoomTypes();
}
