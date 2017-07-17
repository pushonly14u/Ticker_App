package com.walmart.ticket.service;

import com.walmart.ticket.model.SeatHold;

public interface TicketService {
	int numSeatsAvailable();
	
	SeatHold findAndHoldSeats(int numSeats, String customerEmail);
	
	String reserveSeats(int seatHoldId, String customerEmail);
}
