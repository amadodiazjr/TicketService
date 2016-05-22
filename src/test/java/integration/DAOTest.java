package integration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
		final Integer numSeats = dao.getSeatsOnHoldBySeatId(Collections.singleton(seatId));
		
		// ~then
		assertThat(numSeats, is(0));
	}

	@Test
	public void getSeatsOnHoldBySeatIdShallReturnSeatsOnHoldByAllSeatIds() throws Exception {
		final DAO dao = new DAO();

		// ~given
		final Set<Integer> seatIds = new HashSet<>();
		seatIds.add(1);
		seatIds.add(2);
		seatIds.add(3);
		seatIds.add(4);

		// ~when
		final Integer numSeats = dao.getSeatsOnHoldBySeatId(seatIds);
		
		// ~then
		assertThat(numSeats, is(0));		
	}
}