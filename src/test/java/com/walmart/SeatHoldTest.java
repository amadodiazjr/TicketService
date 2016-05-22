package com.walmart;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SeatHoldTest {

	@Test
	public void canCreateASeatHoldObject() {
		// ~given
		SeatHold seatHold = null;
		
		// ~when
		seatHold = new SeatHold();
		
		// ~then
		assertThat(seatHold, is(notNullValue()));
	}
}