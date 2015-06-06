package com.couponsystem.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public Connection getDBConnection() {
		String driver = "com.mysql.jdbc.Driver";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection = null;

		try {
			String host = "jdbc:mysql://localhost:3306/Coupon_DB";
			connection = DriverManager.getConnection(host, "root", "");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
		return connection;
	}

}
