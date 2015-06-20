package com.api.CouponSystemServices;

import java.util.Collection;
import java.util.List;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.couponsystem.CouponSystem;
import com.couponsystem.beans.*;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.facades.AdminFacade;
import com.couponsystem.facades.CompanyFacade;
import com.couponsystem.facades.CouponSystemClientFacade;
import com.couponsystem.facades.CustomerFacade;
import com.couponsystem.helper.classes.ClientBucket;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("login-service")
public class LoginService {

	@Context
	private HttpServletRequest request;

	CouponSystem couponSystem = CouponSystem.getInstance();

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon login(@FormParam("client-type") String clientType,
			@FormParam("clientName") String clientName,
			@FormParam("clientPassword") String clientPassword)
			throws CouponSystemException {

		// TODO: Login Filter, if reached to here that means that the client has
		// session

		ClientBucket clientBucket = null;

		// try {

		clientBucket = couponSystem.login(clientName, clientPassword,
				ClientType.valueOf(clientType));

		if (clientBucket != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("clientBucket", clientBucket);
			session.setAttribute("loggedIn", Boolean.TRUE);

		}

		AdminFacade adminFacade = null;
		CompanyFacade companyFacade = null;
		CustomerFacade customerFacade = null;
 
		Admin admin = null;
		Company company = null;
		Customer customer = null;

		if (clientBucket.getFacade() instanceof AdminFacade) {
			adminFacade = (AdminFacade) clientBucket.getFacade();
			admin = (Admin) clientBucket.getClient();

		} else if (clientBucket.getFacade() instanceof CompanyFacade) {
			companyFacade = (CompanyFacade) clientBucket.getFacade();
			company = (Company) clientBucket.getClient();

		} else if (clientBucket.getFacade() instanceof CustomerFacade) {
			customerFacade = (CustomerFacade) clientBucket.getFacade();
			customer = (Customer) clientBucket.getClient();

		}

		// test this with json

		return companyFacade.getCoupon(78);

		// } catch (CouponSystemException e) {
		// return e.getMaessage();

		// }

	}

}
