package com.walmart.ticket.init;

import java.util.Scanner;

import com.walmart.ticket.model.SeatHold;
import com.walmart.ticket.model.Venue;
import com.walmart.ticket.service.TicketService;
import com.walmart.ticket.service.Impl.TicketServiceImpl;
import com.walmart.ticket.utils.IOvalidator;

public class Start {
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("\t\t\t Ticket Application");
		System.out.println("\n Best seat is Consider as Left from Top");
		
		boolean loop = true;
		String options = "\n......Select your Option...... \n\n1. See Available Seats \n2. Hold Seats \n3. Reserve Seat \n4. Close.";
		int rows = 10;
		int seatsProw = 10;
		Venue v = new Venue(rows, seatsProw);
		TicketService service = new TicketServiceImpl(v);
		System.out.println("Venue consist of "+ rows + " rows & " + seatsProw + " seats per row (After 100 secs. Hold seats are Expired ))");
		while(loop) {
			System.out.println(options);
			String str = sc.next();
			boolean isvalidInput = IOvalidator.isValidNo(str);
			if(!isvalidInput){
				System.out.println("Select only numbers.");
				continue;
			}
			int input = Integer.parseInt(str);
			switch(input){
			case 1:
				System.out.println("\nTotal No. of seats available: " + service.numSeatsAvailable());
				break;
			case 2:
				System.out.println("No. of seats for hold?");
				String xs = sc.next();
				boolean isvalidSeat = IOvalidator.isValidNo(xs);
				if(!isvalidSeat){
					while(!isvalidSeat){
						System.out.println("Invalid seat no.");
						System.out.println("Enter valid no:");
						xs = sc.next();
						isvalidSeat = IOvalidator.isValidNo(xs);
					}
				}
				int seats = Integer.parseInt(xs);
				System.out.println("User email?");
				String email = sc.next();
				SeatHold hold = service.findAndHoldSeats(seats, email);
				if(hold!=null){
					System.out.println("\n" + seats + " held!\n" + hold);
				}else{
					System.out.println("\nYour request has been failed! Please try again!");
				}
				break;
			case 3:
				System.out.println("Your SeatHold Id?");
				String x = sc.next();
				boolean isvalidDigit = IOvalidator.isValidNo(x);
				if(!isvalidDigit){
					while(!isvalidDigit){
						System.out.println("Invalid no pattern.");
						System.out.println("Enter valid no:");
						x = sc.next();
						isvalidDigit = IOvalidator.isValidNo(x);
					}
				}
				int id = Integer.parseInt(x);
				System.out.println("Associated with which customer email?");
				String cust = sc.next();
				System.out.println("\n" + service.reserveSeats(id, cust));
				break;
			case 4:
				loop = false;
				System.out.println("\nThank you!");
				break;
			default:
				System.out.println("Select a valid option.");
			}
		}
		sc.close();
		
	}

}
