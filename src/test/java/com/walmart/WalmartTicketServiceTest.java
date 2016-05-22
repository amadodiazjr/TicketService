package com.walmart;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class WalmartTicketServiceTest {

	@Test
	public void canCreateATicketServiceObject() {
		// ~given
		TicketService service = null;
		
		// ~when
		service = new WalmartTicketService();

		// ~then
		assertThat(service, is(notNullValue()));
	}

	@Test(expected = Exception.class)
	public void reserveSeatsShallReturnAnErrorWhenSeatHoldIdIsANegativeNumber() {
		final TicketService service = new WalmartTicketService();
		final String customerEmail = "foo@bar.com";
		
		// ~given
		final int seatHoldId = -1;
		
		// ~when
		service.reserveSeats(seatHoldId, customerEmail);
		
		// ~then
		// error is thrown
	}

	@Test(expected = Exception.class)
	public void reserveSeatsShallReturnAnErrorWhenCustomerEmailIsNotProvided() {
		final TicketService service = new WalmartTicketService();
		final int seatHoldId = 1;
		
		// ~given
		final String customerEmail = null;
		
		// ~when
		service.reserveSeats(seatHoldId, customerEmail);
		
		// ~then
		// error is thrown
	}

}