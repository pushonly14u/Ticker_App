package com.walmart.ticket.model;

public class SeatLocation {
	int row;
	int seatPerRow;
	public SeatLocation(int row, int seatPerRow) {
		super();
		this.row = row;
		this.seatPerRow = seatPerRow;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getSeatPerRow() {
		return seatPerRow;
	}
	public void setSeatPerRow(int seatPerRow) {
		this.seatPerRow = seatPerRow;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{").append(row).append(",")
				.append(seatPerRow).append("}");
		return builder.toString();
	}
	
	
}
