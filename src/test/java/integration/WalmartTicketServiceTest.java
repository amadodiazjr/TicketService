package integration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.walmart.DBUtils;
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
	
	@Test
	public void numSeatsAvailableShallReturnTotalNumberOfAllLevelsWhenLevelIsNotProvided() throws Exception {
		// ~given
		final TicketService service = new WalmartTicketService();		
		
		// ~when
		final Integer num = service.numSeatsAvailable(Optional.empty()); 

		// ~then
		assertThat(num, is(6250));
	}
	
}