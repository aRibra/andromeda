package com.couponsystem.beans;

import com.couponsystem.CouponSystem;
import com.couponsystem.exceptions.CouponSystemException;

public class Client {

	protected long clientId;
	protected String clientName;
	protected String clientPassword;
	protected ClientType clientType;

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long id) throws CouponSystemException {

		if (id > Long.MAX_VALUE || id < 0)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error2"));

		this.clientId = id;
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
		return clientPassword;
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

}
