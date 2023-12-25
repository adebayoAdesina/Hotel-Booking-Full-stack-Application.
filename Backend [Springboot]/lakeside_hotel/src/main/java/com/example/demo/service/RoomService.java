package com.example.demo.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Room;

public interface RoomService {
	public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice);
	public List<String> getAllRoomTypes();
}
