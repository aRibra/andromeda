package com.couponsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

import com.couponsystem.beans.Coupon;
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
		
		Timer time = new Timer(); // Instantiate Timer Object
		CouponTask st = new CouponTask(); // Instantiate SheduledTask class
		time.schedule (st, 0, 1000); // Create Repetitively task for every 1 secs
		
		//get coupons
		Collection<Coupon> couponList = couponDao.getAllCoupons();
		
		for(Coupon coupon:couponList){
			Date today = new Date();
			Date endDate = coupon.getEndDate();
			
			if(endDate.compareTo(today)<0){
				
				PreparedStatement preparedStatement;
				
				String deleteSQLQuery = "DELETE FROM " + CouponDbNames.COUPON
						+ " WHERE " + CouponDbNames.COUPON_ID + " = ?";
				
				try {
					preparedStatement = connection.prepareStatement(deleteSQLQuery);
					preparedStatement.setLong(1, coupon.getId());
					preparedStatement.executeUpdate();

					System.out.println("Record: " + coupon.getId()
							+ " is deleted from COUPON table!");

				} catch (SQLException e) {

					System.out.println(e.getMessage());

				} 
				
			}
			
		}
		
	}

	public void stopTask() {
		
		this.quit = true;
		
	}

}


class CouponTask extends TimerTask {
 
	Date now; // to display current time
 
	// Add your task here
	public void run() {
		now = new Date(); // initialize date
		System.out.println("Time is :" + now); // Display current time
	}
}
