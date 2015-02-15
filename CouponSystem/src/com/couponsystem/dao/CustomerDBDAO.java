package com.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.beans.Customer;
import com.couponsystem.connection.DBConnection;

public class CustomerDBDAO implements CustomerDAO {

	// private Connection pool;

	public CustomerDBDAO() {
		// this.pool = ConnectionPool.getInstance().getConnection();
	}

	@Override
	public void createCustomer(Customer customer) {

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		String insertSQLQuery = "INSERT INTO CUSTOMER"
				+ "(CUST_NAME, PASSWORD, CLIENT_TYPE) VALUES" + "(?,?,?)";

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
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void removeCustomer(Customer customer) {

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		String deleteSQLQuery = "DELETE FROM CUSTOMER WHERE ID = ?";

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
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void updateCustomer(Customer customer) {

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE CUSTOMER SET CUST_NAME = ?, PASSWORD = ? WHERE ID = ?";

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
				try {

					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public Customer getCustomer(int id) {

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		Customer customer = new Customer();

		String selectSQL = "SELECT ID, CUST_NAME, PASSWORD, CLIENT_TYPE FROM CUSTOMER WHERE ID = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				customer.setId(resultSet.getLong("ID"));
				customer.setCustomerName(resultSet.getString("CUST_NAME"));
				customer.setPassword(resultSet.getString("PASSWORD"));
				customer.setClientType(ClientType.valueOf(resultSet
						.getString("CLIENT_TYPE")));

			}

			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return customer;

	}

	// DONE - TODO: company collection??????? should be Customer. Yes, u r right
	// bitch!
	@Override
	public Collection<Customer> getAllCustomers() {

		Connection connection = new DBConnection().getDBConnection();

		Collection<Customer> customersList = new ArrayList<>();

		String selectSQLQuery = "SELECT ID, CUST_NAME, PASSWORD, CLIENT_TYPE FROM CUSTOMER";

		try {
			PreparedStatement preparedStatement = null;
			preparedStatement = connection.prepareStatement(selectSQLQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Customer customer = new Customer();

				customer.setId(resultSet.getLong("ID"));
				customer.setCustomerName(resultSet.getString("CUST_NAME"));
				customer.setPassword(resultSet.getString("PASSWORD"));
				customer.setClientType(ClientType.valueOf(resultSet
						.getString("CLIENT_TYPE")));

				customersList.add(customer);

			}

			preparedStatement.close();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return customersList;
	}

	// DONE - TODO: IBRAHIM
	@Override
	public Collection<Coupon> getCoupons(Customer customer) {

		// retrieve coupons for a specific customer

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQL = "SELECT CPN.* FROM COUPON CPN INNER JOIN CUSTOMER_COUPON CC ON CC.COUPON_ID = CPN.ID AND CC.CUST_ID = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setLong(1, customer.getId());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Coupon coupon = new Coupon();

				coupon.setId(resultSet.getLong("ID"));
				coupon.setTitle(resultSet.getString("TITLE"));
				coupon.setStartDate(resultSet.getDate("START_DATE"));
				coupon.setEndDate(resultSet.getDate("END_DATE"));
				coupon.setAmount(resultSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setMessage("MESSAGE");
				coupon.setImage("IMAGE");

				couponList.add(coupon);

			}

			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return couponList;
	}

	@Override
	public void purchaseCoupon(Coupon coupon, Customer customer) {

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO CUSTOMER_COUPON (CUST_ID, COUPON_ID) VALUES(?,?)";

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
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public Collection<Coupon> getAllPurchasedCoupons(Customer customer) {

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQL = "SELECT CPN.* FROM COUPON CPN INNER JOIN CUSTOMER_COUPON CC ON CPN.ID = CC.COUPON_ID AND CC.CUST_ID = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setLong(1, customer.getId());

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

			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return couponList;
	}

	@Override
	public Collection<Coupon> getAllPurchasedCouponsByType(
			CouponType couponType, Customer customer) {

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQL = "SELECT cpn.* FROM COUPON cpn INNER JOIN customer_coupon cc ON cpn.ID = cc.COUPON_ID AND cc.CUST_ID = ? AND cpn.TYPE = ? ";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setLong(1, customer.getId());
			preparedStatement.setString(2, couponType.name());

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

			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return couponList;
	}

	@Override
	public Collection<Coupon> getAllPurchasedCouponsByPrice(Double price,
			Customer customer) {

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQL = "SELECT cpn.* FROM COUPON cpn INNER JOIN customer_coupon cc ON cpn.ID = cc.COUPON_ID AND cc.CUST_ID = ? AND cpn.PRICE = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);

			preparedStatement.setLong(1, customer.getId());
			preparedStatement.setDouble(2, price);

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

			if (preparedStatement != null && connection != null) {
				try {
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return couponList;
	}

	@Override
	public boolean login(String custName, String password) {

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		// TODO: some sort of encyption should be added
		String selectSQL = "SELECT CUST_NAME, PASSWORD FROM CUSTOMER";

		try {

			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String name = resultSet.getString("CUST_NAME");
				String pwd = resultSet.getString("PASSWORD");

				if (custName.equals(name) && password.equals(pwd))
					return true;

			}

			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

}
