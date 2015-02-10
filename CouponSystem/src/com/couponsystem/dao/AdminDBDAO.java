package com.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.couponsystem.connection.DBConnection;

public class AdminDBDAO implements AdminDAO {

	@Override
	public boolean login(String adminName, String password) {

		Connection connection = new DBConnection().getDBConnection();

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
