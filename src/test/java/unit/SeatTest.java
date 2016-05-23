package unit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.walmart.Seat;

public class SeatTest {
	
	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenIdIsNull() {
		final Integer levelId = 1;
		final Integer number = 1;

		// ~given
		final Integer id = null;

		// ~when
		new Seat(id, levelId, number);

		// ~then
		// an exception is thrown
	}
	
	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenLevelIdIsNull() {
		final Integer id = 1;
		final Integer number = 1;
		
		// ~given
		final Integer levelId = null;

		// ~when
		new Seat(id, levelId, number);

		// ~then
		// an exception is thrown
	}

	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenNumberIsNull() {
		final Integer id = 1;
		final Integer levelId = 1;

		// ~given
		final Integer number = null;

		// ~when
		new Seat(id, levelId, number);

		// ~then
		// an exception is thrown
	}

	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenIdIsLessThanZero() {
		final Integer levelId = 1;
		final Integer number = 1;

		// ~given
		final Integer id = -1;

		// ~when
		new Seat(id, levelId, number);

		// ~then
		// an exception is thrown
	}
	
	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenLevelIdIsLessThanZero() {
		final Integer id = 1;
		final Integer number = 1;
		
		// ~given
		final Integer levelId = -1;

		// ~when
		new Seat(id, levelId, number);

		// ~then
		// an exception is thrown
	}

	@Test(expected=Exception.class)
	public void constructorShallThrowAnExceptionWhenNumberIsLessThanZero() {
		final Integer id = 1;
		final Integer levelId = 1;

		// ~given
		final Integer number = -1;

		// ~when
		new Seat(id, levelId, number);

		// ~then
		// an exception is thrown
	}

	@Test
	public void constructorShallCreateASeatObjectWhenAllRequirementsAreMet() {
		// ~given
		final Integer id = 1;
		final Integer levelId = 1;
		final Integer number = 1;

		// ~when
		final Seat seat = new Seat(id, levelId, number);

		// ~then
		assertThat(seat, is(notNullValue()));		
	}

}