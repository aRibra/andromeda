package com.couponsystem.facades;

import java.util.Collection;

import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.beans.Customer;
import com.couponsystem.dao.CouponDAO;
import com.couponsystem.dao.CustomerDAO;
import com.couponsystem.dao.CustomerDBDAO;
import com.couponsystem.dao.CouponDBDAO;

public class CustomerFacade implements CouponSystemClientFacade {

	private CustomerDAO customerDao;
	private CouponDAO couponDao;

	public CustomerFacade() {

		customerDao = new CustomerDBDAO();
		couponDao = new CouponDBDAO();

	}

	@Override
	public boolean login(String name, String password, ClientType clientType) {
		// TODO: we have to do somthn' with the clientType
		return customerDao.login(name, password);
	}

	public void updateCustomer(Customer customer) {
		// A customer may update his own info.
		customerDao.updateCustomer(customer);
	}

	public Collection<Coupon> getCoupons(Customer customer) {
		return customerDao.getCoupons(customer);
	}

	// DONE - TODO: write method
	public void purchaseCoupon(Coupon coupon, Customer customer) {
		customerDao.purchaseCoupon(coupon, customer);
	}

	// DONE - TODO: write method
	public Collection<Coupon> getAllPurchasedCoupons(Customer customer) {
		// get them for this precise user
		return customerDao.getAllPurchasedCoupons(customer);
	}

	// DONE - TODO: write method
	public Collection<Coupon> getAllPurchasedCouponsByType(
			CouponType couponType, Customer customer) {
		// get them for this precise user
		return customerDao.getAllPurchasedCouponsByType(couponType, customer);
	}

	// TODO: write method
	public Collection<Coupon> getAllPurchasedCouponsByPrice(Customer customer) {
		// get them for this precise user

		// "SELECT cust.CUST_NAME, cpn.* FROM COUPON cpn INNER JOIN customer_coupon cc ON cpn.ID = cc.COUPON_ID AND cc.CUST_ID = 3 AND cpn.PRICE = 20 INNER JOIN customer cust ON cust.ID = cc.CUST_ID"

		return null;
	}

}
