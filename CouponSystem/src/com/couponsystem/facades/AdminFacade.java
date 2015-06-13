package com.couponsystem.facades;

import java.util.Collection;

import com.couponsystem.CouponSystem;
import com.couponsystem.beans.Admin;
import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Customer;
import com.couponsystem.dao.*;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.helper.classes.ClientBucket;

public class AdminFacade implements CouponSystemClientFacade {

	private CompanyDAO companyDbDao;
	private CustomerDAO customerDbDao;
	private AdminDBDAO adminDbDao;

	public AdminFacade() {
		adminDbDao = new AdminDBDAO();
		companyDbDao = new CompanyDBDAO();
		customerDbDao = new CustomerDBDAO();
	}

	@Override
	public ClientBucket login(String name, String password,
			ClientType clientType) throws CouponSystemException {
		// TODO: we have to do somthn' with the clientType

		Admin admin = adminDbDao.login(name, password);

		if (admin == null) {
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error19"));
		}

		ClientBucket clientBucket = new ClientBucket(new AdminFacade(), admin);

		return clientBucket;
	}

	public void createCompany(Company company) throws CouponSystemException {
		companyDbDao.createCompany(company);
	}

	public void removeCompany(Company company) throws CouponSystemException {
		companyDbDao.removeCompany(company);
	}

	public void updateCompany(Company company) throws CouponSystemException {
		companyDbDao.updateCompany(company);
	}

	public Company getCompany(int id) throws CouponSystemException {
		return companyDbDao.getCompany(id);
	}

	public Collection<Company> getAllCompaines() throws CouponSystemException {
		return companyDbDao.getAllCompaines();
	}

	public void createCustomer(Customer customer) {
		customerDbDao.createCustomer(customer);
	}

	public void removeCustomer(Customer customer) {
		customerDbDao.removeCustomer(customer);
	}

	public void updateCustomer(Customer customer) {
		customerDbDao.updateCustomer(customer);
	}

	public Customer getCustomer(int id) throws CouponSystemException {
		return customerDbDao.getCustomer(id);
	}

	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		return customerDbDao.getAllCustomers();
	}

}
