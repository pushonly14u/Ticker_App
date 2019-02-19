package com.walmart.ticket.model;

import java.time.Instant;
import java.util.List;

public class SeatHold {
	
	private List<Seat> seatsHeld;
	private static int counter;
	private int id;
	private User user;
	private Instant createdAt;
	static {
		counter = 0;
	}
	public SeatHold() {
		this.id = ++counter;
	}
	
	public static int getCounter() {
		return counter;
	}
	
	public static void setCounter(int counter) {
		SeatHold.counter = counter;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public User getCustomer() {
		return user;
	}
	
	public void setCustomer(User user) {
		this.user = user;
	}
	
	public Instant getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	
	public List<Seat> getSeatsHeld() {
		return seatsHeld;
	}
	
	public void setSeatsHeld(List<Seat> seatsHeld) {
		this.seatsHeld = seatsHeld;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeatHold [").append(id).append(", ");
		if (user != null)
			builder.append(user).append(", ");
		if (createdAt != null)
			builder.append(createdAt).append(", ");
		if (seatsHeld != null){
			builder.append(seatsHeld.size() + " seats held: [");
			for(Seat st: seatsHeld){
				builder.append(st.getSeatNo()); builder.append(" ");
			}
			builder.append("]");
		}
		builder.append("]");
		return builder.toString();
	}
	
	
}
