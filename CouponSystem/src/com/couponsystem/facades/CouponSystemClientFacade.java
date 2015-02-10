package com.couponsystem.facades;

import com.couponsystem.beans.ClientType;

public interface CouponSystemClientFacade {

	public boolean login(String name, String password, ClientType clientType);

}
