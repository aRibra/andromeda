package com.couponsystem.beans;

import com.couponsystem.CouponSystem;
import com.couponsystem.exceptions.CouponSystemException;

public class Admin extends Client {

	private ClientType clientType;

	public Admin(long adminId, String adminName) {
		this.clientId = adminId;
		this.clientName = adminName;
		this.clientType = ClientType.ADMIN;
	}

	public Admin() {
		this.clientType = ClientType.ADMIN;
	}

	public long getClientId() {
		return this.clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String name) throws CouponSystemException {

		if (name == null || name.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error3"));

		this.clientName = name;
	}

	public String getClientPassword() {
		return this.clientPassword;
	}

	public void setClientPassword(String password) throws CouponSystemException {

		if (password == null || password.isEmpty())
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error4"));

		this.clientPassword = password;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType)
			throws CouponSystemException {

		if (clientType == null)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error5"));

		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return "--(Admin)--" + "\n" + "ID=" + this.clientId + "\n"
				+ "AdminName=" + this.clientName + "\n" + "Password="
				+ this.clientPassword + "\n" + "Client Type=" + this.clientType
				+ "\n" + "-----------";
	}

}
