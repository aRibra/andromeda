package com.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.couponsystem.CouponDbNames;
import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.connection.DBConnection;

public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	public CompanyDBDAO() {

	}

	@Override
	public void createCompany(Company company) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		String insertSQLQuery = "INSERT INTO " + CouponDbNames.COMPANY + "("
				+ CouponDbNames.COMPANY_COMP_NAME + ", "
				+ CouponDbNames.COMPANY_PASSWORD + ", "
				+ CouponDbNames.COMPANY_EMAIL + ", "
				+ CouponDbNames.COMPANY_CLIENT_TYPE + ") VALUES" + "(?,?,?,?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQLQuery);

			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());
			preparedStatement.setString(4, company.getClientType().name());

			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into COMPANY table!");

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
	public void removeCompany(Company company) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		String deleteSQLQuery = "DELETE FROM " + CouponDbNames.COMPANY
				+ " WHERE " + CouponDbNames.COMPANY_ID + " = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQLQuery);
			preparedStatement.setLong(1, company.getId());
			preparedStatement.executeUpdate();

			System.out.println("Record: " + company.getId()
					+ " is deleted from COMPANY table!");

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
	public void updateCompany(Company company) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		String updateSQLQuery = "UPDATE " + CouponDbNames.COMPANY + " SET "
				+ CouponDbNames.COMPANY_COMP_NAME + " = ?, "
				+ CouponDbNames.COMPANY_PASSWORD + " = ?, "
				+ CouponDbNames.COMPANY_EMAIL + " = ? WHERE "
				+ CouponDbNames.COMPANY_ID + " = ?";

		try {
			preparedStatement = connection.prepareStatement(updateSQLQuery);
			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());
			preparedStatement.setLong(4, company.getId());
			preparedStatement.executeUpdate();

			System.out.println("Company record ID: " + company.getId()
					+ " was updated! ");

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
	public Company getCompany(int id) {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement preparedStatement = null;

		Company retrievedCompany = new Company();

		String selectSQLQuery = "SELECT " + CouponDbNames.COMPANY_ID + ", "
				+ CouponDbNames.COMPANY_COMP_NAME + ", "
				+ CouponDbNames.COMPANY_PASSWORD + ", "
				+ CouponDbNames.COMPANY_EMAIL + ", "
				+ CouponDbNames.COMPANY_CLIENT_TYPE + " FROM "
				+ CouponDbNames.COMPANY + " WHERE " + CouponDbNames.COMPANY_ID
				+ " = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQLQuery);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				retrievedCompany.setId(resultSet
						.getLong(CouponDbNames.COMPANY_ID));
				retrievedCompany.setCompanyName(resultSet
						.getString(CouponDbNames.COMPANY_COMP_NAME));
				retrievedCompany.setPassword(resultSet
						.getString(CouponDbNames.COMPANY_PASSWORD));
				retrievedCompany.setEmail(resultSet
						.getString(CouponDbNames.COMPANY_EMAIL));
				retrievedCompany.setClientType(ClientType.valueOf(resultSet
						.getString(CouponDbNames.COMPANY_CLIENT_TYPE)));

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
		return retrievedCompany;
	}

	@Override
	public Collection<Company> getAllCompaines() {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		PreparedStatement preparedStatement = null;

		Collection<Company> companyList = new ArrayList<>();

		String selectSQLQuery = "SELECT " + CouponDbNames.COMPANY_ID + ", "
				+ CouponDbNames.COMPANY_COMP_NAME + ", "
				+ CouponDbNames.COMPANY_PASSWORD + ", "
				+ CouponDbNames.COMPANY_EMAIL + ", "
				+ CouponDbNames.COMPANY_CLIENT_TYPE + " FROM "
				+ CouponDbNames.COMPANY;

		try {

			preparedStatement = connection.prepareStatement(selectSQLQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Company company = new Company();

				company.setId(resultSet.getLong(CouponDbNames.COMPANY_ID));
				company.setCompanyName(resultSet
						.getString(CouponDbNames.COMPANY_COMP_NAME));
				company.setPassword(resultSet
						.getString(CouponDbNames.COMPANY_PASSWORD));
				company.setEmail(resultSet
						.getString(CouponDbNames.COMPANY_EMAIL));
				company.setClientType(ClientType.valueOf(resultSet
						.getString(CouponDbNames.COMPANY_CLIENT_TYPE)));

				companyList.add(company);

			}

			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}
		return companyList;
	}

	// DONE TODO: IBRAHIM
	@Override
	public Collection<Coupon> getCoupons(Company company) {

		// returns coupons for a specific company
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<>();

		String selectSQLQuery = "SELECT CPN.* FROM "
				+ CouponDbNames.COMPANY_COUPON + " CPN INNER JOIN "
				+ CouponDbNames.COMPANY_COUPON + " CC ON CPN."
				+ CouponDbNames.COUPON_ID + " = CC."
				+ CouponDbNames.COMPANY_COUPON_COUPON_ID + " AND CC."
				+ CouponDbNames.COMPANY_COUPON_COMP_ID + " = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQLQuery);
			preparedStatement.setLong(1, company.getId());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Coupon coupon = new Coupon();

				coupon.setId(resultSet.getLong(CouponDbNames.COUPON_ID));
				coupon.setTitle(resultSet.getString(CouponDbNames.COUPON_TITLE));
				coupon.setStartDate(resultSet
						.getDate(CouponDbNames.COUPON_START_DATE));
				coupon.setEndDate(resultSet
						.getDate(CouponDbNames.COUPON_START_DATE));
				coupon.setAmount(resultSet.getInt(CouponDbNames.COUPON_AMOUNT));
				coupon.setType(CouponType.valueOf(resultSet
						.getString(CouponDbNames.COUPON_TYPE)));
				coupon.setMessage("MESSAGE");
				coupon.setImage("IMAGE");

				couponList.add(coupon);
			}

			resultSet.close();
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
	public boolean login(String compName, String password) {

		boolean okToLogin = false;
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {

			Statement statement = connection.createStatement();

			String selectSQLQuery = "SELECT " + CouponDbNames.COMPANY_COMP_NAME
					+ ", " + CouponDbNames.COMPANY_PASSWORD + " FROM "
					+ CouponDbNames.COMPANY;

			ResultSet resultSet = statement.executeQuery(selectSQLQuery);

			while (resultSet.next()) {

				String name = resultSet
						.getString(CouponDbNames.COMPANY_COMP_NAME);
				String pwd = resultSet
						.getString(CouponDbNames.COMPANY_PASSWORD);

				if (compName.equals(name) && password.equals(pwd)) {
					okToLogin = true;
					break;
				}
			}

			resultSet.close();
			statement.close();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);

				return okToLogin;
			}
		}

		return okToLogin;
	}
}
