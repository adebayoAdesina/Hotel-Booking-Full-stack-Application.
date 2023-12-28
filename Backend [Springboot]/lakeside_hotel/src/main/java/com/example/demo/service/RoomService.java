package com.example.demo.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Room;

public interface RoomService {
	public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice);
	public List<String> getAllRoomTypes();
	public List<Room> getAllRoom();
	public byte[] getRoomPhotoByRoomId(Long id) throws SQLException;
	public void deleteRoom(Long roomId);
}
