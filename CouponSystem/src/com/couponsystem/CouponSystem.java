package com.couponsystem;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import com.couponsystem.beans.Admin;
import com.couponsystem.beans.ClientType;
import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.dailytask.DailyCouponExpirationTask;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.exceptions.CouponSystemExceptionsProperties;
import com.couponsystem.facades.AdminFacade;
import com.couponsystem.facades.CompanyFacade;
import com.couponsystem.facades.CouponSystemClientFacade;
import com.couponsystem.facades.CustomerFacade;
import com.couponsystem.helper.classes.ClientBucket;

public class CouponSystem {

	private AdminFacade adminFacade;
	private CompanyFacade companyFacade;
	private CustomerFacade customerFacade;
	private DailyCouponExpirationTask dailyCouponExpirationTask;

	private static CouponSystem couponSystem = new CouponSystem();
	ConnectionPool connectionPool = ConnectionPool.getInstance();
	public static Map<String, String> couponSystemExceptions;

	private CouponSystem() {
		adminFacade = new AdminFacade();
		companyFacade = new CompanyFacade();
		customerFacade = new CustomerFacade();

		try {
			couponSystemExceptions = new CouponSystemExceptionsProperties()
					.getExceptionsFromProperties();
		} catch (Exception e) {
			System.out.println(couponSystemExceptions
					.get("couponsystem.exception.error1"));
		}

		dailyCouponExpirationTask = new DailyCouponExpirationTask();
		Thread dailyCouponExpirationThread = new Thread(
				dailyCouponExpirationTask);
		dailyCouponExpirationThread.start();
	}

	public static CouponSystem getInstance() {
		return couponSystem;
	}

	// TODO: set login to retrieve also the client object : ClientBucket
	public ClientBucket login(String name, String password, ClientType type)
			throws CouponSystemException {

		switch (type) {
		case ADMIN:

			ClientBucket adminClientBucket = adminFacade.login(name, password,
					type);

			if (adminClientBucket != null) {
				return adminClientBucket;
			} else
				throw new CouponSystemException(
						CouponSystem.couponSystemExceptions
								.get("couponsystem.exception.error17"));

		case COMPANY:

			ClientBucket companyClientBucket = companyFacade.login(name,
					password, type);

			if (companyClientBucket != null) {
				return companyClientBucket;
			} else
				throw new CouponSystemException(
						CouponSystem.couponSystemExceptions
								.get("couponsystem.exception.error17"));

		case CUSTOMER:
			ClientBucket customerClientBucket = customerFacade.login(name,
					password, type);

			if (customerClientBucket != null) {
				return customerClientBucket;
			} else
				throw new CouponSystemException(
						CouponSystem.couponSystemExceptions
								.get("couponsystem.exception.error17"));

		default:
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error18"));

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
