package com.walmart;

import java.util.ArrayList;
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

	/**
	 * Assuming that the lower the seat number, the better the view.  So best seats are allocated first.
	 */
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
		String customerEmail) throws Exception {
		Validate.isTrue(numSeats > 0, "numSeats cannot be less than zero.");
		Validate.notBlank(customerEmail, "customerEmail is not provided.");
		Validate.isTrue(EmailValidator.getInstance().isValid(customerEmail), customerEmail + " is not valid.");

		final DAO dao = new DAO();
		final Integer customerId = dao.getCustomerIdByEmailAddress(customerEmail);

	    final Comparator<Seat> bySeatNumber = (s1, s2) -> Integer.compare(s1.getNumber(), s2.getNumber());
		final Set<Seat> allSeats = dao.getAllAvailableSeats();
		final List<Integer> availableSeatIds = new ArrayList<>();
		if (!minLevel.isPresent() && !maxLevel.isPresent()) {
			availableSeatIds.addAll(allSeats.stream().map(s -> s.getId()).collect(Collectors.toSet()));
		} else {
			final List<Seat> availableSeats = new ArrayList<>();
			if (minLevel.isPresent() && maxLevel.isPresent()) {
				final Integer min = minLevel.get();
				final Integer max = maxLevel.get();
				for (int i=min; min <= max; i++) {
					final Integer levelId = i;
					availableSeats.addAll(allSeats.stream().filter(s -> s.getLevelId().equals(levelId)).sorted(bySeatNumber).collect(Collectors.toList()));
				}
			} else if (!minLevel.isPresent() && maxLevel.isPresent()) {
				availableSeats.addAll(allSeats.stream().filter(s -> s.getLevelId().equals(maxLevel.get())).sorted(bySeatNumber).collect(Collectors.toList()));
			} else if (minLevel.isPresent() && !maxLevel.isPresent()) {
				availableSeats.addAll(allSeats.stream().filter(s -> s.getLevelId().equals(minLevel.get())).sorted(bySeatNumber).collect(Collectors.toList()));	
			}
			availableSeatIds.addAll(availableSeats.stream().map(s -> s.getId()).collect(Collectors.toSet()));
		}

		final List<Integer> heldSeats = new ArrayList<>();
		for (int i=0; i<numSeats; i++) {
			heldSeats.add(availableSeatIds.get(0));
		}

		final SeatHold seatHold = new SeatHold(customerId, heldSeats);
		dao.holdSeats(seatHold);
		dao.addOrder(customerId);
		
		final Integer seatHoldId = dao.getOrderIdByCustomerId(customerId);
		seatHold.setHoldId(seatHoldId);

		return seatHold;
	}

	public String reserveSeats(int seatHoldId, String customerEmail) throws Exception {
		Validate.isTrue(seatHoldId > 0, "seatHoldId cannot be a negative number.");
		Validate.notBlank(customerEmail, "customerEmail is not provided.");
		Validate.isTrue(EmailValidator.getInstance().isValid(customerEmail), customerEmail + " is not valid.");
		
		final DAO dao = new DAO();
		final Integer customerId = dao.getCustomerIdByEmailAddress(customerEmail); // TODO (amado): could get rid of this transaction via SQL join
		final Set<Integer> seatIds = dao.getHeldSeatsByCustomerId(customerId);
		Validate.isTrue(!seatIds.isEmpty(), "customer has no held seats.");

		dao.reserveSeats(customerId, seatIds);

		final String confirmationNumber = ConfirmationNumberGenerator.getInstance().generate().toString(); 
		dao.updateOrder(seatHoldId, confirmationNumber);

		return confirmationNumber;
	}

}