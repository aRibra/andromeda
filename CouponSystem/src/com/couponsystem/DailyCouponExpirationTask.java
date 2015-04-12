package com.couponsystem;

import java.sql.Connection;
import java.sql.SQLException;

import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.dao.CouponDAO;
import com.couponsystem.dao.CouponDBDAO;

public class DailyCouponExpirationTask implements Runnable {

	private boolean quit;
	private CouponDAO couponDao;
	private Connection connection;
	private ConnectionPool connectionPool;

	public DailyCouponExpirationTask() throws SQLException {

		this.quit = false;
		this.couponDao = new CouponDBDAO();
		this.connection = connectionPool.getConnection();

	}

	@Override
	public void run() {
		
		 
		
	}

	public void stopTask() {
		
		this.quit = true;
		
	}

}
