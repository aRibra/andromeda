package com.couponsystem;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import com.couponsystem.beans.ClientType;
import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.dailytask.DailyCouponExpirationTask;
import com.couponsystem.dao.AdminDAO;
import com.couponsystem.dao.AdminDBDAO;
import com.couponsystem.dao.CompanyDAO;
import com.couponsystem.dao.CompanyDBDAO;
import com.couponsystem.dao.CouponDAO;
import com.couponsystem.dao.CustomerDAO;
import com.couponsystem.dao.CustomerDBDAO;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.exceptions.CouponSystemExceptionsProperties;
import com.couponsystem.facades.AdminFacade;
import com.couponsystem.facades.CompanyFacade;
import com.couponsystem.facades.CouponSystemClientFacade;
import com.couponsystem.facades.CustomerFacade;

public class CouponSystem {

	private AdminDAO adminDao;
	private CompanyDAO companyDao;
	private CouponDAO couponDao;
	private CustomerDAO customerDao;
	private DailyCouponExpirationTask dailyCouponExpirationTask;

	private static CouponSystem couponSystem = new CouponSystem();
	ConnectionPool connectionPool = ConnectionPool.getInstance();
	public static Map<String, String> couponSystemExceptions;

	private CouponSystem() {
		adminDao = new AdminDBDAO();
		companyDao = new CompanyDBDAO();
		customerDao = new CustomerDBDAO();

		try {
			couponSystemExceptions = new CouponSystemExceptionsProperties()
					.getExceptionsFromProperties();
		} catch (Exception e) {
			System.out.println(couponSystemExceptions
					.get("couponsystem.exception.error1"));
		}

		Thread dailyCouponExpirationTask = new Thread(
				new DailyCouponExpirationTask());
		dailyCouponExpirationTask.start();
	}

	public static CouponSystem getInstance() {
		return couponSystem;
	}

	public CouponSystemClientFacade login(String name, String password,
			ClientType type) throws CouponSystemException {

		boolean loggedIn;

		switch (type) {
		case ADMIN:
			loggedIn = adminDao.login(name, password);

			if (loggedIn) {
				return new AdminFacade();
			} else
				System.out.println("Sorry Credentials doens't match");

			return null;

		case COMPANY:
			loggedIn = companyDao.login(name, password);

			if (loggedIn) {
				return new CompanyFacade();
			} else
				System.out.println("Sorry Credentials doens't match");

			return null;

		case CUSTOMER:
			loggedIn = customerDao.login(name, password);

			if (loggedIn) {
				return new CustomerFacade();
			} else
				System.out.println("Sorry Credentials doens't match");

			return null;

		default:
			System.out.println("Account type doesn't exist");
			return null;
		}
	}

	public void shutdownCouponSystem() {

		connectionPool.closePoolConnections();
		dailyCouponExpirationTask.stopTask();

	}

	public static String getStackTraceAsString(Exception exception) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);

		return sw.toString();

	}
}
