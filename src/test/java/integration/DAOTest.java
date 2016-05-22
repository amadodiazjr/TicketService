package integration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.walmart.DAO;
import com.walmart.DBUtils;

public class DAOTest {
	
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
	public void getSeatsOnHoldBySeatIdShallReturnSeatsOnHoldBySeatId() throws Exception {
		final DAO dao = new DAO();

		// ~given
		final Integer seatId = 1;
		
		// ~when
		final Integer numSeats = dao.getSeatsOnHoldBySeatId(seatId);
		
		// ~then
		assertThat(numSeats, is(0));	
	}
}
