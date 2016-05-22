package com.walmart;

import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.EmailValidator;

public class WalmartTicketService implements TicketService {

	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		// TODO Auto-generated method stub
		return 0;
	}

	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	public String reserveSeats(int seatHoldId, String customerEmail) {
		Validate.isTrue(seatHoldId > 0, "seatHoldId cannot be a negative number.");
		Validate.notBlank(customerEmail, "customerEmail is not provided.");
		Validate.isTrue(EmailValidator.getInstance().isValid(customerEmail), customerEmail + " is not valid.");

		return ConfirmationNumberGenerator.getInstance().generate().toString();
	}

}