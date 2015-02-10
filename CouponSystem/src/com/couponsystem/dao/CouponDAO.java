package com.couponsystem.dao;

import java.util.*;

import com.couponsystem.beans.*;

public interface CouponDAO {

	public void createCoupon(Coupon coupon);

	public void removeCoupon(Coupon coupon);

	public void updateCoupon(Coupon coupon);

	public Coupon getCoupon(int id);

	public Collection<Coupon> getAllCoupons();

	public Collection<Coupon> getCouponByType(CouponType type);

}
