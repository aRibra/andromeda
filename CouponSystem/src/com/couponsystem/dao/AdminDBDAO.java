package com.couponsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.couponsystem.beans.Admin;
import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.CouponType;
import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.connection.DBConnection;
import com.couponsystem.helper.classes.ClientBucket;

public class AdminDBDAO implements AdminDAO {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	public AdminDBDAO() {

	}

	@Override
	public Admin login(String adminName, String password) {
		// TODO: return Admin object - change selectSQL query to return all
		// admin data

		Admin admin = null;
		Connection connection = null;

		PreparedStatement preparedStatement = null;
		
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String selectSQL = "SELECT ID, ADMIN_NAME, CLIENT_TYPE, PASSWORD FROM ADMIN";

		try {
			
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				long id = resultSet.getLong("ID");
				String name = resultSet.getString("ADMIN_NAME");
				String pwd = resultSet.getString("PASSWORD");
				ClientType type = ClientType.valueOf(resultSet
						.getString("CLIENT_TYPE"));

				if (adminName.equals(name) && password.equals(pwd))
					admin = new Admin(id, name);
			
			}
			
			preparedStatement.close();
			
			return admin;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				
				connectionPool.releaseConnection(connection);
			}
		}
		return null;
	}
}
