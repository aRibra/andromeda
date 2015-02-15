package com.couponsystem.connection;

import java.sql.*;
import java.util.*;

public class ConnectionPool {

	private static int initialNumberOfConnections;
	private Collection<Connection> connections;
	private static ConnectionPool connectionPool = new ConnectionPool();

	private ConnectionPool() {
		this.connections = new ArrayList<Connection>();
	}

	public static ConnectionPool getInstance() {
		return connectionPool;
	}

	public synchronized Connection getConnection() {
		return null;
	}

	public synchronized void returnConnections() {

	}

	public void closeAllConnections() {

	}
}
