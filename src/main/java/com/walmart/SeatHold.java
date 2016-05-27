package com.walmart;

import java.util.List;

public class SeatHold {
	final Integer customerId;
	final List<Integer> seatIds;
	
	public SeatHold(final Integer customerId, final List<Integer> seatIds) {
		this.customerId = customerId;
		this.seatIds = seatIds;
	}
	
	public Integer getCustomerId() {
		return this.customerId;
	}
	
	public List<Integer> getSeatIds() {
		return this.seatIds;
	}
}