package com.walmart;

import org.apache.commons.lang.Validate;

import lombok.Getter;

@Getter
public class Seat {
	private final Integer id;
	private final Integer levelId;
	private final Integer number;
	
	public Seat(final Integer id, final Integer levelId, final Integer number) {
		Validate.notNull(id, "id cannot be null.");
		Validate.isTrue(id > 0, "id cannot be less than zero.");
		Validate.notNull(levelId, "levelId cannot be null.");
		Validate.isTrue(levelId > 0, "levelId cannot be less than zero.");
		Validate.notNull(number, "number cannot be null.");
		Validate.isTrue(number > 0, "number cannot be less than zero.");
		
		this.id = id;
		this.levelId = levelId;
		this.number = number;
	}
	
}