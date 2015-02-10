package com.couponsystem.beans;

import java.util.*;

public class Customer {

	private long id;
	private String customerName;
	private String password;
	private Collection<Coupon> coupons;
	private ClientType clientType;

	public Customer() {
		this.coupons = new HashSet<Coupon>();
		this.clientType = ClientType.CUSTOMER;
	}

	public Customer(String custName, String password) {
		this.customerName = custName;
		this.password = password;
		this.coupons = new HashSet<Coupon>();
		this.clientType = ClientType.CUSTOMER;
	}

	public Customer(String custName, String password, Set<Coupon> coupons) {
		this.customerName = custName;
		this.password = password;
		this.coupons = coupons;
		this.clientType = ClientType.CUSTOMER;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String custName) {
		this.customerName = custName;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "ID:" + id + " ,CustomerName:" + customerName + " ,Password:"
				+ password + " ,Coupons:" + coupons + " ,Client Type:"
				+ this.clientType;
	}
}