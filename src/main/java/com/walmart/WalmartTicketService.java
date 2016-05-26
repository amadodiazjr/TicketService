package com.walmart;

import java.util.Collections;
import java.util.Comparator;
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
		if (!minLevel.isPresent() && !maxLevel.isPresent()) {
			final Set<Integer> seatIds = seats.stream().map(s -> s.getId()).collect(Collectors.toSet());
			final SeatHold seatHold = new SeatHold(customerId, seatIds);
			dao.holdSeats(seatHold);

			return seatHold;
		} else {
			final List<Seat> orchestraSeats = seats.stream().filter(s -> s.getLevelId().equals(1)).sorted(bySeatNumber).collect(Collectors.toList());
			final List<Seat> mainSeats = seats.stream().filter(s -> s.getLevelId().equals(2)).sorted(bySeatNumber).collect(Collectors.toList());
			final List<Seat> balconyOneSeats = seats.stream().filter(s -> s.getLevelId().equals(3)).sorted(bySeatNumber).collect(Collectors.toList());
			final List<Seat> balconyTwoSeats = seats.stream().filter(s -> s.getLevelId().equals(4)).sorted(bySeatNumber).collect(Collectors.toList());

			
			final Set<Level> levels = dao.getAllLevels();
			final Set<Integer> levelIds = levels.stream().map(l -> l.getId()).collect(Collectors.toSet());
			
		}

		return null;
	}

	public String reserveSeats(int seatHoldId, String customerEmail) {
		Validate.isTrue(seatHoldId > 0, "seatHoldId cannot be a negative number.");
		Validate.notBlank(customerEmail, "customerEmail is not provided.");
		Validate.isTrue(EmailValidator.getInstance().isValid(customerEmail), customerEmail + " is not valid.");

		return ConfirmationNumberGenerator.getInstance().generate().toString();
	}

}