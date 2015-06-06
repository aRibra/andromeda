package com.couponsystem.dailytask;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.couponsystem.dao.CouponDAO;
import com.couponsystem.dao.CouponDBDAO;

public class DailyCouponExpirationTask implements Runnable {

	CouponDAO couponDao;
	ScheduledExecutorService scheduler;

	public DailyCouponExpirationTask() {

		couponDao = new CouponDBDAO();

	}

	@Override
	public void run() {
		
		scheduler = Executors.newScheduledThreadPool(1);
		Date aDate = new Date();// Current date or parsed date; 
		 
		Calendar with = Calendar.getInstance();
		with.setTime(aDate);
		int hour = with.get(Calendar.HOUR_OF_DAY);
		int Minutes = with.get(Calendar.MINUTE);
		 
		int MinutesPassed12AM = hour * 60 + Minutes;
		int MinutesAt24AM = 24 * 60;
		int OneDayMinutes = 24 * 60;
		long DelayInMinutes = MinutesPassed12AM <= MinutesAt24AM ? MinutesAt24AM - MinutesPassed12AM : OneDayMinutes - (MinutesPassed12AM - MinutesAt24AM);
		 
		scheduler.scheduleAtFixedRate(new DeleteExpiredCouponsThread(), DelayInMinutes, OneDayMinutes, TimeUnit.MINUTES);	

	}

	public void stopTask() {
		
		scheduler.shutdown();
		
	}

}
