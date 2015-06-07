package com.couponsystem.facades;

import java.util.Collection;

import com.couponsystem.beans.Admin;
import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.dao.CompanyDAO;
import com.couponsystem.dao.CouponDAO;
import com.couponsystem.dao.CouponDBDAO;
import com.couponsystem.dao.CompanyDBDAO;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.helper.classes.ClientBucket;

public class CompanyFacade implements CouponSystemClientFacade {

	private CouponDAO couponDbDao;
	private CompanyDAO companyDbDao;

	public CompanyFacade() {
		couponDbDao = new CouponDBDAO();
		companyDbDao = new CompanyDBDAO();
	}

	@Override
	public ClientBucket login(String name, String password,
			ClientType clientType) throws CouponSystemException {

		Company company = companyDbDao.login(name, password);
		ClientBucket clientBucket = new ClientBucket(new CompanyFacade(),
				company);

		return clientBucket;

	}

	public void createCoupon(Coupon coupon) throws CouponSystemException {
		// make sure to add an entry in "company_coupon" table.
		couponDbDao.createCoupon(coupon);
	}

	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		couponDbDao.createCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		couponDbDao.updateCoupon(coupon);
	}

	public Coupon getCoupon(int id) throws CouponSystemException {
		return couponDbDao.getCoupon(id);

	}

	public Collection<Coupon> getCoupons(Company company)
			throws CouponSystemException {
		return companyDbDao.getCoupons(company);
	}

	public Collection<Coupon> getCouponByType(Company company)
			throws CouponSystemException {
		return couponDbDao.getCompanyCouponsByType(company);
	}

}
