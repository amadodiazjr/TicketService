package com.walmart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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

	public Set<Seat> getAllAvailableSeats() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final Set<Seat> seats = new HashSet<>();

		final StringBuffer sql = new StringBuffer("SELECT id, status_id, level_id, number, customer_id FROM seat WHERE status_id = 0;");
		try {
            conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				final Integer id = Integer.parseInt(rs.getString("id"));
				final Integer levelId = Integer.parseInt(rs.getString("level_id"));
				final Integer statusId = Integer.parseInt(rs.getString("status_id"));
				final Integer customerId = null != rs.getString("customer_id")
					? Integer.parseInt(rs.getString("customer_id")) : null;
				final Integer number = Integer.parseInt(rs.getString("number"));
				seats.add(new Seat(id, levelId, number, statusId, customerId));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
			DBUtils.safeClose(rs);
		}
		
		return seats;
	}
	
	public Set<Level> getAllLevels() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final Set<Level> levels = new HashSet<>();

		final StringBuffer sql = new StringBuffer("SELECT id, name, price, rows, seats_per_row FROM level;");
		try {
            conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				final Integer id = Integer.parseInt(rs.getString("id"));
				final String name = rs.getString("name");
				final Double price = Double.parseDouble(rs.getString("price"));
				final Integer rows = Integer.parseInt(rs.getString("rows"));
				final Integer seatsPerRow = Integer.parseInt(rs.getString("seats_per_row"));
				levels.add(new Level(id, name, price, rows, seatsPerRow));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
			DBUtils.safeClose(rs);
		}

		return levels;		
	}

	public void holdSeats(final SeatHold seatHold) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		final Collection<Integer> seatIds = seatHold.getSeatIds();
		final Integer customerId = seatHold.getCustomerId();
		
        Integer batchSize = 0;
		final StringBuffer sql = new StringBuffer();
		sql.append("UPDATE seat SET status_id=1, customer_id=? WHERE id=?;");
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			for (final Integer seatId : seatIds) {				
				pstmt.setInt(1, customerId);
				pstmt.setInt(2, seatId);
				pstmt.addBatch();
				batchSize++;		
			}

			if (batchSize > 0) {
				pstmt.executeBatch();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
		}
	}

	public void reserveSeats(final Integer customerId, final Set<Integer> seatIds) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
        Integer batchSize = 0;
		final StringBuffer sql = new StringBuffer();
		sql.append("UPDATE seat SET status_id=2 WHERE id=? AND customer_id=?;");
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			for (final Integer seatId : seatIds) {
				pstmt.setInt(1, seatId);
				pstmt.setInt(2, customerId);
				pstmt.addBatch();
				batchSize++;		
			}

			if (batchSize > 0) {
				pstmt.executeBatch();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
		}        
	}

	public void updateOrder(final Integer holdId, final String confirmation) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		final StringBuffer sql = new StringBuffer();
		sql.append("UPDATE orders SET confirmation=? WHERE id=?;");
		try {
            conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, confirmation);
            pstmt.setInt(2, holdId);

			pstmt.execute();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
		}
	}

	public void addOrder(final Integer customerId) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;

		final StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO orders (customer_id) VALUES (?);");
		try {
            conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, customerId);

			pstmt.execute();
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
		}
	}

	public Integer getOrderIdByCustomerId(final Integer customerId) throws Exception {
		Validate.notNull(customerId, "customerId cannot be null.");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT id ");
		sql.append("FROM orders ");
		sql.append("WHERE customer_id = ?");

		Integer orderId = null;
		try {
            conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, customerId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				orderId = Integer.parseInt(rs.getString("id"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
			DBUtils.safeClose(rs);
		}
		
		return orderId;		
	}

	public Set<Integer> getHeldSeatsByCustomerId(final Integer customerId) throws Exception {
		Validate.notNull(customerId, "customerId cannot be null.");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final Set<Integer> seatIds = new HashSet<>();

		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT id ");
		sql.append("FROM seat ");
		sql.append("WHERE status_id = 1 AND customer_id = ?");

		try {
            conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, customerId);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				final Integer seatId = Integer.parseInt(rs.getString("id"));
				seatIds.add(seatId);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DBUtils.safeClose(conn);
			DBUtils.safeClose(pstmt);
			DBUtils.safeClose(rs);
		}
		
		return seatIds;		
	}

	public Integer getTotalNumberOfSeats(final List<Integer> levelIds) throws Exception {
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
	
	public Integer getSeatsOnHoldOrReserveBySeatId(final List<Integer> seatIds) throws Exception {
		Validate.notNull(seatIds, "seatIds cannot be null.");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer count = null;

		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) as count ");
		sql.append("FROM seat ");
		sql.append("WHERE status_id <> 0 ");
		sql.append("AND id IN (");
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
	
	public List<Integer> getLevelIds() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final List<Integer> levelIds = new ArrayList<>();
		
		final StringBuffer sql = new StringBuffer("SELECT id FROM level ORDER BY id ASC;");
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