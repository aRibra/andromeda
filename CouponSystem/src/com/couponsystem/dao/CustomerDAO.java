package com.couponsystem.dao;

import java.util.*;

import com.couponsystem.beans.*;

public interface CustomerDAO {

	public void createCustomer(Customer customer);

	public void removeCustomer(Customer customer);

	public void updateCustomer(Customer customer);

	public Customer getCustomer(int id);

	public Collection<Customer> getAllCustomers();

	public Collection<Coupon> getCoupons(Customer customer);

	public void purchaseCoupon(Coupon coupon, Customer customer);

	public Collection<Coupon> getAllPurchasedCoupons(Customer customer);

	public Collection<Coupon> getAllPurchasedCouponsByType(
			CouponType couponType, Customer customer);

	public Collection<Coupon> getAllPurchasedCouponsByPrice(Double price,
			Customer customer);

	public boolean login(String companyName, String password);

}
