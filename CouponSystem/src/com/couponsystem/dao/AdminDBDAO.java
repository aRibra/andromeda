package com.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.connection.DBConnection;

public class AdminDBDAO implements AdminDAO {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	public AdminDBDAO() {

	}

	@Override
	public boolean login(String adminName, String password) {

		// connectionPool.getConnection();
		// make a new thread for every process

		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String selectSQL = "SELECT ADMIN_NAME, PASSWORD FROM ADMIN";

		try {
			PreparedStatement preparedStatement = null;
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String name = resultSet.getString("ADMIN_NAME");
				String pwd = resultSet.getString("PASSWORD");

				if (adminName.equals(name) && password.equals(pwd))
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
