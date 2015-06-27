package com.api.CouponSystemServices;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.Customer;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.facades.AdminFacade;
import com.couponsystem.facades.CompanyFacade;
import com.couponsystem.facades.CustomerFacade;
import com.couponsystem.helper.classes.ClientBucket;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/customer_service")
public class CustomerService {

	@Context
	private HttpServletRequest request;

	@POST
	@Path("/display_customer")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@FormParam("CUSTOMER_ID") String customerId)
			throws JSONException, CouponSystemException {

		CustomerFacade customerFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		customerFacade = (CustomerFacade) clientBucket.getFacade();

		Customer customer = customerFacade.getCustomer(Long
				.parseLong(customerId));

		return customer;
	}

	@POST
	@Path("/update_customer_info")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject updateCustomerInfo(@FormParam("ID") long customerId,
			@FormParam("NAME") String custName,
			@FormParam("PASSWORD") String password) throws JSONException,
			CouponSystemException {

		CustomerFacade customerFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		customerFacade = (CustomerFacade) clientBucket.getFacade();

		Customer customer = new Customer();
		customer.setClientId(customerId);
		customer.setClientName(custName);
		customer.setClientPassword(password);

		customerFacade.updateCustomer(customer);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "customer updated successfully");

		return jsonResponse;
	}

	
	@POST
	@Path("/get_customer_coupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupons(@FormParam("CUSTOMER_ID") int customerId) throws JSONException,
			CouponSystemException {

		CustomerFacade customerFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		customerFacade = (CustomerFacade) clientBucket.getFacade();

		Collection<Coupon> coupons = customerFacade.getCoupons(customerId);

		return coupons;
	}
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {

		return "Hello CustomerService";
	}

}
