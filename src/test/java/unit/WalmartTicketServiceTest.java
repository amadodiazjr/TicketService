package unit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

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
	public void reserveSeatsShallThrowAnExceptionWhenSeatHoldIdIsANegativeNumber() throws Exception {
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
	public void reserveSeatsShallThrowAnExceptionAnErrorWhenCustomerEmailIsNotProvided() throws Exception {
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
	public void reserveSeatsShallThrowAnExceptionAnErrorWhenCustomerEmailIsNotValid() throws Exception {
		final TicketService service = new WalmartTicketService();
		final int seatHoldId = 1;

		// ~given
		final String customerEmail = "foobar";

		// ~when
		service.reserveSeats(seatHoldId, customerEmail);
		
		// ~then
		// exception is thrown
	}

	@Test(expected = Exception.class)
	public void findAndHoldSeatsShallThrowAnExceptionAnErrorWhenCustomerNumSeatsIsANegativeNumber() throws Exception {
		final TicketService service = new WalmartTicketService();
		final String customerEmail = "foo@bar.com";
		
		// ~given
		final int numSeats = -1;

		// ~when
		service.findAndHoldSeats(numSeats, Optional.empty(), Optional.empty(), customerEmail); 
		
		// ~then
		// exception is thrown
	}

	@Test(expected = Exception.class)
	public void findAndHoldSeatsShallThrowAnExceptionAnErrorWhenCustomerEmailIsNotProvided() throws Exception {
		final TicketService service = new WalmartTicketService();
		final int numSeats = 1;

		// ~given
		final String customerEmail = null;
		
		// ~when
		service.findAndHoldSeats(numSeats, Optional.empty(), Optional.empty(), customerEmail); 
		
		// ~then
		// exception is thrown
	}

	@Test(expected = Exception.class)
	public void findAndHoldSeatsShallThrowAnExceptionAnErrorWhenCustomerEmailIsNotValid() throws Exception {
		final TicketService service = new WalmartTicketService();
		final int numSeats = 1;

		// ~given
		final String customerEmail = "foobar";
		
		// ~when
		service.findAndHoldSeats(numSeats, Optional.empty(), Optional.empty(), customerEmail); 
		
		// ~then
		// exception is thrown
	}

}