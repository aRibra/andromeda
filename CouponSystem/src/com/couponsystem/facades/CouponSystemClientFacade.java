package com.couponsystem.facades;

import com.couponsystem.beans.ClientType;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.helper.classes.ClientBucket;

public interface CouponSystemClientFacade {

	public ClientBucket login(String name, String password, ClientType clientType) throws CouponSystemException;

}
