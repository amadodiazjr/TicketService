package integration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.sql.Connection;

import org.junit.Test;

import com.walmart.DBUtils;

public class DBUtilsTest {

	@Test
	public void initWithMySqlShallWork() {
		// ~given
		System.setProperty("dataSource", "conf/dev/mysql.properties");
		
		// when
		try {
			DBUtils.start();
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.stop();
		}
		
		// ~then no exception is thrown
	}
	
	@Test
	public void getConnectionShouldReturnAConnectionObject() throws Exception {
		System.setProperty("dataSource", "conf/dev/mysql.properties");

		// ~given
		Connection conn = null;

		try {			
			DBUtils.start();
			
			// ~when
			conn = DBUtils.getConnection();
	
			// ~then
			assertThat(conn, is(notNullValue()));
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.stop();
		}
	}

}