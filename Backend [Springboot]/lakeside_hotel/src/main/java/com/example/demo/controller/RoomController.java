package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Room;
import com.example.demo.response.RoomResponse;
import com.example.demo.service.RoomService;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:9091")
public class RoomController {
	private RoomService roomService;

	public RoomController(RoomService roomService) {
		super();
		this.roomService = roomService;
	}

	@PostMapping("/room/add/new-room")
	public ResponseEntity<RoomResponse> addNewRoom(
			@RequestParam("photo") MultipartFile photo,
			@RequestParam("room_type") String roomType,
			@RequestParam("room_price") BigDecimal roomPrice
		) {
		Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
		RoomResponse roomResponse = new RoomResponse(
				savedRoom.getId(),
				savedRoom.getRoomType(),
				savedRoom.getRoomPrice()
				);
		return new ResponseEntity<RoomResponse>(roomResponse, HttpStatus.CREATED);
	}
	
	@GetMapping("/room/room-types")
	public List<String> getRoomType() {
		return roomService.getAllRoomTypes();
	}
}
