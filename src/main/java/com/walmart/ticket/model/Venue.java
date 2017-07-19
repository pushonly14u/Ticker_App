package com.walmart.ticket.model;

import java.util.Arrays;
/**
 * 
 * @author Darpan Shah
 *
 */
public class Venue {
	private int rows;
	private int seatsPerRow;
	private Seat[][] seats;
	private int capacity;

	public Venue(int rows, int seatsPerRow) {
		super();
		this.rows = rows;
		this.seatsPerRow = seatsPerRow;
		this.capacity = (this.rows * this.seatsPerRow);
		init();
	}
	private void init(){
		seats = new Seat[rows][seatsPerRow];
		for(int i=0; i<rows; i++){
			for(int j=0; j<seatsPerRow; j++){
				seats[i][j] = new Seat(new Loc(i, j), STATUS.AVAILABLE);
			}
		}
	}
	
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getSeatsPerRow() {
		return seatsPerRow;
	}
	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}
	public Seat[][] getSeats() {
		return seats;
	}
	public void setSeats(Seat[][] seats) {
		this.seats = seats;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Venue [rows=").append(rows).append(", seatsPerRow=")
				.append(seatsPerRow).append(", ");
		if (seats != null)
			builder.append("seats=").append(Arrays.toString(seats));
		builder.append("]");
		return builder.toString();
	}
	public String prettyPrint(){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<rows; i++){
			for(int j=0; j<seatsPerRow; j++){
				sb.append(seats[i][j].getStatus()); sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
