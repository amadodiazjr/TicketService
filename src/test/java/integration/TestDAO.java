package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.walmart.DBUtils;

public class TestDAO {
	public void resetDatabase() throws Exception {
		resetSeatTable();
		deleteOrders();
	}

	private void resetSeatTable() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		final StringBuffer sql = new StringBuffer();
		sql.append("UPDATE seat SET status_id = 0, customer_id = NULL;");
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.execute();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
		}
	}

	private void deleteOrders() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		final StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM orders;");
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.execute();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
		}
	}

}
