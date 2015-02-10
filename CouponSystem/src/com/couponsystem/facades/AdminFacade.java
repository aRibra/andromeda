package com.couponsystem.facades;

import java.util.Collection;

import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Customer;
import com.couponsystem.dao.*;

public class AdminFacade implements CouponSystemClientFacade {

	private CompanyDBDAO companyDbDao;
	private CustomerDBDAO customerDbDao;

	public AdminFacade() {
		companyDbDao = new CompanyDBDAO();
		customerDbDao = new CustomerDBDAO();
	}

	@Override
	public boolean login(String name, String password, ClientType clientType) {
		// TODO

		return false;
	}

	public void createCompanyFacade(Company company) {
		companyDbDao.createCompany(company);
	}

	public void removeCompanyFacade(Company company) {
		companyDbDao.removeCompany(company);
	}

	public void updateCompanyFacade(Company company) {
		companyDbDao.updateCompany(company);
	}

	public Company getCompanyFacade(int id) {
		return companyDbDao.getCompany(id);
	}

	public Collection<Company> getAllCompainesFacade() {
		return companyDbDao.getAllCompaines();
	}

	public void createCustomerFacade(Customer customer) {
		customerDbDao.createCustomer(customer);
	}

	public void removeCustomerFacade(Customer customer) {
		customerDbDao.removeCustomer(customer);
	}

	public void updateCustomerFacade(Customer customer) {
		customerDbDao.updateCustomer(customer);
	}

	public Customer getCustomerFacade(int id) {
		return customerDbDao.getCustomer(id);
	}

	public Collection<Customer> getAllCustomers() {
		return customerDbDao.getAllCustomers();
	}

}
