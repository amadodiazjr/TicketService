package com.walmart;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.EmailValidator;

public class WalmartTicketService implements TicketService {

	public int numSeatsAvailable(Optional<Integer> venueLevel) throws Exception {
		final DAO dao = new DAO();
		Set<Integer> levelIds = dao.getLevelIds();

		if (venueLevel.isPresent()) {
			final Integer levelId = venueLevel.get();
			Validate.isTrue(levelIds.contains(levelId), levelId + " is not supported.");
			levelIds = Collections.singleton(levelId);
		}
		
		final Integer totalSeats = dao.getTotalNumberOfSeats(levelIds);
		final Integer totalSeatsTaken = dao.getSeatsOnHoldOrReserveBySeatId(levelIds);

		return totalSeats - totalSeatsTaken;
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