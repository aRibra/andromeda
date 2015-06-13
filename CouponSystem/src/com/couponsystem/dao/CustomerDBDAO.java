package com.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.couponsystem.CouponSystem;
import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.beans.Customer;
import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.connection.DBConnection;
import com.couponsystem.exceptions.CouponSystemException;

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

		String insertSQLQuery = "INSERT INTO CUSTOMER"
				+ "(CUST_NAME, PASSWORD, CLIENT_TYPE) VALUES" + "(?,?,?)";

		try {

			preparedStatement = connection.prepareStatement(insertSQLQuery);

			preparedStatement.setString(1, customer.getCustomerName());
			preparedStatement.setString(2, customer.getClientPassword());
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

		String deleteSQLQuery = "DELETE FROM CUSTOMER WHERE ID = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQLQuery);

			preparedStatement.setLong(1, customer.getClientId());
			preparedStatement.executeUpdate();

			System.out.println("Record: " + customer.getClientId()
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

		String updateSQL = "UPDATE CUSTOMER SET CUST_NAME = ?, PASSWORD = ? WHERE ID = ?";

		try {
			preparedStatement = connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, customer.getCustomerName());
			preparedStatement.setString(2, customer.getClientPassword());
			preparedStatement.setLong(3, customer.getClientId());
			preparedStatement.executeUpdate();

			System.out.println("CUSTOMER record ID: " + customer.getClientId()
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
	public Customer getCustomer(int id) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		Customer customer = new Customer();

		String selectSQL = "SELECT ID, CUST_NAME, PASSWORD, CLIENT_TYPE FROM CUSTOMER WHERE ID = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				customer.setClientId(resultSet.getLong("ID"));
				customer.setClientName(resultSet.getString("CUST_NAME"));
				customer.setClientPassword(resultSet.getString("PASSWORD"));
				customer.setClientType(ClientType.valueOf(resultSet
						.getString("CLIENT_TYPE")));

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
	public Collection<Customer> getAllCustomers() throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		Collection<Customer> customersList = new ArrayList<>();

		String selectSQLQuery = "SELECT ID, CUST_NAME, PASSWORD, CLIENT_TYPE FROM CUSTOMER";

		try {
			PreparedStatement preparedStatement = null;
			preparedStatement = connection.prepareStatement(selectSQLQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Customer customer = new Customer();

				customer.setClientId(resultSet.getLong("ID"));
				customer.setClientName(resultSet.getString("CUST_NAME"));
				customer.setClientPassword(resultSet.getString("PASSWORD"));
				customer.setClientType(ClientType.valueOf(resultSet
						.getString("CLIENT_TYPE")));

				customersList.add(customer);

			}

			preparedStatement.close();

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}
		return customersList;
	}

	// DONE - TODO: IBRAHIM
	@Override
	public Collection<Coupon> getCoupons(Customer customer)
			throws CouponSystemException {

		// retrieve coupons for a specific customer

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQL = "SELECT CPN.* FROM COUPON CPN INNER JOIN CUSTOMER_COUPON CC ON CC.COUPON_ID = CPN.ID AND CC.CUST_ID = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setLong(1, customer.getClientId());

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
				coupon.setMessage("MESSAGE");
				coupon.setImage("IMAGE");

				couponList.add(coupon);

			}

			preparedStatement.close();

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}

		return couponList;
	}

	@Override
	public void purchaseCoupon(Coupon coupon, Customer customer)
			throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO CUSTOMER_COUPON (CUST_ID, COUPON_ID) VALUES(?,?)";

		try {

			preparedStatement = connection.prepareStatement(insertSQL);

			preparedStatement.setLong(1, coupon.getId());
			preparedStatement.setLong(2, customer.getClientId());

			preparedStatement.executeUpdate();

			System.out
					.println("Coupon Purchase process completed successfully!");

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (preparedStatement != null && connection != null) {
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
	public Collection<Coupon> getAllPurchasedCoupons(Customer customer)
			throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQL = "SELECT CPN.* FROM COUPON CPN INNER JOIN CUSTOMER_COUPON CC ON CPN.ID = CC.COUPON_ID AND CC.CUST_ID = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setLong(1, customer.getClientId());

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

			preparedStatement.close();

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}

		return couponList;
	}

	@Override
	public Collection<Coupon> getAllPurchasedCouponsByType(
			CouponType couponType, Customer customer)
			throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQL = "SELECT cpn.* FROM COUPON cpn INNER JOIN customer_coupon cc ON cpn.ID = cc.COUPON_ID AND cc.CUST_ID = ? AND cpn.TYPE = ? ";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setLong(1, customer.getClientId());
			preparedStatement.setString(2, couponType.name());

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

			preparedStatement.close();

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}

		return couponList;
	}

	@Override
	public Collection<Coupon> getAllPurchasedCouponsByPrice(Double price,
			Customer customer) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQL = "SELECT cpn.* FROM COUPON cpn INNER JOIN customer_coupon cc ON cpn.ID = cc.COUPON_ID AND cc.CUST_ID = ? AND cpn.PRICE = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);

			preparedStatement.setLong(1, customer.getClientId());
			preparedStatement.setDouble(2, price);

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

			if (preparedStatement != null && connection != null) {
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
	public Customer login(String custName, String password)
			throws CouponSystemException {
		// TODO: return Customer object - change selectSQL query to return all
		// customer data

		Customer customer = null;
		Connection connection = null;

		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		// TODO: some sort of encyption should be added
		String selectSQL = "SELECT ID, CUST_NAME, PASSWORD FROM CUSTOMER";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				long id = resultSet.getLong("ID");
				String name = resultSet.getString("CUST_NAME");
				String pwd = resultSet.getString("PASSWORD");

				if (custName.equals(name) && password.equals(pwd)) {
					customer = new Customer(id, name);
				}

			}

			preparedStatement.close();

			return customer;

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}
	}

}
