package com.couponsystem.connection;

import java.sql.*;
import java.util.*;

public class ConnectionPool {

	private DBConnection dbConnection = new DBConnection();
	private Set<Connection> connections;
	private static ConnectionPool connectionPool;

	private ConnectionPool() {
		this.connections = new HashSet<Connection>();
	}

	public static ConnectionPool getInstance() {
		return connectionPool;
	}

	public synchronized Connection getConnection() {
		while (connections.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		connections.remove(dbConnection.getDBConnection());
		return connections.iterator().next();

	}

	public synchronized void returnConnections() {
		connections.add(dbConnection.getDBConnection());
		this.notify();
	}

	public void closeAllConnections() {

		Iterator<Connection> it = connections.iterator();

		while (it.hasNext()) {
			try {
				it.next().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
