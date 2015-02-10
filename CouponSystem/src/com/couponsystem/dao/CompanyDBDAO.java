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
import com.couponsystem.connection.DBConnection;

public class CompanyDBDAO implements CompanyDAO {

	// private Connection pool;

	public CompanyDBDAO() {
		// this.pool = ConnectionPool.getInstance().getConnection();
	}

	@Override
	public void createCompany(Company company) {

		Connection connection = new DBConnection().getDBConnection();
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
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void removeCompany(Company company) {

		Connection connection = new DBConnection().getDBConnection();
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
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void updateCompany(Company company) {

		Connection connection = new DBConnection().getDBConnection();
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

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		Company retrievedCompany = new Company();

		String selectSQLQuery = "SELECT ID, COMP_NAME, PASSWORD, EMAIL, CLIENT_TYPE FROM COMPANY WHERE ID = ?";

		try {
			preparedStatement = connection.prepareStatement(selectSQLQuery);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				retrievedCompany.setId(resultSet.getLong("ID"));
				retrievedCompany.setCompanyName(resultSet.getString("COMP_NAME"));
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
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return retrievedCompany;
	}

	@Override
	public Collection<Company> getAllCompaines() {

		Connection connection = new DBConnection().getDBConnection();
		PreparedStatement preparedStatement = null;

		Collection<Company> cmpList = new ArrayList<>();

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

				cmpList.add(company);

				preparedStatement.close();
			}

			resultSet.close();

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
		return cmpList;
	}

	// TODO: IBRAHIM
	@Override
	public Collection<Coupon> getCoupons(Company company) {

		// returns coupons for a specific company

		return null;
	}

	@Override
	public boolean login(String compName, String password) {

		boolean okToLogin = false;
		Connection connection = new DBConnection().getDBConnection();

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
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				return okToLogin;
			}
		}

		return okToLogin;
	}
}
