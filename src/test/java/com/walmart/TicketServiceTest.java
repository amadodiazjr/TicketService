package com.walmart;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TicketServiceTest {

	@Test
	public void canCreateATicketServiceObject() {
		// ~given
		TicketService service = null;
		
		// ~when
		service = new TicketService();

		// ~then
		assertThat(service, is(notNullValue()));
	}

}
