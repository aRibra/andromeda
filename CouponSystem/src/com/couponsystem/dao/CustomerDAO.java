package com.couponsystem.dao;

import java.util.*;

import com.couponsystem.beans.*;
import com.couponsystem.exceptions.CouponSystemException;

public interface CustomerDAO {

	public void createCustomer(Customer customer);

	public void removeCustomer(Customer customer);

	public void updateCustomer(Customer customer);

	public Customer getCustomer(int id) throws CouponSystemException;

	public Collection<Customer> getAllCustomers() throws CouponSystemException;

	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException;

	public void purchaseCoupon(Coupon coupon, Customer customer) throws CouponSystemException;

	public Collection<Coupon> getAllPurchasedCoupons(Customer customer) throws CouponSystemException;

	public Collection<Coupon> getAllPurchasedCouponsByType(
			CouponType couponType, Customer customer) throws CouponSystemException;

	public Collection<Coupon> getAllPurchasedCouponsByPrice(Double price,
			Customer customer) throws CouponSystemException;

	public boolean login(String companyName, String password) throws CouponSystemException;

}
