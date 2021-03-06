package integration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.walmart.DAO;
import com.walmart.DBUtils;
import com.walmart.SeatHold;
import com.walmart.TicketService;
import com.walmart.WalmartTicketService;

public class WalmartTicketServiceTest {

	@BeforeClass
	public static void doThisBeforeAllTests() throws Exception {
		System.setProperty("dataSource", "conf/dev/mysql.properties");
		DBUtils.start();
	}

	@AfterClass
	public static void doThisAfterAllTests() throws Exception {
		DBUtils.stop();	
	}
	
	@Before
	public void doThisBeforeEachTest() throws Exception {
		final DAO dao = new DAO();
		dao.resetDatabase();
	}
	
	@Test
	public void numSeatsAvailableShallReturnTotalNumberOfAllLevelsWhenLevelIsNotProvided() throws Exception {
		// ~given
		final TicketService service = new WalmartTicketService();		
		
		// ~when
		final Integer num = service.numSeatsAvailable(Optional.empty()); 

		// ~then
		assertThat(num, is(6250));
	}
	
	@Test
	public void numSeatsAvailableShallReturnTotalNumberOfSpecificLevelWhenLevelIsProvided() throws Exception {
		// ~given
		final TicketService service = new WalmartTicketService();		
		
		// ~when
		final Integer num = service.numSeatsAvailable(Optional.of(1)); 

		// ~then
		assertThat(num, is(1250));
	}

	@Test(expected=Exception.class)
	public void reserveSeatsShallThrowAnExceptionWhenZeroHeldSeatsAreFound() throws Exception {
		final TicketService service = new WalmartTicketService();

		// ~given
		final int seatHoldId = 1;
		final String customerEmail = "foo@bar.com";

		// ~when
		service.reserveSeats(seatHoldId, customerEmail);
		
		// ~then
		// an exception is thrown
	}
	
	@Test
	public void findAndHoldSeatsShallHoldSeatsByNumber() throws Exception {
		final TicketService service = new WalmartTicketService();

		// ~given
		final Integer numSeats = 1;
		final String customerEmail = "foo@bar.com";

		// ~when
		final SeatHold seatHold = service.findAndHoldSeats(numSeats, Optional.empty(), Optional.empty(), customerEmail);

		// ~then
		assertThat(seatHold, is(notNullValue()));
		assertThat(seatHold.getSeatIds().size(), is(numSeats));
	}

}