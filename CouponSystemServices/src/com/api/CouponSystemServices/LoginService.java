package com.api.CouponSystemServices;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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

	@Context
	private HttpServletResponse response;

	CouponSystem couponSystem = CouponSystem.getInstance();

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@FormParam("client-type") String clientType,
			@FormParam("clientName") String clientName,
			@FormParam("clientPassword") String clientPassword)
			throws CouponSystemException, JSONException, IOException,
			URISyntaxException {
		// try{
		ClientBucket clientBucket = null;

		clientBucket = couponSystem.login(clientName, clientPassword,
				ClientType.valueOf(clientType));

		if (clientBucket != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("clientBucket", clientBucket);
			session.setAttribute("loggedIn", Boolean.TRUE);

		}

		String queryId = String.valueOf(clientBucket.getClient().getClientId());
		String queryName = clientBucket.getClient().getClientName();
		String queryClientType = clientBucket.getClient().getClientType().name();

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "You have been logged in successfully");
		jsonResponse.put("clientId", queryId);
		jsonResponse.put("clientName", queryName);
		jsonResponse.put("clientType", queryClientType);

		// URI uri = new URI(request.getContextPath() +
		// "/client_page.html?O=o");
		//
		// return Response.seeOther(uri).build();
		//

		// } catch (CouponSystemException e) {
		// return e.getMaessage();
		//
		// }

		response.sendRedirect(request.getContextPath()
				+ "/client_page.html?response=" + jsonResponse.toString());
		return "";

	}
}
