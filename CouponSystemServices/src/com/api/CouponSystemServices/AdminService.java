package com.api.CouponSystemServices;

import java.util.Collection;
import java.util.List;

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

import com.couponsystem.CouponSystem;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.Customer;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.facades.AdminFacade;
import com.couponsystem.facades.CompanyFacade;
import com.couponsystem.helper.classes.ClientBucket;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/admin_service")
public class AdminService {

	@Context
	private HttpServletRequest request;

	// CouponSystem couponSystem = CouponSystem.getInstance();

	@POST
	@Path("/create_company")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject createCompany(@FormParam("COMP_NAME") String companyName,
			@FormParam("PASSWORD") String password,
			@FormParam("EMAIL") String email) throws JSONException,
			CouponSystemException {

		// AdminFacade adminFacade = null;
		// ClientBucket clientBucket = null;
		Company company = new Company(companyName, password, email);

		// HttpSession session = request.getSession(true);
		// clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		//
		// adminFacade = (AdminFacade) clientBucket.getFacade();
		// adminFacade.createCompany(company);

		AdminFacade admin = new AdminFacade();
		admin.createCompany(company);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "company created successfully");

		return jsonResponse;
	}

	@POST
	@Path("/get_company")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@FormParam("COMP_ID") String companyId)
			throws JSONException, CouponSystemException {

		// HttpSession session = request.getSession(true);
		// clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		//
		// adminFacade = (AdminFacade) clientBucket.getFacade();
		// adminFacade.createCompany(company);

		AdminFacade admin = new AdminFacade();

		Company company = admin.getCompany(Integer.parseInt(companyId));

		return company;
	}

	@POST
	@Path("/get_all_companies")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getAllCompaines() throws JSONException,
			CouponSystemException {

		// HttpSession session = request.getSession(true);
		// clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		//
		// adminFacade = (AdminFacade) clientBucket.getFacade();
		// adminFacade.createCompany(company);

		AdminFacade admin = new AdminFacade();

		Collection<Company> companies = admin.getAllCompaines();

		return companies;
	}

	@POST
	@Path("/remove_company")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject removeCompany(@FormParam("COMP_ID") int companyId)
			throws JSONException, CouponSystemException {

		// HttpSession session = request.getSession(true);
		// clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		//
		// adminFacade = (AdminFacade) clientBucket.getFacade();
		// adminFacade.createCompany(company);

		AdminFacade admin = new AdminFacade();
		admin.removeCompany(companyId);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "company removed successfully");

		return jsonResponse;
	}

	@POST
	@Path("/update_company")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject updateCompany(@FormParam("COMP_ID") int id,
			@FormParam("COMP_NAME") String companyName,
			@FormParam("EMAIL") String email) throws JSONException,
			CouponSystemException {

		// HttpSession session = request.getSession(true);
		// clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		//
		// adminFacade = (AdminFacade) clientBucket.getFacade();
		// adminFacade.createCompany(company);

		AdminFacade admin = new AdminFacade();

		Company company = new Company(id, companyName, email);
		company.setClientId(id);
		admin.updateCompany(company);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "company updated successfully");

		return jsonResponse;
	}

	@POST
	@Path("/create_customer")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject createCustomer(
			@FormParam("CUSTOMER_NAME") String customerName,
			@FormParam("PASSWORD") String password) throws JSONException,
			CouponSystemException {

		// HttpSession session = request.getSession(true);
		// clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		//
		// adminFacade = (AdminFacade) clientBucket.getFacade();
		// adminFacade.createCompany(company);

		AdminFacade admin = new AdminFacade();
		Customer customer = new Customer(customerName, password);
		admin.createCustomer(customer);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "customer created successfully");

		return jsonResponse;
	}

	@POST
	@Path("/get_customer")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@FormParam("CUSTOMER_ID") int id)
			throws JSONException, CouponSystemException {

		// HttpSession session = request.getSession(true);
		// clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		//
		// adminFacade = (AdminFacade) clientBucket.getFacade();
		// adminFacade.createCompany(company);

		AdminFacade admin = new AdminFacade();
		Customer customer = admin.getCustomer(id);

		return customer;
	}

	@POST
	@Path("/remove_customer")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject removeCustomer(@FormParam("CUSTOMER_ID") int id)
			throws JSONException, CouponSystemException {

		// HttpSession session = request.getSession(true);
		// clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		//
		// adminFacade = (AdminFacade) clientBucket.getFacade();
		// adminFacade.createCompany(company);

		AdminFacade admin = new AdminFacade();
		admin.removeCustomer(id);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "customer removed successfully");

		return jsonResponse;
	}

	@POST
	@Path("/get_all_customers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAllCustomers() throws JSONException,
			CouponSystemException {

		// HttpSession session = request.getSession(true);
		// clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		//
		// adminFacade = (AdminFacade) clientBucket.getFacade();
		// adminFacade.createCompany(company);

		AdminFacade admin = new AdminFacade();

		Collection<Customer> customers = admin.getAllCustomers();

		return customers;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Service: AdiminService";
	}

	@POST
	@Path("/update_customer")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject updateCustomer(@FormParam("CUSTOMER_ID") int id,
			@FormParam("CUSTOMER_NAME") String customerName)
			throws JSONException, CouponSystemException {

		// HttpSession session = request.getSession(true);
		// clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		//
		// adminFacade = (AdminFacade) clientBucket.getFacade();
		// adminFacade.createCompany(company);

		AdminFacade admin = new AdminFacade();

		Customer customer = new Customer(id, customerName);
		customer.setClientId(id);
		admin.updateCustomer(customer);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "customer updated successfully");

		return jsonResponse;
	}

}
