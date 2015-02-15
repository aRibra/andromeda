package com.couponsystem.facades;

import java.util.Collection;

import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.dao.CompanyDAO;
import com.couponsystem.dao.CouponDAO;
import com.couponsystem.dao.CouponDBDAO;
import com.couponsystem.dao.CompanyDBDAO;

public class CompanyFacade implements CouponSystemClientFacade {

	private CouponDAO couponDbDao;
	private CompanyDAO companyDbDao;

	public CompanyFacade() {
		couponDbDao = new CouponDBDAO();
		companyDbDao = new CompanyDBDAO();
	}

	@Override
	public boolean login(String name, String password, ClientType clientType) {
		// TODO: we have to do somthn' with the clientType
		return companyDbDao.login(name, name);
	}

	public void createCoupon(Coupon coupon) {
		// make sure to add an entry in "company_coupon" table.
		couponDbDao.createCoupon(coupon);
	}

	public void removeCoupon(Coupon coupon) {
		couponDbDao.createCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon) {
		couponDbDao.updateCoupon(coupon);
	}

	public Coupon getCoupon(int id) {
		return couponDbDao.getCoupon(id);

	}

	public Collection<Coupon> getCoupons(Company company) {
		return companyDbDao.getCoupons(company);
	}

	public Collection<Coupon> getCouponByType(Company company) {
		return couponDbDao.getCompanyCouponsByType(company);
	}

}
