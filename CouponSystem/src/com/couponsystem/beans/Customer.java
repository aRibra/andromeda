package com.couponsystem.beans;

import java.util.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.couponsystem.CouponSystem;
import com.couponsystem.exceptions.CouponSystemException;

@XmlRootElement
public class Customer extends Client {

	private Collection<Coupon> coupons;

	public Customer() {
		this.coupons = new HashSet<Coupon>();
		this.clientType = ClientType.CUSTOMER;
	}

	public Customer(String customerName, String password)
			throws CouponSystemException {
		setClientName(customerName);
		setClientPassword(password);
		this.coupons = new HashSet<Coupon>();
		this.clientType = ClientType.CUSTOMER;
	}

	public Customer(long id, String customerName) throws CouponSystemException {
		setClientId(id);
		setClientName(customerName);
		this.coupons = new HashSet<Coupon>();
		this.clientType = ClientType.CUSTOMER;
	}

	public Customer(String custName, String password, ClientType clientType)
			throws CouponSystemException {
		setClientName(custName);
		setClientPassword(password);
		setClientType(clientType);
		this.coupons = new HashSet<Coupon>();
	}

	public Customer(String custName, String password, Set<Coupon> coupons)
			throws CouponSystemException {
		setClientName(custName);
		setClientPassword(password);
		this.coupons = coupons;
		this.clientType = ClientType.CUSTOMER;
	}

	public long getClientId() {
		return this.clientId;
	}

	public void setClientId(long id) throws CouponSystemException {

		if (id < 0 || id > Long.MAX_VALUE)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error17"));

		this.clientId = id;
	}

	public String getCustomerName() {
		return this.clientName;
	}

	public void setClientName(String custName) throws CouponSystemException {

		if (custName == null || custName.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error18"));

		this.clientName = custName;
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

	public String getClientPassword() {
		return clientPassword;
	}

	public void setClientPassword(String password) throws CouponSystemException {

		if (password == null || password.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error20"));

		this.clientPassword = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "--(Customer)--" + "\n" + "ID=" + this.clientId + "\n"
				+ "CustomerName=" + this.clientName + "\n" + "Password="
				+ this.clientPassword + "\n" + "Coupons=" + this.coupons + "\n"
				+ "Client Type=" + this.clientType + "\n" + "-----------";
	}
}