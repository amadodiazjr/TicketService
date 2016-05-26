package com.walmart;

public class Level {
	private final Integer id;
	private final String name;
	private final Double price;
	private final Integer rows;
	private final Integer seatsPerRow;
	
	public Level(final Integer id, final String name, final Double price, final Integer rows, final Integer seatsPerRow) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.rows = rows;
		this.seatsPerRow = seatsPerRow;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Double getPrice() {
		return this.price;
	}
	
	public Integer getRows() {
		return this.rows;
	}
	
	public Integer getSeatsPerRow() {
		return this.seatsPerRow;
	}

}