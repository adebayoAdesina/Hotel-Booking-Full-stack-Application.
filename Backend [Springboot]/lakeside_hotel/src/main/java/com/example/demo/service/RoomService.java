package com.example.demo.service;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Room;

public interface RoomService {
	public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice);
	public List<String> getAllRoomTypes();
	public List<Room> getAllRoom();
	public byte[] getRoomPhotoByRoomId(Long id) throws SQLException;
	public void deleteRoom(Long roomId);
	public Optional<Room> getRoomId(Long roomId);
	public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photobyte);
}
