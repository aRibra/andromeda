package com.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

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

		String insertSQLQuery = "INSERT INTO COMPANY"
				+ "(COMP_NAME, PASSWORD, EMAIL, CLIENT_TYPE) VALUES"
				+ "(?,?,?,?)";

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

		String deleteSQLQuery = "DELETE FROM COMPANY WHERE ID = ?";

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

		String updateSQLQuery = "UPDATE COMPANY SET COMP_NAME = ?, PASSWORD = ?, EMAIL = ? WHERE ID = ?";

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

		String selectSQLQuery = "SELECT ID, COMP_NAME, PASSWORD, EMAIL, CLIENT_TYPE FROM COMPANY WHERE ID = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQLQuery);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				retrievedCompany.setId(resultSet.getLong("ID"));
				retrievedCompany.setCompanyName(resultSet
						.getString("COMP_NAME"));
				retrievedCompany.setPassword(resultSet.getString("PASSWORD"));
				retrievedCompany.setEmail(resultSet.getString("EMAIL"));
				retrievedCompany.setClientType(ClientType.valueOf(resultSet
						.getString("CLIENT_TYPE")));

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

		String selectSQLQuery = "SELECT ID, COMP_NAME, PASSWORD, EMAIL, CLIENT_TYPE FROM COMPANY";

		try {

			preparedStatement = connection.prepareStatement(selectSQLQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Company company = new Company();

				company.setId(resultSet.getLong("ID"));
				company.setCompanyName(resultSet.getString("COMP_NAME"));
				company.setPassword(resultSet.getString("PASSWORD"));
				company.setEmail(resultSet.getString("EMAIL"));
				company.setClientType(ClientType.valueOf(resultSet
						.getString("CLIENT_TYPE")));

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

		String selectSQLQuery = "SELECT CPN.* FROM COUPON CPN INNER JOIN COMPANY_COUPON CC ON CPN.ID = CC.COUPON_ID AND CC.COMP_ID = ?";

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

			String selectSQLQuery = "SELECT COMP_NAME, PASSWORD FROM COMPANY";

			ResultSet resultSet = statement.executeQuery(selectSQLQuery);

			while (resultSet.next()) {

				String name = resultSet.getString("COMP_NAME");
				String pwd = resultSet.getString("PASSWORD");

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
