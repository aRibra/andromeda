package com.couponsystem.facades;

import com.couponsystem.beans.ClientType;
import com.couponsystem.exceptions.CouponSystemException;

public interface CouponSystemClientFacade {

	public boolean login(String name, String password, ClientType clientType) throws CouponSystemException;

}
