package com.walmart.ticket.utils;

import java.util.UUID;


import com.walmart.ticket.model.Seat;
import com.walmart.ticket.model.SeatHold;


public class IOvalidator {
		
	public static boolean isValidNo(String no) {
		if(no == null){
			return false;
		}
		try {
			Integer.parseInt(no);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	public static boolean validateCustomer(String input_customer, String stored_customer){
		if(input_customer == null || stored_customer == null){
			return false;
		}
		return input_customer.equalsIgnoreCase(stored_customer);
	}
	public static String reservationCode(SeatHold hold){
		StringBuilder sb = new StringBuilder();
		sb.append("Congrats! Your seats have been reserved!\n");
		sb.append("Info:\n");
		sb.append("Confirmation no: " + UUID.randomUUID().toString() + "\n");
		sb.append("seats: [ ");
		for(Seat st: hold.getSeatsHeld()){
			sb.append(st.getSeatNo()); sb.append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
}
