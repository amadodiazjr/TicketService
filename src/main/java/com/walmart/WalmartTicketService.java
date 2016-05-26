package com.walmart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.EmailValidator;

public class WalmartTicketService implements TicketService {
	
	public int numSeatsAvailable(Optional<Integer> venueLevel) throws Exception {
		final DAO dao = new DAO();
		List<Integer> levelIds = dao.getLevelIds();

		if (venueLevel.isPresent()) {
			final Integer levelId = venueLevel.get();
			Validate.isTrue(levelIds.contains(levelId), levelId + " is not supported.");
			levelIds = Collections.singletonList(levelId);
		}
		
		final Integer totalSeats = dao.getTotalNumberOfSeats(levelIds);
		final Integer totalSeatsTaken = dao.getSeatsOnHoldOrReserveBySeatId(levelIds);

		return totalSeats - totalSeatsTaken;
	}

	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
		String customerEmail) throws Exception {
		Validate.isTrue(numSeats > 0, "numSeats cannot be less than zero.");
		Validate.notBlank(customerEmail, "customerEmail is not provided.");
		Validate.isTrue(EmailValidator.getInstance().isValid(customerEmail), customerEmail + " is not valid.");

		final DAO dao = new DAO();
		final Integer customerId = dao.getCustomerIdByEmailAddress(customerEmail);

	    final Comparator<Seat> bySeatNumber = (s1, s2) -> Integer.compare(s1.getNumber(), s2.getNumber());
		final Set<Seat> seats = dao.getAllAvailableSeats();
		final Set<Integer> seatIds = new HashSet<>();
		if (!minLevel.isPresent() && !maxLevel.isPresent()) {
			seatIds.addAll(seats.stream().map(s -> s.getId()).collect(Collectors.toSet()));
		} else {
			final List<Seat> heldSeats = new ArrayList<>();
			if (minLevel.isPresent() && maxLevel.isPresent()) {
				final Integer min = minLevel.get();
				final Integer max = maxLevel.get();
				for (int i=min; min <= max; i++) {
					final Integer levelId = i;
					heldSeats.addAll(seats.stream().filter(s -> s.getLevelId().equals(levelId)).sorted(bySeatNumber).collect(Collectors.toList()));
				}
			} else if (!minLevel.isPresent() && maxLevel.isPresent()) {
				heldSeats.addAll(seats.stream().filter(s -> s.getLevelId().equals(maxLevel.get())).sorted(bySeatNumber).collect(Collectors.toList()));
			} else if (minLevel.isPresent() && !maxLevel.isPresent()) {
				heldSeats.addAll(seats.stream().filter(s -> s.getLevelId().equals(minLevel.get())).sorted(bySeatNumber).collect(Collectors.toList()));	
			}
			seatIds.addAll(heldSeats.stream().map(s -> s.getId()).collect(Collectors.toSet()));
		}

		final SeatHold seatHold = new SeatHold(customerId, seatIds);
		dao.holdSeats(seatHold);

		return seatHold;
	}

	public String reserveSeats(int seatHoldId, String customerEmail) {
		Validate.isTrue(seatHoldId > 0, "seatHoldId cannot be a negative number.");
		Validate.notBlank(customerEmail, "customerEmail is not provided.");
		Validate.isTrue(EmailValidator.getInstance().isValid(customerEmail), customerEmail + " is not valid.");

		return ConfirmationNumberGenerator.getInstance().generate().toString();
	}

}