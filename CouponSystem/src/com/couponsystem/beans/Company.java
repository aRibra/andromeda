package com.couponsystem.beans;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.couponsystem.CouponSystem;
import com.couponsystem.exceptions.CouponSystemException;

@XmlRootElement
public class Company extends Client {

	private String email;
	private Collection<Coupon> coupons;

	public Company() {
		this.coupons = new HashSet<Coupon>();
		this.clientType = ClientType.COMPANY;
	}

	public Company(long id, String companyName, String email)
			throws CouponSystemException {
		setClientId(id);
		setCompanyName(companyName);
		setEmail(email);
		this.coupons = new HashSet<Coupon>();
		this.clientType = ClientType.COMPANY;
	}

	public Company(String companyName, String password, String email)
			throws CouponSystemException {
		setCompanyName(companyName);
		setClientPassword(password);
		setEmail(email);
		this.coupons = new HashSet<Coupon>();
		this.clientType = ClientType.COMPANY;
	}

	public Company(String companyName, String password, String email,
			Set<Coupon> coupons) {
		this.clientName = companyName;
		this.clientPassword = password;
		this.email = email;
		this.coupons = coupons;
		this.clientType = ClientType.COMPANY;
	}

	public long getClientId() {
		return this.clientId;
	}

	public void setClientId(long id) throws CouponSystemException {

		if (id < 0 || id > Long.MAX_VALUE)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error6"));

		this.clientId = id;
	}

	public String getCompanyName() {
		return this.clientName;
	}

	public void setCompanyName(String compName) throws CouponSystemException {

		if (compName == null || compName.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error7"));

		this.clientName = compName;
	}

	public String getClientPassword() {
		return clientPassword;
	}

	public void setClientPassword(String password) throws CouponSystemException {

		if (password == null || password.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error8"));

		this.clientPassword = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws CouponSystemException {

		if (email == null || email.isEmpty() || !email.contains("@"))
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
		return "--(Company)--" + "\n" + "ID=" + this.clientId + "\n"
				+ "CompanyName=" + this.clientName + "\n" + "Password="
				+ this.clientPassword + "\n" + "Email=" + this.email + "\n"
				+ "Coupons=" + coupons + "\n" + "Client Type="
				+ this.clientType + "\n" + "-----------";
	}
}
