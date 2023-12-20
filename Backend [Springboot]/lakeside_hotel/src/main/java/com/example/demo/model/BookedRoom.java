package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "booked_room")
public class BookedRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private Long bookingId;
	@Column(name = "check_in_date")
	private LocalDate checkInDate;
	@Column(name = "check_out_date")
	private LocalDate checkOutDate;
	@Column(name = "guest_full_name")
	private String guestFullName;
	@Column(name = "guest_email")
	private String guestEmail;
	@Column(name = "number_of_adults")
	private int numberOfAdults;
	@Column(name = "number_of_children")
	private int numberOfChildren;
	@Column(name = "totol_number_of_guest")
	private int totalNumberOfGuest;
	@Column(name = "booking_confirmation_code")
	private String bookingConfirmationCode;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;
	
	public void calculateNumberOfGuest() {
		this.totalNumberOfGuest = this.numberOfAdults + this.numberOfChildren;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
//		calculateNumberOfGuest();
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
//		calculateNumberOfGuest();
	}
	
	
}
