package com.couponsystem.facades;

import java.util.Collection;

import com.couponsystem.beans.ClientType;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Customer;
import com.couponsystem.dao.*;

public class AdminFacade implements CouponSystemClientFacade {

	private CompanyDAO companyDbDao;
	private CustomerDAO customerDbDao;
	private AdminDAO adminDbDao;

	public AdminFacade() {
		companyDbDao = new CompanyDBDAO();
		customerDbDao = new CustomerDBDAO();
	}

	@Override
	public boolean login(String name, String password, ClientType clientType) {
		// TODO: we have to do somthn' with the clientType
		return adminDbDao.login(name, name);
	}

	public void createCompany(Company company) {
		companyDbDao.createCompany(company);
	}

	public void removeCompany(Company company) {
		companyDbDao.removeCompany(company);
	}

	public void updateCompany(Company company) {
		companyDbDao.updateCompany(company);
	}

	public Company getCompany(int id) {
		return companyDbDao.getCompany(id);
	}

	public Collection<Company> getAllCompaines() {
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

	public Customer getCustomer(int id) {
		return customerDbDao.getCustomer(id);
	}

	public Collection<Customer> getAllCustomers() {
		return customerDbDao.getAllCustomers();
	}

}
