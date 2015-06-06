package com.couponsystem.beans;

import com.couponsystem.CouponSystem;
import com.couponsystem.exceptions.CouponSystemException;

public class Admin {

	private long id;
	private String name;
	private String password;
	private ClientType clientType;

	public Admin() {
		this.clientType = ClientType.ADMIN;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) throws CouponSystemException {

		if (id > Long.MAX_VALUE || id < 0)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error2"));

		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws CouponSystemException {

		if (name == null || name.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error3"));

		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws CouponSystemException {

		if (password == null || password.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error4"));

		this.password = password;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) throws CouponSystemException {
		
		if(clientType == null)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error5"));
		
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return "--(Admin)--" + "\n" + "ID=" + this.id + "\n" + "AdminName="
				+ this.name + "\n" + "Password=" + this.password + "\n"
				+ "Client Type=" + this.clientType + "\n" + "-----------";
	}

}
