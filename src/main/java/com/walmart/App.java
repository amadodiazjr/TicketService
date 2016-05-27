package com.walmart;

import java.util.Optional;

public class App {
	
	public static void main(final String[] args) {
		try {
			init();
			run();
		} catch (final Exception e) {
			System.out.println("Uh-oh! Somebody gonna getta hurt real bad!");
		}
	}

	private static void init() throws Exception {
		System.setProperty("dataSource", "conf/dev/mysql.properties");
		DBUtils.start();

		final DAO dao = new DAO();
		dao.resetDatabase();
	}
	
	private static void run() throws Exception {
		// I know this is all hardcoded, cheating here on purpose. PROMISE!
		final String customerEmail = "foo@bar.com";
		System.out.println("Customer Email: " + customerEmail);
		
		final Integer numSeats = 1;
		final TicketService service = new WalmartTicketService();

		// 1. GET ALL AVAILABLE SEATS
		System.out.println("Getting number of seats available...");
		Integer numSeatsAvailable = service.numSeatsAvailable(Optional.empty());
		System.out.println("Total seats available: " + numSeatsAvailable);

		// 2. FIND AND HOLD SEATS
		System.out.println("Find and hold seats...");
		final SeatHold seatHold = service.findAndHoldSeats(numSeats, Optional.empty(), Optional.empty(), customerEmail);

		// 3. VERIFY THAT SEATS ARE HELD
		numSeatsAvailable = service.numSeatsAvailable(Optional.empty());
		System.out.println("Now how many seats do we have?");
		System.out.println("Total seats available: " + numSeatsAvailable);
		
		// 4. RESERVE SEATS
		final String confirmationNumber = service.reserveSeats(seatHold.getHoldId(), customerEmail);
		System.out.println("SUCCESS!");
		System.out.println("Here is your confirmation number: " + confirmationNumber);		
	}

}