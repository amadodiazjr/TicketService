package com.walmart;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class WalmartTicketServiceTest {

	@Test
	public void canCreateATicketServiceObject() {
		// ~given
		WalmartTicketService service = null;
		
		// ~when
		service = new WalmartTicketService();

		// ~then
		assertThat(service, is(notNullValue()));
	}

}
