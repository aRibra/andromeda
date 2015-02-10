package com.couponsystem.dao;

import java.util.*;

import com.couponsystem.beans.*;

public interface CompanyDAO {

	public void createCompany(Company company);

	public void removeCompany(Company company);

	public void updateCompany(Company company);

	public Company getCompany(int id);

	public Collection<Company> getAllCompaines();

	public Collection<Coupon> getCoupons(Company company);

	public boolean login(String companyName, String password);

}
