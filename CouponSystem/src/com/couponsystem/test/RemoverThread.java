package com.couponsystem.test;

import java.sql.Connection;
import java.util.List;

import com.couponsystem.connection.ConnectionPool;

public class RemoverThread extends Thread {

	private ConnectionPool connectionPool;

	public RemoverThread(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public void run() {
		try {
			sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < connectionPool.getBusyConnections().size(); i++) {
			connectionPool.releaseConnection(connectionPool
					.getBusyConnections().get(i));

			try {
				sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
