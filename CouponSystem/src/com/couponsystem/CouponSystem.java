package com.couponsystem;

import com.couponsystem.beans.ClientType;
import com.couponsystem.dao.*;
import com.couponsystem.facades.AdminFacade;
import com.couponsystem.facades.CompanyFacade;
import com.couponsystem.facades.CouponSystemClientFacade;
import com.couponsystem.facades.CustomerFacade;

public class CouponSystem {

	private AdminDAO adminDao;
	private CompanyDAO companyDao;
	private CouponDAO couponDao;
	private CustomerDAO customerDao;

	private static CouponSystem couponSystem = new CouponSystem();

	private CouponSystem() {
		adminDao = new AdminDBDAO();
		companyDao = new CompanyDBDAO();
		customerDao = new CustomerDBDAO();
	}

	public static CouponSystem getInstance() {
		return couponSystem;
	}

	public CouponSystemClientFacade login(String name, String password,
			ClientType type) {

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

}
