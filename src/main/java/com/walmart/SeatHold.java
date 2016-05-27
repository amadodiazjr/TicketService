package com.walmart;

import java.util.List;

import org.apache.commons.lang.Validate;

public class SeatHold {
	final Integer customerId;
	final List<Integer> seatIds;
	Integer holdId;

	public SeatHold(final Integer customerId, final List<Integer> seatIds) {
		Validate.notNull(customerId, "customerId cannot be null.");
		Validate.notEmpty(seatIds, "seatIds cannot be empty.");

		this.customerId = customerId;
		this.seatIds = seatIds;
		this.holdId = null;
	}
	
	public Integer getCustomerId() {
		return this.customerId;
	}
	
	public List<Integer> getSeatIds() {
		return this.seatIds;
	}

	public Integer getHoldId() {
		return this.holdId;
	}
	
	public void setHoldId(final Integer holdId) {
		this.holdId = holdId;
	}

}