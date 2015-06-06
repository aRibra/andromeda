package com.couponsystem.beans;

import java.util.*;

import com.couponsystem.CouponSystem;
import com.couponsystem.exceptions.CouponSystemException;

public class Company {

	private long id;
	private String companyName;
	// TODO Encrypted passwords
	private String password;
	// TODO Check if email is not mistyped
	private String email;
	private Collection<Coupon> coupons;
	private ClientType clientType;

	public Company() {
		this.coupons = new HashSet<Coupon>();
		this.clientType = ClientType.COMPANY;
	}

	public Company(String companyName, String password, String email) throws CouponSystemException {
		setCompanyName(companyName);
		setPassword(password);
		setEmail(email);
		this.coupons = new HashSet<Coupon>();
		this.clientType = ClientType.COMPANY;
	}

	public Company(String companyName, String password, String email,
			Set<Coupon> coupons) {
		this.companyName = companyName;
		this.password = password;
		this.email = email;
		this.coupons = coupons;
		this.clientType = ClientType.COMPANY;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) throws CouponSystemException {

		if (id < 0 || id > Long.MAX_VALUE)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error6"));

		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String compName) throws CouponSystemException {
		
		if(compName == null || compName.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error7"));
		
		this.companyName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws CouponSystemException {
		
		if(password == null || password.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error8"));
		
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws CouponSystemException {
		
		if(email == null || email.isEmpty() || !email.contains("@"))
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error9"));
		
		this.email = email;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return "--(Company)--" + "\n" + "ID=" + id + "\n" + "CompanyName="
				+ companyName + "\n" + "Password=" + password + "\n" + "Email="
				+ email + "\n" + "Coupons=" + coupons + "\n" + "Client Type="
				+ this.clientType + "\n" + "-----------";
	}
}
