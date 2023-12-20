package com.example.demo.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService{
	private RoomRepository roomRepository;

	@Override
	public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) {
		// TODO Auto-generated method stub
		Room  room = new Room();
		
		room.setRoomPrice(roomPrice);
		room.setRoomType(roomType);
		if(!photo.isEmpty()) {
			byte[] photoBytes;
			Blob photoBlob;
			try {
				photoBytes = photo.getBytes();
				try {
					photoBlob = new SerialBlob(photoBytes);
					room.setPhoto(photoBlob);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return roomRepository.save(room);
	}

}
