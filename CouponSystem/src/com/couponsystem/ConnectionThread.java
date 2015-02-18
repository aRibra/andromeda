package com.couponsystem;

import java.sql.*;

import com.couponsystem.connection.ConnectionPool;

public class ConnectionThread extends Thread {

	private ConnectionPool connectionPool;
	private Connection connection;
	private int connectionNumber;
	private boolean timeToQuit = false;

	public ConnectionThread(ConnectionPool connectionPool, int connectionNumber) {
		this.connectionNumber = connectionNumber;
		this.connectionPool = connectionPool;
	}

	public void run() {

		try {

			this.connection = connectionPool.getConnection();

			if (connection != null) {
				System.out.println("[ConnectionThread] has connection number "
						+ this.connectionNumber);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void stopRunning() {
		this.timeToQuit = true;
		connectionPool.releaseConnection(connection);
	}

	@Override
	public String toString() {
		return "[Thread #:" + this.connectionNumber + "] ";
	}

}