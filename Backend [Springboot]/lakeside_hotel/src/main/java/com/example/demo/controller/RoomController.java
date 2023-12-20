package com.example.demo.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Room;
import com.example.demo.response.RoomResponse;
import com.example.demo.service.impl.RoomServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(name = "api/v1/room")
@RequiredArgsConstructor
public class RoomController {
	private RoomServiceImpl roomServiceImpl;

	@PostMapping("/add/new-room")
	public ResponseEntity<RoomResponse> addNewRoom(
			@RequestParam("photo") MultipartFile photo,
			@RequestParam("room_type") String roomType,
			@RequestParam("room_price") BigDecimal roomPrice
		) {
		Room savedRoom = roomServiceImpl.addNewRoom(photo, roomType, roomPrice);
		RoomResponse roomResponse = new RoomResponse(
				savedRoom.getId(),
				savedRoom.getRoomType(),
				savedRoom.getRoomPrice()
				);
		return new ResponseEntity<RoomResponse>(roomResponse, HttpStatus.CREATED);
	}
}
