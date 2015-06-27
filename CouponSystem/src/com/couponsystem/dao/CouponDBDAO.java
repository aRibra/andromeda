package com.couponsystem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.couponsystem.CouponSystem;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.connection.DBConnection;
import com.couponsystem.exceptions.CouponSystemException;

public class CouponDBDAO implements CouponDAO {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	public CouponDBDAO() {

	}

	@Override
	public void createCoupon(Coupon coupon) throws CouponSystemException {

		// Before creating Check coupon start and end dates are not before
		// today.

		java.util.Date today = new java.util.Date();
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(today);

		Calendar startDateCal = Calendar.getInstance();
		Calendar endDateCal = Calendar.getInstance();
		startDateCal.setTime(coupon.getStartDate());
		endDateCal.setTime(coupon.getEndDate());

		if (startDateCal.before(todayCal) || endDateCal.before(todayCal))
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error12"));

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		String insertSQLQuery = "INSERT INTO COUPON"
				+ "(TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE) VALUES"
				+ "(?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQLQuery);
			preparedStatement.setString(1, coupon.getTitle());
			preparedStatement.setDate(2, new Date(coupon.getStartDate()
					.getTime()));
			preparedStatement.setDate(3,
					new Date(coupon.getEndDate().getTime()));
			preparedStatement.setInt(4, coupon.getAmount());

			// DONE - TODO: ERROR - set ENUMS ???
			preparedStatement.setString(5, coupon.getType().name());
			preparedStatement.setString(6, coupon.getMessage());
			preparedStatement.setDouble(7, coupon.getPrice());
			preparedStatement.setString(8, coupon.getImage());

			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into COUPON table!");

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {

					String stackTrace = CouponSystem.getStackTraceAsString(e);
					throw new CouponSystemException(e.getMessage(), stackTrace);

				}
			}
		}

	}

	@Override
	public void removeCoupon(Coupon coupon) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		}

		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement2 = null;

		try {
			// Delete Customer_Coupon join

			String deleteSQLQuery = "DELETE FROM customer_coupon WHERE COUPON_ID = ?";

			preparedStatement = connection.prepareStatement(deleteSQLQuery);
			preparedStatement.setLong(1, coupon.getId());
			preparedStatement.executeUpdate();

			System.out.println("Record: " + coupon.getId()
					+ " is deleted from customer_coupon table!");

			// Delete Company_Coupon join

			String deleteSQLQuery2 = "DELETE FROM company_coupon WHERE COUPON_ID = ?";

			preparedStatement2 = connection.prepareStatement(deleteSQLQuery2);
			preparedStatement2.setLong(1, coupon.getId());
			preparedStatement2.executeUpdate();

			System.out.println("Record: " + coupon.getId()
					+ " is deleted from company_coupon table!");

			String deleteSQLQuery3 = "DELETE FROM COUPON WHERE ID = ?";

			preparedStatement3 = connection.prepareStatement(deleteSQLQuery3);
			preparedStatement3.setLong(1, coupon.getId());
			preparedStatement3.executeUpdate();

			System.out.println("Record: " + coupon.getId()
					+ " is deleted from COUPON table!");

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
					preparedStatement2.close();
					preparedStatement3.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					String stackTrace = CouponSystem.getStackTraceAsString(e);
					throw new CouponSystemException(e.getMessage(), stackTrace);
				}
			}
		}

	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {

		// Before updating Check coupon start and end dates are not before
		// today.

		java.util.Date today = new java.util.Date();
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(today);

		Calendar startDateCal = Calendar.getInstance();
		Calendar endDateCal = Calendar.getInstance();
		startDateCal.setTime(coupon.getStartDate());
		endDateCal.setTime(coupon.getEndDate());

		if (startDateCal.before(todayCal))
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error12"));
		else if (endDateCal.before(todayCal))
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error13"));

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE COUPON SET "
				+ "TITLE = ?, START_DATE = ?, END_DATE = ?, AMOUNT = ?, TYPE = ?, MESSAGE = ?, PRICE = ?, IMAGE = ? WHERE ID = ?";

		try {
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, coupon.getTitle());
			preparedStatement.setDate(2, new Date(coupon.getStartDate()
					.getTime()));
			preparedStatement.setDate(3,
					new Date(coupon.getEndDate().getTime()));
			preparedStatement.setInt(4, coupon.getAmount());

			// DONE - TODO: ERROR - set ENUMS ???
			preparedStatement.setString(5, coupon.getType().name());
			preparedStatement.setString(6, coupon.getMessage());
			preparedStatement.setDouble(7, coupon.getPrice());
			preparedStatement.setString(8, coupon.getImage());
			preparedStatement.setLong(9, coupon.getId());

			preparedStatement.executeUpdate();

			System.out.println("COUPON record ID: " + coupon.getId()
					+ " was updated! ");

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					String stackTrace = CouponSystem.getStackTraceAsString(e);
					throw new CouponSystemException(e.getMessage(), stackTrace);
				}
			}
		}
	}

	@Override
	public Coupon getCoupon(int id) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		Coupon coupon = new Coupon();

		String selectSQLQuery = "SELECT ID, TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE FROM COUPON WHERE ID = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQLQuery);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				coupon.setId(resultSet.getLong("ID"));
				coupon.setTitle(resultSet.getString("TITLE"));
				coupon.setStartDate(new java.util.Date(resultSet.getDate(
						"START_DATE").getTime()));
				coupon.setEndDate(new java.util.Date(resultSet.getDate(
						"END_DATE").getTime()));
				coupon.setAmount(resultSet.getInt("AMOUNT"));

				// DONE - TODO: ENUM ERROR
				coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setPrice(resultSet.getDouble("PRICE"));
				coupon.setImage(resultSet.getString("IMAGE"));

			}

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (connection != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					String stackTrace = CouponSystem.getStackTraceAsString(e);
					throw new CouponSystemException(e.getMessage(), stackTrace);
				}
			}
		}

		return coupon;

	}

	// TODO: DONE IBRAHIM get coupon by enum type???
	@Override
	public Collection<Coupon> getAllCouponsByType(CouponType type)
			throws CouponSystemException {

		// retrieves list of all coupons based on a specific type

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQLQuery = "SELECT * FROM COUPON CPN WHERE CPN.TYPE = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQLQuery);
			preparedStatement.setString(1, type.name());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Coupon coupon = new Coupon();

				coupon.setId(resultSet.getLong("ID"));
				coupon.setTitle(resultSet.getString("TITLE"));
				coupon.setStartDate(new java.util.Date(resultSet.getDate(
						"START_DATE").getTime()));
				coupon.setEndDate(new java.util.Date(resultSet.getDate(
						"END_DATE").getTime()));
				coupon.setAmount(resultSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setPrice(resultSet.getDouble("PRICE"));
				coupon.setImage(resultSet.getString("IMAGE"));

				couponList.add(coupon);

			}

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (connection != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					String stackTrace = CouponSystem.getStackTraceAsString(e);
					throw new CouponSystemException(e.getMessage(), stackTrace);
				}
			}
		}

		return couponList;
	}

	@Override
	public Collection<Coupon> getCompanyCouponsByType(long id, CouponType type)
			throws CouponSystemException {

		// retrieves list of coupons for a specific company based on a specific
		// type

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQLQuery = "SELECT * FROM COUPON cpn INNER JOIN company_coupon cc ON cpn.ID = cc.COUPON_ID AND cc.COMP_ID = ? AND cpn.type = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQLQuery);
			preparedStatement.setLong(1, id);
			preparedStatement.setString(2, type.name());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Coupon coupon = new Coupon();

				coupon.setId(resultSet.getLong("ID"));
				coupon.setTitle(resultSet.getString("TITLE"));
				coupon.setStartDate(new java.util.Date(resultSet.getDate(
						"START_DATE").getTime()));
				coupon.setEndDate(new java.util.Date(resultSet.getDate(
						"END_DATE").getTime()));
				coupon.setAmount(resultSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setPrice(resultSet.getDouble("PRICE"));
				coupon.setImage(resultSet.getString("IMAGE"));

				couponList.add(coupon);

			}

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (connection != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					String stackTrace = CouponSystem.getStackTraceAsString(e);
					throw new CouponSystemException(e.getMessage(), stackTrace);
				}
			}
		}

		return couponList;
	}

	@Override
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		List<Coupon> coupList = new ArrayList<>();

		String selectSQLQuery = "SELECT ID, TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE FROM COUPON";

		try {

			preparedStatement = connection.prepareStatement(selectSQLQuery);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Coupon coupon = new Coupon();

				coupon.setId(rs.getLong("ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(new java.util.Date(rs.getDate("START_DATE")
						.getTime()));
				coupon.setEndDate(new java.util.Date(rs.getDate("END_DATE")
						.getTime()));
				coupon.setAmount(rs.getInt("AMOUNT"));

				// DONE - TODO: ENUM ERROR
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));

				coupList.add(coupon);

			}

		} catch (SQLException e) {
			// DONE :D ^_^ we have to remove all printStackTrace() stmnts
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (connection != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					String stackTrace = CouponSystem.getStackTraceAsString(e);
					throw new CouponSystemException(e.getMessage(), stackTrace);
				}
			}
		}

		return coupList;

	}

}
