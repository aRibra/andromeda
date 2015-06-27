package com.couponsystem.dao;

import java.util.*;

import com.couponsystem.beans.*;
import com.couponsystem.exceptions.CouponSystemException;

public interface CouponDAO {

	public void createCoupon(Coupon coupon) throws CouponSystemException;

	public void removeCoupon(Coupon coupon) throws CouponSystemException;

	public void updateCoupon(Coupon coupon) throws CouponSystemException;

	public Coupon getCoupon(int id) throws CouponSystemException;

	public Collection<Coupon> getAllCoupons() throws CouponSystemException;

	public Collection<Coupon> getAllCouponsByType(CouponType type) throws CouponSystemException;

	public Collection<Coupon> getCompanyCouponsByType(long id, CouponType type) throws CouponSystemException;

}
