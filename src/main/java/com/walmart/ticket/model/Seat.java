package com.walmart.ticket.model;

public class Seat {
	SeatLocation seatNo;
	User reservedBy;
	STATUS status;
	
	public Seat(SeatLocation seatNo) {
		super();
		this.seatNo = seatNo;
	}
	
	public Seat(SeatLocation seatNo, STATUS status) {
		this(seatNo);
		this.status = status;
	}

	public SeatLocation getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(SeatLocation seatNo) {
		this.seatNo = seatNo;
	}
	public User getReservedBy() {
		return reservedBy;
	}
	public void setReservedBy(User reservedBy) {
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
		builder.append("Seat<");
		if (seatNo != null)
			builder.append(seatNo).append(", ");
		if (reservedBy != null)
			builder.append("reservedBy=").append(reservedBy).append(", ");
		if (status != null)
			builder.append("status=").append(status);
		builder.append(">");
		return builder.toString();
	}
	
	
}
