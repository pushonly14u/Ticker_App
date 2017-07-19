package com.walmart.ticket;

import java.util.Scanner;

import com.walmart.ticket.model.SeatHold;
import com.walmart.ticket.model.Venue;
import com.walmart.ticket.service.TicketService;
import com.walmart.ticket.service.Impl.TicketServiceImpl;
import com.walmart.ticket.utils.Helper;

public class Start {
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("\t\t\t TicketService System");
		System.out.println("\t\t\t ====================");
		boolean loop = true;
		String options = "\nOptions: \t1. start/reset \t2. Available Seats \t3. Request for Hold \t4. Reserve/commit \t5. Exit.";
		int rows = 5;
		int seatsProw = 5;
		Venue v = new Venue(rows, seatsProw);
		TicketService service = new TicketServiceImpl(v);
		System.out.println("System started with "+ rows + " rows & " + seatsProw + " seats per row venue! (Expiration seconds is set to 100 secs.))");
		while(loop) {
			System.out.println(options);
			String str = sc.next();
			boolean isvalidInput = Helper.isValidNo(str);
			if(!isvalidInput){
				System.out.println("Select only numbers.");
				continue;
			}
			int input = Integer.parseInt(str);
			switch(input){
			case 1:
				System.out.println("How many rows?");
				String xr = sc.next();
				boolean isvalidRow = Helper.isValidNo(xr);
				if(!isvalidRow){
					while(!isvalidRow){
						System.out.println("Invalid row no.");
						System.out.println("Enter valid no:");
						xr = sc.next();
						isvalidRow = Helper.isValidNo(xr);
					}
				}
				rows = Integer.parseInt(xr);
				System.out.println("How many seats per rows?");
				String xst = sc.next();
				boolean isvalidStPRw = Helper.isValidNo(xst);
				if(!isvalidStPRw){
					while(!isvalidStPRw){
						System.out.println("Invalid seat no.");
						System.out.println("Enter valid no:");
						xst = sc.next();
						isvalidStPRw = Helper.isValidNo(xst);
					}
				}
				seatsProw = Integer.parseInt(xst);
				System.out.println("Expiration seconds (Optional: Hit enter to skip)");
				int exp;
				try {
					exp = Integer.parseInt(sc.next());
				} catch (NumberFormatException e) {
					exp = -1;
				}
				v = new Venue(rows, seatsProw);
				service = (exp==-1)?new TicketServiceImpl(v):new TicketServiceImpl(v, exp);
				System.gc();
				break;
			case 2:
				System.out.println("No of seats available now: " + service.numSeatsAvailable());
				break;
			case 3:
				System.out.println("How many seats for hold?");
				String xs = sc.next();
				boolean isvalidSeat = Helper.isValidNo(xs);
				if(!isvalidSeat){
					while(!isvalidSeat){
						System.out.println("Invalid seat no.");
						System.out.println("Enter valid no:");
						xs = sc.next();
						isvalidSeat = Helper.isValidNo(xs);
					}
				}
				int seats = Integer.parseInt(xs);
				System.out.println("Customer email?");
				String email = sc.next();
				boolean isvalid = Helper.isValidEmail(email);
				if(!isvalid){
					while(!isvalid){
						System.out.println("Invalid email pattern.");
						System.out.println("Enter valid email:");
						email = sc.next();
						isvalid = Helper.isValidEmail(email);
					}
				}
				SeatHold hold = service.findAndHoldSeats(seats, email);
				if(hold!=null){
					System.out.println("" + seats + " held!\n" + hold);
				}else{
					System.out.println("Your request has been failed! Please try again!");
				}
				break;
			case 4:
				System.out.println("SeatHold Id?");
				String x = sc.next();
				boolean isvalidDigit = Helper.isValidNo(x);
				if(!isvalidDigit){
					while(!isvalidDigit){
						System.out.println("Invalid no pattern.");
						System.out.println("Enter valid no:");
						x = sc.next();
						isvalidDigit = Helper.isValidNo(x);
					}
				}
				int id = Integer.parseInt(x);
				System.out.println("Associated with which customer email?");
				String cust = sc.next();
				boolean isvalidEmail = Helper.isValidEmail(cust);
				if(!isvalidEmail){
					while(!isvalidEmail){
						System.out.println("Invalid email pattern.");
						System.out.println("Enter valid email:");
						cust = sc.next();
						isvalidEmail = Helper.isValidEmail(cust);
					}
				}
				System.out.println(service.reserveSeats(id, cust));
				break;
			case 5:
				loop = false;
				System.out.println("Bye!");
				break;
			default:
				System.out.println("Invalid option.");
			}
		}
		sc.close();
		
		/*
		TicketService service1 = new TicketServiceImpl(v);
		log.info(""+service1.numSeatsAvailable());
		log.info(""+service1.findAndHoldSeats(4, "darpan@dp.com"));
		log.info(""+service1.numSeatsAvailable());
		log.info(""+service1.findAndHoldSeats(12, "darpan@dp.com"));
		log.info(""+service1.numSeatsAvailable());
		Thread.sleep(6000); // default seconds is 100, change it in less to test locally.
		log.info("First: " + service1.numSeatsAvailable());
		log.info(""+service1.findAndHoldSeats(22, "darpan@dp.com"));
		log.info("After 1 seat holding: " + service1.numSeatsAvailable());
		
		log.info(service1.reserveSeats(3, "darpan@dp.com"));
		log.info("Reserving held seats: " + service1.numSeatsAvailable());
		//log.info(service1.reserveSeats(2, "darpan@dp.com"));
		log.info(""+service1.numSeatsAvailable());
		*/
	}

}
