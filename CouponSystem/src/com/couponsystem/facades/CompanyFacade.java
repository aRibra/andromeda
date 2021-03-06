package com.couponsystem.facades;

import java.util.Collection;

import com.couponsystem.CouponSystem;
import com.couponsystem.beans.Admin;
import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
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

		if (company == null) {
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error19"));
		}

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

	public Collection<Coupon> getCoupons(int companyId)
			throws CouponSystemException {
		return companyDbDao.getCoupons(companyId);
	}

	// TODO: should pass id, couponType paramters
	public Collection<Coupon> getCouponByType(long id, CouponType type)
			throws CouponSystemException {
		return couponDbDao.getCompanyCouponsByType(id, type);
	}

}
