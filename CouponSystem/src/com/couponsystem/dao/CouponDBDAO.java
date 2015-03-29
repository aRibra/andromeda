package com.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.connection.DBConnection;

public class CouponDBDAO implements CouponDAO {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	public CouponDBDAO() {

	}

	@Override
	public void createCoupon(Coupon coupon) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		String insertSQLQuery = "INSERT INTO COUPON"
				+ "(ID, TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE) VALUES"
				+ "(?,?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQLQuery);
			preparedStatement.setLong(1, coupon.getId());
			preparedStatement.setString(2, coupon.getTitle());
			preparedStatement.setDate(3, coupon.getStartDate());
			preparedStatement.setDate(4, coupon.getEndDate());
			preparedStatement.setInt(5, coupon.getAmount());

			// DONE - TODO: ERROR - set ENUMS ???
			preparedStatement.setString(6, coupon.getType().name());
			preparedStatement.setString(7, coupon.getMessage());
			preparedStatement.setDouble(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImage());

			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into COUPON table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void removeCoupon(Coupon coupon) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		String deleteSQLQuery = "DELETE FROM COUPON WHERE ID = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQLQuery);
			preparedStatement.setLong(1, coupon.getId());
			preparedStatement.executeUpdate();

			System.out.println("Record: " + coupon.getId()
					+ " is deleted from COUPON table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void updateCoupon(Coupon coupon) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE COUPON SET "
				+ "TITLE = ?, START_DATE = ?, END_DATE = ?, AMOUNT = ?, TYPE = ?, MESSAGE = ?, PRICE = ?, IMAGE = ? WHERE ID = ?";

		try {
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, coupon.getTitle());
			preparedStatement.setDate(2, coupon.getStartDate());
			preparedStatement.setDate(3, coupon.getEndDate());
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

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Coupon getCoupon(int id) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
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
				coupon.setStartDate(resultSet.getDate("START_DATE"));
				coupon.setEndDate(resultSet.getDate("END_DATE"));
				coupon.setAmount(resultSet.getInt("AMOUNT"));

				// DONE - TODO: ENUM ERROR
				coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setPrice(resultSet.getDouble("PRICE"));
				coupon.setImage(resultSet.getString("IMAGE"));

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return coupon;

	}

	@Override
	public Collection<Coupon> getAllCouponsByType(CouponType type) {

		// retrieves list of all coupons based on a specific type

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
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
				coupon.setStartDate(resultSet.getDate("START_DATE"));
				coupon.setEndDate(resultSet.getDate("END_DATE"));
				coupon.setAmount(resultSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setPrice(resultSet.getDouble("PRICE"));
				coupon.setImage(resultSet.getString("IMAGE"));

				couponList.add(coupon);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return couponList;
	}

	@Override
	public Collection<Coupon> getCompanyCouponsByType(Company company) {

		// retrieves list of coupons for a specific company based on a specific
		// type

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQLQuery = "SELECT * FROM COUPON cpn INNER JOIN company_coupon cc ON cpn.ID = cc.COUPON_ID AND cc.COMP_ID = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQLQuery);
			preparedStatement.setLong(1, company.getId());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Coupon coupon = new Coupon();

				coupon.setId(resultSet.getLong("ID"));
				coupon.setTitle(resultSet.getString("TITLE"));
				coupon.setStartDate(resultSet.getDate("START_DATE"));
				coupon.setEndDate(resultSet.getDate("END_DATE"));
				coupon.setAmount(resultSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setPrice(resultSet.getDouble("PRICE"));
				coupon.setImage(resultSet.getString("IMAGE"));

				couponList.add(coupon);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return couponList;
	}

	@Override
	public Collection<Coupon> getAllCoupons() {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
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
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));

				// DONE - TODO: ENUM ERROR
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));

				coupList.add(coupon);

			}

		} catch (SQLException e) {
			// we have to remove all printStackTrace() stmnts
			e.printStackTrace();

		} finally {

			if (connection != null) {
				try {
					preparedStatement.close();
					connectionPool.releaseConnection(connection);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return coupList;

	}

}
