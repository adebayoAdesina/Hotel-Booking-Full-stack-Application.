package com.example.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.PhotoRetrievalException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.BookedRoom;
import com.example.demo.model.Room;
import com.example.demo.response.BookingResponse;
import com.example.demo.response.RoomResponse;
import com.example.demo.service.BookedRoomService;
import com.example.demo.service.RoomService;

@RestController
@RequestMapping("api/v1")
@CrossOrigin
public class RoomController {
	private final RoomService roomService;
	private final BookedRoomService  bookedRoomService;

	public RoomController(RoomService roomService, BookedRoomService bookedRoomService) {
		super();
		this.roomService = roomService;
		this.bookedRoomService = bookedRoomService;
	}

	@PostMapping("room/add/new-room")
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
	
	@GetMapping("rooms/room-types")
	public List<String> getRoomType() {
		return roomService.getAllRoomTypes();
	}
	
	@GetMapping("rooms/all-rooms")
	public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
		List<Room> rooms = roomService.getAllRoom();
		List<RoomResponse> roomResponses = new ArrayList<>();
		for (Room room : rooms) {
			byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
			if(photoBytes != null && photoBytes.length > 0) {
				String base64Photo = org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(photoBytes);
				RoomResponse roomResponse = getRoomResponse(room);
				roomResponse.setPhoto(base64Photo);
				roomResponses.add(roomResponse);
			}
		}
		return new ResponseEntity<List<RoomResponse>>(roomResponses, HttpStatus.OK);
	}
	
	@DeleteMapping("rooms/delete/room/{roomId}")
	public ResponseEntity<Void> deleteRoom(@PathVariable("roomId") Long roomId) {
		roomService.deleteRoom(roomId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("rooms/update/{roomId}")
	public ResponseEntity<RoomResponse> updateRoom(
			@PathVariable Long roomId, 
			@RequestParam(required = false, name = "room_type") String  roomType, 
			@RequestParam(required = false, name = "room_price") BigDecimal roomPrice, 
			@RequestParam(required = false) MultipartFile photo
			) throws IOException, SQLException{
		byte[] photobyte = photo != null && !photo.isEmpty() ? photo.getBytes() : roomService.getRoomPhotoByRoomId(roomId);
		Blob photoBlob = photobyte != null && photobyte.length > 0 ? new SerialBlob(photobyte) : null;		
		Room theRoom = roomService.updateRoom(roomId, roomType, roomPrice, photobyte);
		theRoom.setPhoto(photoBlob);
		RoomResponse roomResponse = getRoomResponse(theRoom);

		return new ResponseEntity<RoomResponse>(roomResponse, HttpStatus.OK);
		
	}
	
	@GetMapping("rooms/room/{roomId}")
	public ResponseEntity<Optional<RoomResponse>>getRoomById(@PathVariable Long roomId) {
		Optional<Room> theRoom = roomService.getRoomId(roomId); 
		return theRoom.map(room -> {
			RoomResponse roomResponse = getRoomResponse(room);
			return ResponseEntity.ok(Optional.of(roomResponse));
		}).orElseThrow(()-> new ResourceNotFoundException("Room not found"));
	}
	
	private RoomResponse getRoomResponse(Room room) {
		List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
//		List<BookingResponse> bookingInfo = bookings
//				.stream(
//						).map(booking ->  
//						new BookingResponse(
//								booking.getBookingId(), 
//								booking.getCheckInDate(), 
//								booking.getCheckOutDate(), 
//								booking.getBookingConfirmationCode())
//						).toList();
		byte[] photoBytes = null;
		Blob photoBlob = room.getPhoto();
		if (photoBlob != null) {
			try {
				photoBytes = photoBlob.getBytes(1,(int) photoBlob.length());
			} catch (SQLException e) {
				// TODO: handle exception
				throw new PhotoRetrievalException("Error retrieving photo");
			}
		}
		return new RoomResponse(
				room.getId(), 
				room.getRoomType(), 
				room.getRoomPrice(), 
				room.isBooked(), 
				photoBytes
				);
	}

	private List<BookedRoom> getAllBookingsByRoomId(Long id) {
		// TODO Auto-generated method stub
		return bookedRoomService.getAllBookingsByRoomId(id);
	}
}
