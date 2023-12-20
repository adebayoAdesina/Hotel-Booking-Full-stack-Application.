package com.example.demo.response;

import java.math.BigDecimal;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomResponse {
	private Long id;
	private String roomType;
	private BigDecimal roomPrice;
	private boolean isBooked;
	private String photo;
	private List<BookingResponse> bookingsBooked;
	public RoomResponse(Long id, String roomType, BigDecimal roomPrice) {
		super();
		this.id = id;
		this.roomType = roomType;
		this.roomPrice = roomPrice;
	}
	public RoomResponse(Long id, String roomType, BigDecimal roomPrice, boolean isBooked, byte[] photoBytes,
			List<BookingResponse> bookingsBooked) {
		super();
		this.id = id;
		this.roomType = roomType;
		this.roomPrice = roomPrice;
		this.isBooked = isBooked;
		this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
		this.bookingsBooked = bookingsBooked;
	}
	
}
