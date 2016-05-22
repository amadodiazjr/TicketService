package integration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import com.walmart.TicketService;
import com.walmart.WalmartTicketService;

public class WalmartTicketServiceTest {
	
	@Test
	public void numSeatsAvailableShallReturnTotalNumberOfAllLevelsWhenLevelIsNotProvided() {
		final TicketService service = new WalmartTicketService();
		final int seatHoldId = 1;
		final String customerEmail = "foo@bar.com";

		// ~given
		
		
		// ~when
		final Integer num = service.numSeatsAvailable(Optional.of(1)); 

		// ~then
		assertThat(num, is(50));
	}
	
}