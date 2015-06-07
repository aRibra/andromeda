package com.couponsystem.helper.classes;

import com.couponsystem.beans.Client;
import com.couponsystem.facades.CouponSystemClientFacade;

public class ClientBucket {

	private CouponSystemClientFacade facade;
	private Client client;

	public ClientBucket(CouponSystemClientFacade facade, Client client) {
		this.facade = facade;
		this.client = client;
	}

	public CouponSystemClientFacade getFacade() {
		return facade;
	}

	public void setFacade(CouponSystemClientFacade facade) {
		this.facade = facade;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
