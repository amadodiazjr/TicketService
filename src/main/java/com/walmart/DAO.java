package com.walmart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

import org.apache.commons.lang.Validate;

public class DAO {

	public String getEmailAddressByCustomerId(final Integer customerId) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String emailAddress = null;

		final StringBuffer sql = new StringBuffer("SELECT email FROM customer WHERE customer_id = ?;");
		try {
            conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, customerId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				emailAddress = rs.getString("email");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
			DBUtils.safeClose(rs);
		}
		
		return emailAddress;
	}

	public Integer getSeatsOnHoldBySeatId(final Set<Integer> seatIds) throws Exception {
		Validate.notNull(seatIds, "seatIds cannot be null.");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer count = 0;

		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) as count ");
		sql.append("FROM seats_on_hold ");
		sql.append("WHERE seat_id IN (");
		for (int i=0; i< seatIds.size(); i++) {
			if (i>0) { sql.append(", "); }
			sql.append("?");
		}
		sql.append(")");

		try {
            conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int i=1;
			for (final Integer seatId : seatIds) {
				pstmt.setInt(i, seatId);
				i++;
			}

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = Integer.parseInt(rs.getString("count"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
			DBUtils.safeClose(rs);
		}
		
		return count;
	}
}