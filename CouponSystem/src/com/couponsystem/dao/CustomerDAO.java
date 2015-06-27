package com.couponsystem.dao;

import java.util.Collection;

import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.beans.Customer;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.helper.classes.ClientBucket;

public interface CustomerDAO {

	public void createCustomer(Customer customer);

	public void removeCustomer(int id);

	public void updateCustomer(Customer customer);

	public Customer getCustomer(long customerId) throws CouponSystemException;

	public Collection<Customer> getAllCustomers() throws CouponSystemException;

	public Collection<Coupon> getCoupons(long customerId)
			throws CouponSystemException;

	public void purchaseCoupon(Coupon coupon, long customerId)
			throws CouponSystemException;

	public Collection<Coupon> getAllPurchasedCoupons(long customerId)
			throws CouponSystemException;

	public Collection<Coupon> getAllPurchasedCouponsByType(
			CouponType couponType, long customerId)
			throws CouponSystemException;

	public Collection<Coupon> getAllPurchasedCouponsByPrice(Double price,
			long customerId) throws CouponSystemException;

	public Customer login(String companyName, String password)
			throws CouponSystemException;

}
