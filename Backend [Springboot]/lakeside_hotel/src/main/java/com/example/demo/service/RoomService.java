package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Room;

public interface RoomService {
	Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice);
}
