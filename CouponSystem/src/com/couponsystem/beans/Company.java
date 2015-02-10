package com.couponsystem.beans;

import java.util.*;

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

	public Company(String companyName, String password, String email) {
		this.companyName = companyName;
		this.password = password;
		this.email = email;
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

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String compName) {
		this.companyName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
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
		return "ID:" + id + " ,CompanyName:" + companyName + " ,Password:"
				+ password + " ,Email:" + email + " ,Coupons:" + coupons
				+ " ,Client Type:" + this.clientType;
	}
}
