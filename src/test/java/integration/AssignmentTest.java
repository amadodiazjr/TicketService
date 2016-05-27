package integration;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.walmart.DAO;
import com.walmart.DBUtils;

public class AssignmentTest {

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
	public void test() throws Exception {
	}

}
