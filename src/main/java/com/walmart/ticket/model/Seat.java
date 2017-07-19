package com.walmart.ticket.model;
/**
 * 
 * @author Darpan Shah
 *
 */
public class Seat {
	Loc seatNo;
	Customer reservedBy;
	STATUS status;
	
	public Seat(Loc seatNo) {
		super();
		this.seatNo = seatNo;
	}
	
	public Seat(Loc seatNo, STATUS status) {
		this(seatNo);
		this.status = status;
	}

	public Loc getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(Loc seatNo) {
		this.seatNo = seatNo;
	}
	public Customer getReservedBy() {
		return reservedBy;
	}
	public void setReservedBy(Customer reservedBy) {
		this.reservedBy = reservedBy;
	}
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS status) {
		this.status = status;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Seat [");
		if (seatNo != null)
			builder.append("seatNo=").append(seatNo).append(", ");
		if (reservedBy != null)
			builder.append("reservedBy=").append(reservedBy).append(", ");
		if (status != null)
			builder.append("status=").append(status);
		builder.append("]");
		return builder.toString();
	}
	
	
}
