package com.couponsystem.dao;

import java.util.*;

import com.couponsystem.beans.*;
import com.couponsystem.exceptions.CouponSystemException;

public interface CompanyDAO {

	public void createCompany(Company company) throws CouponSystemException;

	public void removeCompany(Company company) throws CouponSystemException;

	public void updateCompany(Company company) throws CouponSystemException;

	public Company getCompany(int id) throws CouponSystemException;

	public Collection<Company> getAllCompaines() throws CouponSystemException;

	public Collection<Coupon> getCoupons(Company company) throws CouponSystemException;

	public Company login(String companyName, String password) throws CouponSystemException;

}
