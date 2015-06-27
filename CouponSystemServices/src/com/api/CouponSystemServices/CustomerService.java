package com.api.CouponSystemServices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/customer_service")
public class CustomerService {
	
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {

		return "Hello CustomerService";
	}

}
