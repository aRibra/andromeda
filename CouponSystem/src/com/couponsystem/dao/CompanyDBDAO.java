package com.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import com.couponsystem.CouponSystem;
import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.connection.DBConnection;
import com.couponsystem.exceptions.CouponSystemException;

public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	public CompanyDBDAO() {

	}

	@Override
	public void createCompany(Company company) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		String insertSQLQuery = "INSERT INTO COMPANY"
				+ "(COMP_NAME, PASSWORD, EMAIL, CLIENT_TYPE) VALUES"
				+ "(?,?,?,?)";

		try {
			preparedStatement = connection.prepareStatement(insertSQLQuery);

			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getClientPassword());
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
					String stackTrace = CouponSystem.getStackTraceAsString(e);
					throw new CouponSystemException(e.getMessage(), stackTrace);
				}
			}
		}
	}

	@Override
	public void removeCompany(int id) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		String deleteSQLQuery = "DELETE FROM COMPANY WHERE ID = ?";

		try {
			preparedStatement = connection.prepareStatement(deleteSQLQuery);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();

			System.out.println("Record: " + id
					+ " is deleted from COMPANY table!");

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
	public void updateCompany(Company company) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		String updateSQLQuery = "UPDATE COMPANY SET COMP_NAME = ?, EMAIL = ? WHERE ID = ?";

		try {
			preparedStatement = connection.prepareStatement(updateSQLQuery);
			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getEmail());
			preparedStatement.setLong(3, company.getClientId());
			preparedStatement.executeUpdate();

			System.out.println("Company record ID: " + company.getClientId()
					+ " was updated! ");

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (preparedStatement != null && connection != null) {
				try {
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					String stackTrace = CouponSystem.getStackTraceAsString(e);
					throw new CouponSystemException(e.getMessage(), stackTrace);
				}
			}
		}

	}

	@Override
	public Company getCompany(int id) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		PreparedStatement preparedStatement = null;

		Company retrievedCompany = new Company();

		String selectSQLQuery = "SELECT ID, COMP_NAME, EMAIL, CLIENT_TYPE FROM COMPANY WHERE ID = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQLQuery);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				retrievedCompany.setClientId(resultSet.getLong("ID"));
				retrievedCompany.setCompanyName(resultSet
						.getString("COMP_NAME"));
				retrievedCompany.setEmail(resultSet.getString("EMAIL"));
				retrievedCompany.setClientType(ClientType.valueOf(resultSet
						.getString("CLIENT_TYPE")));

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
		return retrievedCompany;
	}

	@Override
	public Collection<Company> getAllCompaines() throws CouponSystemException {

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}
		PreparedStatement preparedStatement = null;

		Collection<Company> companyList = new ArrayList<Company>();

		String selectSQLQuery = "SELECT ID, COMP_NAME, EMAIL, CLIENT_TYPE FROM COMPANY";

		try {

			preparedStatement = connection.prepareStatement(selectSQLQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Company company = new Company();

				company.setClientId(resultSet.getLong("ID"));
				company.setCompanyName(resultSet.getString("COMP_NAME"));
				company.setEmail(resultSet.getString("EMAIL"));
				company.setClientType(ClientType.valueOf(resultSet
						.getString("CLIENT_TYPE")));

				companyList.add(company);

			}

			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}
		return companyList;
	}

	@Override
	public Collection<Coupon> getCoupons(int companyId)
			throws CouponSystemException {

		// returns coupons for a specific company
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}
		PreparedStatement preparedStatement = null;

		Collection<Coupon> couponList = new ArrayList<Coupon>();

		String selectSQLQuery = "SELECT CPN.ID, CPN.TITLE, DATE_FORMAT(CPN.START_DATE, \"%d/%l/%Y\") AS START_DATE, DATE_FORMAT(CPN.END_DATE, \"%d/%l/%Y\") AS END_DATE, CPN.AMOUNT, CPN.TYPE, CPN.PRICE, CPN.MESSAGE, CPN.IMAGE  FROM COUPON CPN INNER JOIN COMPANY_COUPON CC ON CPN.ID = CC.COUPON_ID AND CC.COMP_ID = ?";

		try {

			preparedStatement = connection.prepareStatement(selectSQLQuery);
			preparedStatement.setLong(1, companyId);

			ResultSet resultSet = preparedStatement.executeQuery();

			DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy",
					Locale.ENGLISH);

			while (resultSet.next()) {

				Coupon coupon = new Coupon();

				coupon.setId(resultSet.getLong("ID"));
				coupon.setTitle(resultSet.getString("TITLE"));

				java.util.Date date = dateFormatter.parse(resultSet
						.getString("START_DATE"));
				
				coupon.setStartDate(date);

				date = dateFormatter.parse(resultSet.getString("END_DATE"));
				coupon.setEndDate(date);

				coupon.setAmount(resultSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(resultSet.getString("TYPE")));
				coupon.setPrice(resultSet.getDouble("PRICE"));
				coupon.setMessage(resultSet.getString("MESSAGE"));
				coupon.setImage("IMAGE");

				couponList.add(coupon);
			}

			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {

			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);

		} catch (ParseException e) {
			e.printStackTrace();
		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}

		return couponList;
	}

	@Override
	public Company login(String compName, String password)
			throws CouponSystemException {
		// TODOs: return Company object - change selectSQL query to return all
		// company data

		boolean okToLogin = false;
		Company company = null;
		Connection connection = null;

		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			String stackTrace = CouponSystem.getStackTraceAsString(e);
			throw new CouponSystemException(e.getMessage(), stackTrace);
		}

		try {

			Statement statement = connection.createStatement();

			String selectSQLQuery = "SELECT ID, COMP_NAME, EMAIL, CLIENT_TYPE, PASSWORD FROM COMPANY";

			ResultSet resultSet = statement.executeQuery(selectSQLQuery);

			while (resultSet.next()) {

				long id = resultSet.getLong("ID");
				String name = resultSet.getString("COMP_NAME");
				String email = resultSet.getString("EMAIL");
				String pwd = resultSet.getString("PASSWORD");

				if (compName.equals(name) && password.equals(pwd)) {
					company = new Company(id, name, email);
					okToLogin = true;
					return company;
				}
			}

			resultSet.close();
			statement.close();

			return company;

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
