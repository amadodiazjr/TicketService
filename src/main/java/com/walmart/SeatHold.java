package com.walmart;

import java.util.Set;

public class SeatHold {
	final Integer customerId;
	final Set<Integer> seatIds;
	
	public SeatHold(final Integer customerId, final Set<Integer> seatIds) {
		this.customerId = customerId;
		this.seatIds = seatIds;
	}
	
	public Integer getCustomerId() {
		return this.customerId;
	}
	
	public Set<Integer> getSeatIds() {
		return this.seatIds;
	}
}