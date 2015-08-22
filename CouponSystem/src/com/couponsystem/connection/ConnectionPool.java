package com.couponsystem.connection;

import java.sql.*;
import java.util.*;

public class ConnectionPool {

	private static ConnectionPool connectionPool = new ConnectionPool();

	private List<Connection> availableConnections, busyConnections;
	private int initialNumberOfConnections = 10;
	private int maxNumberOfConnections;
	private boolean connectionPending = false;
	private boolean waitIfBusy = true;

	// private static int numberOfConnectionsToAdd = 10;

	private ConnectionPool() {
		availableConnections = new ArrayList<Connection>(
				initialNumberOfConnections);
		busyConnections = new ArrayList<Connection>();

		for (int i = 0; i < initialNumberOfConnections; i++) {
			Connection connection = new DBConnection().getDBConnection();
			availableConnections.add(connection);
		}
	}

	public static ConnectionPool getInstance() {
		return connectionPool;
	}

	public synchronized Connection getConnection() throws SQLException {

		if (!availableConnections.isEmpty()) {
			int lastIndex = availableConnections.size() - 1;
			Connection connection = (Connection) availableConnections
					.remove(lastIndex);

			// check that the connection is not closed() (e.g timed out)
			if (connection.isClosed()) {
				// Free up a spot for anybody waiting then retry getting a
				// connection
				System.out
						.println("connection is closed. trying to get a new connection");
				notifyAll();
				return getConnection();
			} else {
				busyConnections.add(connection);
				return connection;
			}

		} else {

			if ((totalConnections() < maxNumberOfConnections)
					&& !connectionPending) {
				System.out.println("now you have a connection in hand.");
				return makeNewConnection();
			}

			try {
				System.out
						.println(Thread.currentThread().toString()
								+ " :: maximum connections reached, waiting for a connection to be released.");
				wait();
			} catch (InterruptedException ie) {
			}
			// Someone freed up a connection, so try again.
			return getConnection();

		}
	}

	public synchronized void releaseConnection(Connection connection) {
		busyConnections.remove(connection);
		availableConnections.add(connection);

		System.out.println("Connection released.");

		// Wake up threads that are waiting for a connection
		notifyAll();
	}

	public synchronized void returnConnection() {

	}

	public void closePoolConnections() {
		closeConnections(availableConnections);
		availableConnections = new ArrayList<Connection>();

		closeConnections(busyConnections);
		busyConnections = new ArrayList<Connection>();
	}

	private void closeConnections(Collection<Connection> connections) {
		// no matter if there is active connections, close them all.
		for (Connection connection : connections) {
			try {
				if (!connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private Connection makeNewConnection() {
		connectionPending = true;

		Connection connection = new DBConnection().getDBConnection();
		busyConnections.add(connection);

		connectionPending = false;
		return connection;
	}

	public synchronized int totalConnections() {
		return (availableConnections.size() + busyConnections.size());
	}

	public List<Connection> getBusyConnections() {
		return this.busyConnections;
	}

	public void maxNumberOfConnections(int maxNumberOfConnections) {
		//sets max number of connections
		this.maxNumberOfConnections = maxNumberOfConnections;
		
	}

}
