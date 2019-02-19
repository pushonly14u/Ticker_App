package com.walmart.ticket.service.Impl;

import java.time.Instant;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.walmart.ticket.model.User;
import com.walmart.ticket.model.STATUS;
import com.walmart.ticket.model.Seat;
import com.walmart.ticket.model.SeatHold;
import com.walmart.ticket.model.Venue;
import com.walmart.ticket.service.TicketService;
import com.walmart.ticket.utils.IOvalidator;

public class TicketServiceImpl implements TicketService {
	private int available;
	private Venue v;
	private Map<Integer, SeatHold> seatHoldMapper;
	private long seconds = 100L;

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
		for (Iterator<Map.Entry<Integer, SeatHold>> it = seatHoldMapper.entrySet().iterator(); it.hasNext();) {
			Map.Entry<Integer, SeatHold> entry = it.next();
			SeatHold tempSH = entry.getValue();
			long now = Instant.now().getEpochSecond();
			if ((now - tempSH.getCreatedAt().getEpochSecond()) > this.seconds) {
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
		hold.setCustomer(new User(customerEmail));
		hold.setSeatsHeld(holdingSeats);
		hold.setCreatedAt(Instant.now());
		
		return hold;
	}
	
	private List<Seat> findGoodSeats(int numSeats){
		if(this.available < numSeats){
			System.out.println(this.available + " seats left!");
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
			System.out.println("SeatHoldId is invalid or it may expired! ");
			return null;
		}
		boolean isValidCustomer = IOvalidator.validateCustomer(customerEmail, seatHold.getCustomer().getEmail());
		if(!isValidCustomer){
			return "cannot verify customer. Please request reservation with correct customer email.";
		}
		updateStatus(seatHold.getSeatsHeld(), STATUS.RESERVED);
		String result =  IOvalidator.reservationCode(seatHold);
		seatHoldMapper.remove(seatHoldId);
		return result;
	}
	
	private SeatHold finder(int seatHoldId){
		return seatHoldMapper.get(seatHoldId);
	}
}
