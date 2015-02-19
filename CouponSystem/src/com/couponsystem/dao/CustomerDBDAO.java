package com.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.couponsystem.CouponDbNames;
import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.beans.Customer;
import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.connection.DBConnection;

public class CustomerDBDAO implements CustomerDAO {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	public CustomerDBDAO() {

	}

	@Override
	public void createCustomer(Customer customer) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		String insertSQLQuery = "INSERT INTO " + CouponDbNames.CUSTOMER + "("
				+ CouponDbNames.CUSTOMER_CUST_NAME + ", "
				+ CouponDbNames.CUSTOMER_PASSWORD + ", "
				+ CouponDbNames.CUSTOMER_CLIENT_TYPE + ") VALUES" + "(?,?,?)";

		try {

			preparedStatement = connection.prepareStatement(insertSQLQuery);

			preparedStatement.setString(1, customer.getCustomerName());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.setString(3, customer.getClientType().name());

			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into CUSTOMER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null && connection != null) {
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
	public void removeCustomer(Customer customer) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		String deleteSQLQuery = "DELETE FROM " + CouponDbNames.CUSTOMER
				+ " WHERE " + CouponDbNames.CUSTOMER_ID + " = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQLQuery);

			preparedStatement.setLong(1, customer.getId());
			preparedStatement.executeUpdate();

			System.out.println("Record: " + customer.getId()
					+ " is deleted from CUSTOMER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null && connection != null) {
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
	public void updateCustomer(Customer customer) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE " + CouponDbNames.CUSTOMER + " SET "
				+ CouponDbNames.CUSTOMER_CUST_NAME + " = ?, "
				+ CouponDbNames.CUSTOMER_PASSWORD + " = ? WHERE "
				+ CouponDbNames.CUSTOMER_ID + " = ?";

		try {
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, customer.getCustomerName());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.setLong(3, customer.getId());
			preparedStatement.executeUpdate();

			System.out.println("CUSTOMER record ID: " + customer.getId()
					+ " was updated! ");

			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}

	}

	@Override
	public Customer getCustomer(int id) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		Customer customer = new Customer();

		String selectSQL = "SELECT " + CouponDbNames.CUSTOMER_ID + ", "
				+ CouponDbNames.CUSTOMER_CUST_NAME + ", "
				+ CouponDbNames.CUSTOMER_PASSWORD + ", "
				+ CouponDbNames.CUSTOMER_CLIENT_TYPE + " FROM "
				+ CouponDbNames.CUSTOMER + " WHERE "
				+ CouponDbNames.CUSTOMER_ID + " = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				customer.setId(resultSet.getLong(CouponDbNames.CUSTOMER_ID));
				customer.setCustomerName(resultSet
						.getString(CouponDbNames.CUSTOMER_CUST_NAME));
				customer.setPassword(resultSet
						.getString(CouponDbNames.CUSTOMER_PASSWORD));
				customer.setClientType(ClientType.valueOf(resultSet
						.getString(CouponDbNames.CUSTOMER_CLIENT_TYPE)));

			}

			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}

		return customer;

	}

	// DONE - TODO: company collection??????? should be Customer. Yes, u r right
	// bitch!
	@Override
	public Collection<Customer> getAllCustomers() {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		Collection<Customer> customersList = new ArrayList<>();

		String selectSQLQuery = "SELECT " + CouponDbNames.CUSTOMER_ID + ", "
				+ CouponDbNames.CUSTOMER_CUST_NAME + ", "
				+ CouponDbNames.CUSTOMER_PASSWORD + ", "
				+ CouponDbNames.CUSTOMER_CLIENT_TYPE + " FROM "
				+ CouponDbNames.CUSTOMER;

		try {
			PreparedStatement preparedStatement = null;
			preparedStatement = connection.prepareStatement(selectSQLQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Customer customer = new Customer();

				customer.setId(resultSet.getLong(CouponDbNames.CUSTOMER_ID));
				customer.setCustomerName(resultSet
						.getString(CouponDbNames.CUSTOMER_CUST_NAME));
				customer.setPassword(resultSet
						.getString(CouponDbNames.CUSTOMER_PASSWORD));
				customer.setClientType(ClientType.valueOf(resultSet
						.getString(CouponDbNames.CUSTOMER_CLIENT_TYPE)));

				customersList.add(customer);

			}

			preparedStatement.close();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}
		return customersList;
	}

	// DONE - TODO: IBRAHIM
	@Override
	public Collection<Coupon> getCoupons(Customer customer) {

		// retrieve coupons for a specific customer

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		// "SELECT CPN.* FROM COUPON CPN INNER JOIN CUSTOMER_COUPON CC ON CC.COUPON_ID = CPN.ID AND CC.CUST_ID = ?"
		String selectSQL = "SELECT CPN.* FROM " + CouponDbNames.COUPON
				+ " CPN INNER JOIN " + CouponDbNames.CUSTOMER_COUPON
				+ " CC ON CC." + CouponDbNames.CUSTOMER_COUPON_COUPON_ID
				+ " = CPN." + CouponDbNames.COUPON_ID + " AND CC."
				+ CouponDbNames.CUSTOMER_COUPON_CUST_ID + " = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setLong(1, customer.getId());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Coupon coupon = new Coupon();

				coupon.setId(resultSet.getLong(CouponDbNames.COUPON_ID));
				coupon.setTitle(resultSet.getString(CouponDbNames.COUPON_TITLE));
				coupon.setStartDate(resultSet
						.getDate(CouponDbNames.COUPON_START_DATE));
				coupon.setEndDate(resultSet
						.getDate(CouponDbNames.COUPON_END_DATE));
				coupon.setAmount(resultSet.getInt(CouponDbNames.COUPON_AMOUNT));
				coupon.setType(CouponType.valueOf(resultSet
						.getString(CouponDbNames.COUPON_TYPE)));
				coupon.setMessage(CouponDbNames.COUPON_MESSAGE);
				coupon.setImage(CouponDbNames.COUPON_IMAGE);

				couponList.add(coupon);

			}

			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}

		return couponList;
	}

	@Override
	public void purchaseCoupon(Coupon coupon, Customer customer) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		// "INSERT INTO CUSTOMER_COUPON (CUST_ID, COUPON_ID) VALUES(?,?)"
		String insertSQL = "INSERT INTO " + CouponDbNames.CUSTOMER_COUPON
				+ " (" + CouponDbNames.CUSTOMER_COUPON_CUST_ID + ", "
				+ CouponDbNames.CUSTOMER_COUPON_COUPON_ID + ") VALUES(?,?)";

		try {

			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setLong(1, coupon.getId());
			preparedStatement.setLong(2, customer.getId());

			preparedStatement.executeUpdate();

			System.out
					.println("Coupon Purchase process completed successfully!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null && connection != null) {
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
	public Collection<Coupon> getAllPurchasedCoupons(Customer customer) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		// "SELECT CPN.* FROM COUPON CPN INNER JOIN CUSTOMER_COUPON CC ON CPN.ID = CC.COUPON_ID AND CC.CUST_ID = ?"
		String selectSQL = "SELECT CPN.* FROM " + CouponDbNames.COUPON
				+ " CPN INNER JOIN " + CouponDbNames.CUSTOMER_COUPON
				+ " CC ON CPN." + CouponDbNames.COUPON_ID + " = CC."
				+ CouponDbNames.CUSTOMER_COUPON_COUPON_ID + " AND CC."
				+ CouponDbNames.CUSTOMER_COUPON_CUST_ID + " = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setLong(1, customer.getId());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Coupon coupon = new Coupon();

				coupon.setId(resultSet.getLong(CouponDbNames.COUPON_ID));
				coupon.setTitle(resultSet.getString(CouponDbNames.COUPON_TITLE));
				coupon.setStartDate(resultSet
						.getDate(CouponDbNames.COUPON_START_DATE));
				coupon.setEndDate(resultSet
						.getDate(CouponDbNames.COUPON_END_DATE));
				coupon.setAmount(resultSet.getInt(CouponDbNames.COUPON_AMOUNT));
				coupon.setType(CouponType.valueOf(resultSet
						.getString(CouponDbNames.COUPON_TYPE)));
				coupon.setMessage(resultSet
						.getString(CouponDbNames.COUPON_MESSAGE));
				coupon.setPrice(resultSet.getDouble(CouponDbNames.COUPON_PRICE));
				coupon.setImage(resultSet.getString(CouponDbNames.COUPON_IMAGE));

				couponList.add(coupon);

			}

			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}

		return couponList;
	}

	@Override
	public Collection<Coupon> getAllPurchasedCouponsByType(
			CouponType couponType, Customer customer) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		// "SELECT cpn.* FROM COUPON cpn INNER JOIN customer_coupon cc ON cpn.ID = cc.COUPON_ID AND cc.CUST_ID = ? AND cpn.TYPE = ? "
		String selectSQL = "SELECT cpn.* FROM " + CouponDbNames.COUPON
				+ " cpn INNER JOIN " + CouponDbNames.CUSTOMER_COUPON
				+ " cc ON cpn." + CouponDbNames.COUPON_ID + " = cc."
				+ CouponDbNames.CUSTOMER_COUPON_COUPON_ID + " AND cc."
				+ CouponDbNames.CUSTOMER_COUPON_CUST_ID + " = ? AND cpn."
				+ CouponDbNames.COUPON_TYPE + " = ? ";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setLong(1, customer.getId());
			preparedStatement.setString(2, couponType.name());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Coupon coupon = new Coupon();

				coupon.setId(resultSet.getLong(CouponDbNames.COUPON_ID));
				coupon.setTitle(resultSet.getString(CouponDbNames.COUPON_TITLE));
				coupon.setStartDate(resultSet
						.getDate(CouponDbNames.COUPON_START_DATE));
				coupon.setEndDate(resultSet
						.getDate(CouponDbNames.COUPON_END_DATE));
				coupon.setAmount(resultSet.getInt(CouponDbNames.COUPON_AMOUNT));
				coupon.setType(CouponType.valueOf(resultSet
						.getString(CouponDbNames.COUPON_TYPE)));
				coupon.setMessage(resultSet
						.getString(CouponDbNames.COUPON_MESSAGE));
				coupon.setPrice(resultSet.getDouble(CouponDbNames.COUPON_PRICE));
				coupon.setImage(resultSet.getString(CouponDbNames.COUPON_IMAGE));

				couponList.add(coupon);

			}

			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}

		return couponList;
	}

	@Override
	public Collection<Coupon> getAllPurchasedCouponsByPrice(Double price,
			Customer customer) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		// "SELECT cpn.* FROM COUPON cpn INNER JOIN customer_coupon cc ON cpn.ID = cc.COUPON_ID AND cc.CUST_ID = ? AND cpn.PRICE = ?"
		String selectSQL = "SELECT cpn.* FROM " + CouponDbNames.COUPON
				+ " cpn INNER JOIN " + CouponDbNames.CUSTOMER_COUPON
				+ " cc ON cpn." + CouponDbNames.COUPON_ID + " = cc."
				+ CouponDbNames.CUSTOMER_COUPON_COUPON_ID + " AND cc."
				+ CouponDbNames.CUSTOMER_COUPON_CUST_ID + " = ? AND cpn."
				+ CouponDbNames.COUPON_PRICE + " = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);

			preparedStatement.setLong(1, customer.getId());
			preparedStatement.setDouble(2, price);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Coupon coupon = new Coupon();

				coupon.setId(resultSet.getLong(CouponDbNames.COUPON_ID));
				coupon.setTitle(resultSet.getString(CouponDbNames.COUPON_TITLE));
				coupon.setStartDate(resultSet
						.getDate(CouponDbNames.COUPON_START_DATE));
				coupon.setEndDate(resultSet
						.getDate(CouponDbNames.COUPON_END_DATE));
				coupon.setAmount(resultSet.getInt(CouponDbNames.COUPON_AMOUNT));
				coupon.setType(CouponType.valueOf(resultSet
						.getString(CouponDbNames.COUPON_TYPE)));
				coupon.setMessage(resultSet
						.getString(CouponDbNames.COUPON_MESSAGE));
				coupon.setPrice(resultSet.getDouble(CouponDbNames.COUPON_PRICE));
				coupon.setImage(resultSet.getString(CouponDbNames.COUPON_IMAGE));

				couponList.add(coupon);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null && connection != null) {
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
	public boolean login(String custName, String password) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		// TODO: some sort of encyption should be added
		// "SELECT CUST_NAME, PASSWORD FROM CUSTOMER"
		String selectSQL = "SELECT " + CouponDbNames.CUSTOMER_CUST_NAME + ", "
				+ CouponDbNames.CUSTOMER_PASSWORD + " FROM "
				+ CouponDbNames.CUSTOMER;

		try {

			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String name = resultSet
						.getString(CouponDbNames.CUSTOMER_CUST_NAME);
				String pwd = resultSet
						.getString(CouponDbNames.CUSTOMER_PASSWORD);

				if (custName.equals(name) && password.equals(pwd))
					return true;

			}

			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}
		return false;
	}

}
