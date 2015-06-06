package com.couponsystem.beans;

import java.util.*;

import com.couponsystem.CouponSystem;
import com.couponsystem.exceptions.CouponSystemException;

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

	public Customer(String custName, String password, ClientType clientType)
			throws CouponSystemException {
		setCustomerName(custName);
		setPassword(password);
		this.coupons = new HashSet<Coupon>();
		setClientType(clientType);
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

	public void setId(long id) throws CouponSystemException {

		if (id < 0 || id > Long.MAX_VALUE)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error17"));

		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String custName) throws CouponSystemException {

		if (custName == null || custName.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error18"));

		this.customerName = custName;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType)
			throws CouponSystemException {

		if (clientType == null)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error19"));

		this.clientType = clientType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws CouponSystemException {

		if (password == null || password.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error20"));

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
		return "--(Customer)--" + "\n" + "ID=" + id + "\n" + "CustomerName="
				+ customerName + "\n" + "Password=" + password + "\n"
				+ "Coupons=" + coupons + "\n" + "Client Type="
				+ this.clientType + "\n" + "-----------";
	}
}