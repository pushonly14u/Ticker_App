package com.walmart.ticket;

import com.walmart.ticket.model.Venue;
import com.walmart.ticket.service.TicketService;
import com.walmart.ticket.service.Impl.TicketServiceImpl;

public class Start {

	public static void main(String[] args) throws InterruptedException {
		int rows = 4;
		int seatsProw = 4;
		Venue v = new Venue(rows, seatsProw);
		TicketService service = new TicketServiceImpl(v);
		System.out.println(service.numSeatsAvailable());
		System.out.println(service.findAndHoldSeats(4, "darpan@dp.com"));
		//Thread.sleep(6000);
		System.out.println(service.reserveSeats(1, "darpan@dp.com"));
	}

}
