package unit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.walmart.TicketService;
import com.walmart.WalmartTicketService;

public class WalmartTicketServiceTest {

	@Test
	public void constructorShallCreateATicketServiceObject() {
		// ~given
		TicketService service = null;
		
		// ~when
		service = new WalmartTicketService();

		// ~then
		assertThat(service, is(notNullValue()));
	}

	@Test(expected = Exception.class)
	public void reserveSeatsShallThrowAnExceptionWhenSeatHoldIdIsANegativeNumber() {
		final TicketService service = new WalmartTicketService();
		final String customerEmail = "foo@bar.com";
		
		// ~given
		final int seatHoldId = -1;
		
		// ~when
		service.reserveSeats(seatHoldId, customerEmail);
		
		// ~then
		// exception is thrown
	}

	@Test(expected = Exception.class)
	public void reserveSeatsShallThrowAnExceptionAnErrorWhenCustomerEmailIsNotProvided() {
		final TicketService service = new WalmartTicketService();
		final int seatHoldId = 1;
		
		// ~given
		final String customerEmail = null;
		
		// ~when
		service.reserveSeats(seatHoldId, customerEmail);
		
		// ~then
		// exception is thrown
	}

	@Test(expected = Exception.class)
	public void reserveSeatsShallThrowAnExceptionAnErrorWhenCustomerEmailIsNotValid() {
		final TicketService service = new WalmartTicketService();
		final int seatHoldId = 1;

		// ~given
		final String customerEmail = "foobar";

		// ~when
		service.reserveSeats(seatHoldId, customerEmail);
		
		// ~then
		// exception is thrown
	}

	@Test
	public void reserveSeatsShallReturnAStringWhenAllRequirementsAreMet() {
		final TicketService service = new WalmartTicketService();

		// ~given
		final int seatHoldId = 1;
		final String customerEmail = "foo@bar.com";

		// ~when
		final String confirmationNumber = service.reserveSeats(seatHoldId, customerEmail);
		
		// ~then
		assertThat(confirmationNumber, is(notNullValue()));
	}
}