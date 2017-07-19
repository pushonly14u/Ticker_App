package com.walmart.ticket.service.Impl;

import java.time.Instant;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.walmart.ticket.model.Customer;
import com.walmart.ticket.model.Loc;
import com.walmart.ticket.model.STATUS;
import com.walmart.ticket.model.Seat;
import com.walmart.ticket.model.SeatHold;
import com.walmart.ticket.model.Venue;
import com.walmart.ticket.service.TicketService;

public class TicketServiceImpl implements TicketService {
	private int available;
	private Venue v;
	private Map<Integer, SeatHold> seatHoldMapper;
	private long seconds = 5L;

	public TicketServiceImpl(Venue v) {
		super();
		this.v = v;
		this.available = v.getCapacity();
		seatHoldMapper = new TreeMap<Integer, SeatHold>();
	}
	
	public TicketServiceImpl(Venue v, long secs) {
		this(v);
		this.seconds = secs;
	}

	public int numSeatsAvailable() {
		expiryCheck();
		System.out.println(v.prettyPrint());
		return available;
	}

	private void expiryCheck() {
		for (Iterator<Map.Entry<Integer, SeatHold>> it = seatHoldMapper.entrySet().iterator(); it
				.hasNext();) {
			Map.Entry<Integer, SeatHold> entry = it.next();
			SeatHold tempSH = entry.getValue();
			long now = Instant.now().getEpochSecond();
			if((now - tempSH.getCreatedAt().getEpochSecond())> this.seconds){
				System.out.println("\t()now = " + now + " sec.");
				System.out.println("\t()Created at = " + tempSH.getCreatedAt().getEpochSecond() + " sec.");
				updateStatus(tempSH.getSeatsHeld(), STATUS.AVAILABLE);
				this.available += tempSH.getSeatsHeld().size();
				it.remove();
			}
		}
	}
	
	private void expiryCheck(int seatHoldId) {
		SeatHold tempSH = seatHoldMapper.get(seatHoldId);
		if(tempSH!=null){
			long now = Instant.now().getEpochSecond();
			if((now - tempSH.getCreatedAt().getEpochSecond())> this.seconds){
				System.out.println("\tnow = " + now + " sec.");
				System.out.println("\tCreated at = " + tempSH.getCreatedAt().getEpochSecond() + " sec.");
				updateStatus(tempSH.getSeatsHeld(), STATUS.AVAILABLE);
				this.available += tempSH.getSeatsHeld().size();
				seatHoldMapper.remove(seatHoldId);
			}
		}
	}

	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		expiryCheck();
		List<Seat> holdingSeats = findGoodSeats(numSeats);
		updateStatus(holdingSeats, STATUS.HOLD);
		this.available -= holdingSeats.size();
		SeatHold hold = generateSeatHold(holdingSeats, customerEmail);
		if(hold!=null)seatHoldMapper.put(hold.getId(), hold);
		return hold;
	}

	private void updateStatus(List<Seat> seats, STATUS status){
		for(Seat st: seats){
			st.setStatus(status);
		}
	}
	
	private SeatHold generateSeatHold(List<Seat> holdingSeats, String customerEmail){
		if(holdingSeats.size()<1){
			return null;
		}
		SeatHold hold = new SeatHold();
		hold.setCustomer(new Customer(customerEmail));
		hold.setSeatsHeld(holdingSeats);
		hold.setCreatedAt(Instant.now());
		
		return hold;
	}
	
	private List<Seat> findGoodSeats(int numSeats){
		if(this.available < numSeats){
			return new LinkedList<Seat>(); 
		}
		Seat[][] seats = v.getSeats();
		List<Seat> storeSeats = new LinkedList<Seat>();
		boolean breakFlag = false;
		for(int i=0; i<v.getRows(); i++){
			if(breakFlag){
				break;
			}
			for(int j=0; j<v.getSeatsPerRow(); j++){
				Seat st = seats[i][j];
				if(STATUS.AVAILABLE == st.getStatus()){
					storeSeats.add(st);
					if(--numSeats == 0){
						breakFlag = true;
						break;
					}
				}
			}
		}
		return storeSeats;
	}
	public String reserveSeats(int seatHoldId, String customerEmail) {
		expiryCheck(seatHoldId);
		SeatHold seatHold = finder(seatHoldId);
		if(seatHold == null){
			System.out.println("\t\t Reserve: seatHold is null ");
			return null;
		}
		updateStatus(seatHold.getSeatsHeld(), STATUS.RESERVED);
//		this.available -= seatHold.getSeatsHeld().size();
		seatHoldMapper.remove(seatHoldId);
		return "Reserved!";
	}
	
	private SeatHold finder(int seatHoldId){
		return seatHoldMapper.get(seatHoldId);
	}
}
