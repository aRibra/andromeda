package com.api.CouponSystemServices;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

import com.api.business.BusinessDelegate;
import com.couponsystem.CouponSystem;
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.Customer;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.facades.AdminFacade;
import com.couponsystem.facades.CompanyFacade;
import com.couponsystem.helper.classes.ClientBucket;
import com.sun.jersey.spi.resource.Singleton;

import couponsystem.ejb.db.Income;

@Singleton
@Path("/admin_service")
public class AdminService {

	@Context
	private HttpServletRequest request;
	
	private BusinessDelegate delegate;

	// CouponSystem couponSystem = CouponSystem.getInstance();

	@PostConstruct
	public void init(){
		delegate = new BusinessDelegate();
	}
	
	@POST
	@Path("/view_all_incomes")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Income> viewIncomeByCustomer() throws JSONException,
			CouponSystemException {
		
		Collection<Income> incomeCollection = delegate.viewAllIncome();

		return incomeCollection;
	}
	
	@POST
	@Path("/view_income_by_customer")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Income> viewIncomeByCustomer(@FormParam("CUSTOMER_ID") int customerId) throws JSONException,
			CouponSystemException {
		
		Collection<Income> incomeCollection = delegate.viewIncomeByCustomer(customerId);

		return incomeCollection;
	}
	
	@POST
	@Path("/view_income_by_company")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Income> viewIncomeByCompany(@FormParam("COMP_ID") int companyId) throws JSONException,
			CouponSystemException {
		
		Collection<Income> incomeCollection = delegate.viewIncomeByCompany(companyId);

		return incomeCollection;
	}
	
	@POST
	@Path("/create_company")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject createCompany(@FormParam("COMP_NAME") String companyName,
			@FormParam("PASSWORD") String password,
			@FormParam("EMAIL") String email) throws CouponSystemException, JSONException {

		Company company = new Company(companyName, password, email);

		AdminFacade adminFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);

		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		adminFacade = (AdminFacade) clientBucket.getFacade();
		adminFacade.createCompany(company);

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

		AdminFacade adminFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		adminFacade = (AdminFacade) clientBucket.getFacade();

		Company company = adminFacade.getCompany(Integer.parseInt(companyId));

		return company;
	}

	@POST
	@Path("/get_all_companies")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getAllCompaines() throws JSONException,
			CouponSystemException {

		AdminFacade adminFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		adminFacade = (AdminFacade) clientBucket.getFacade();

		Collection<Company> companies = adminFacade.getAllCompaines();

		return companies;
	}

	@POST
	@Path("/remove_company")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject removeCompany(@FormParam("COMP_ID") int companyId)
			throws JSONException, CouponSystemException {

		AdminFacade adminFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		adminFacade = (AdminFacade) clientBucket.getFacade();

		adminFacade.removeCompany(companyId);

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

		AdminFacade adminFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");

		adminFacade = (AdminFacade) clientBucket.getFacade();

		Company company = new Company(id, companyName, email);
		company.setClientId(id);
		adminFacade.updateCompany(company);

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

		AdminFacade adminFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		adminFacade = (AdminFacade) clientBucket.getFacade();

		Customer customer = new Customer(customerName, password);
		adminFacade.createCustomer(customer);

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

		AdminFacade adminFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		adminFacade = (AdminFacade) clientBucket.getFacade();

		Customer customer = adminFacade.getCustomer(id);

		return customer;
	}

	@POST
	@Path("/remove_customer")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject removeCustomer(@FormParam("CUSTOMER_ID") int id)
			throws JSONException, CouponSystemException {

		AdminFacade adminFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		adminFacade = (AdminFacade) clientBucket.getFacade();

		adminFacade.removeCustomer(id);

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

		AdminFacade adminFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		adminFacade = (AdminFacade) clientBucket.getFacade();

		Collection<Customer> customers = adminFacade.getAllCustomers();

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

		AdminFacade adminFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		adminFacade = (AdminFacade) clientBucket.getFacade();

		Customer customer = new Customer(id, customerName);
		customer.setClientId(id);
		adminFacade.updateCustomer(customer);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "customer updated successfully");

		return jsonResponse;
	}

}
