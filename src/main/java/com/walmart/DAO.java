package com.walmart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
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

	public Integer getCustomerIdByEmailAddress(final String emailAddress) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer customerId = null;

		final StringBuffer sql = new StringBuffer("SELECT id FROM customer WHERE email = ?;");
		try {
            conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, emailAddress);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				customerId = Integer.parseInt(rs.getString("id"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
			DBUtils.safeClose(rs);
		}
		
		return customerId;
	}

	public Integer getTotalNumberOfSeats(final Set<Integer> levelIds) throws Exception {
		Validate.notNull(levelIds, "seatIds cannot be null.");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer total = 0;

		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT rows, seats_per_row ");
		sql.append("FROM level ");
		sql.append("WHERE id IN (");
		for (int i=0; i< levelIds.size(); i++) {
			if (i>0) { sql.append(", "); }
			sql.append("?");
		}
		sql.append(")");

		try {
            conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int i=1;
			for (final Integer seatId : levelIds) {
				pstmt.setInt(i, seatId);
				i++;
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				final Integer rows = Integer.parseInt(rs.getString("rows"));
				final Integer seats_per_row = Integer.parseInt(rs.getString("seats_per_row"));
				total = total + (rows * seats_per_row);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
			DBUtils.safeClose(rs);
		}
		
		return total;		
	}
	
	public Integer getSeatsOnHoldOrReserveBySeatId(final Set<Integer> seatIds) throws Exception {
		Validate.notNull(seatIds, "seatIds cannot be null.");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer count = null;

		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) as count ");
		sql.append("FROM seat_status ");
		sql.append("WHERE hold = 1 OR reserve = 1 ");
		sql.append("AND seat_id IN (");
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
	
	public Set<Integer> getLevelIds() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final Set<Integer> levelIds = new HashSet<>();
		
		final StringBuffer sql = new StringBuffer("SELECT id FROM level;");
		try {
            conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				levelIds.add(Integer.parseInt(rs.getString("id")));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
			DBUtils.safeClose(rs);
		}
		
		return levelIds;		
	}

}