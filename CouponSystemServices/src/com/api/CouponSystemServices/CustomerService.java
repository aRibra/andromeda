package com.api.CouponSystemServices;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
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
import com.couponsystem.beans.Company;
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.beans.Customer;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.facades.AdminFacade;
import com.couponsystem.facades.CompanyFacade;
import com.couponsystem.facades.CustomerFacade;
import com.couponsystem.helper.classes.ClientBucket;
import com.sun.jersey.spi.resource.Singleton;

import couponsystem.ejb.db.Income;
import couponsystem.ejb.db.IncomeType;

@Singleton
@Path("/customer_service")
public class CustomerService {

	@Context
	private HttpServletRequest request;

	private BusinessDelegate delegate;

	@PostConstruct
	public void init() {
		delegate = new BusinessDelegate();
	}

	@POST
	@Path("/purchase_coupon")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject purchaseCoupon(@FormParam("TITLE") String title,
			@FormParam("START_DATE_YEAR") String startDateYear,
			@FormParam("START_DATE_MONTH") String startDateMonth,
			@FormParam("START_DATE_DAY") String startDateDay,
			@FormParam("END_DATE_YEAR") String endDateYear,
			@FormParam("END_DATE_MONTH") String endDateMonth,
			@FormParam("END_DATE_DAY") String endDateDay,
			@FormParam("AMOUNT") int amount, @FormParam("TYPE") String type,
			@FormParam("MESSAGE") String message,
			@FormParam("PRICE") double price,
			@FormParam("IMAGE") String imagePath) throws JSONException,
			CouponSystemException {

		CustomerFacade customerFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		customerFacade = (CustomerFacade) clientBucket.getFacade();

		Calendar cal = Calendar.getInstance();
		Date startDate, endDate;

		cal.set(Calendar.YEAR, Integer.parseInt(startDateYear));
		cal.set(Calendar.MONTH, Integer.parseInt(startDateMonth));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDateDay));

		startDate = new Date(cal.getTime().getTime());

		cal.set(Calendar.YEAR, Integer.parseInt(endDateYear));
		cal.set(Calendar.MONTH, Integer.parseInt(endDateMonth));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(endDateDay));

		endDate = new Date(cal.getTime().getTime());

		CouponType couponType = CouponType.valueOf(type);

		// format from HTML date: 2016-05-17
		// SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		// Date ss = dateFormatter.parse();

		Coupon coupon = new Coupon(title, startDate, endDate, amount,
				couponType, message, price, imagePath);
		customerFacade.purchaseCoupon(coupon, clientBucket.getClient()
				.getClientId());

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "customer purchased coupon successfully!");

		// Income
		Income income = new Income();

		income.setExecutorName(clientBucket.getClient().getClientName());
		income.setDescription(IncomeType.CUSTOMER_PURCHASE);
		income.setAmount(amount);
		delegate.storeIncome(income);

		return jsonResponse;
	}

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
	public Collection<Coupon> getAllCoupons(
			@FormParam("CUSTOMER_ID") int customerId) throws JSONException,
			CouponSystemException {

		CustomerFacade customerFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		customerFacade = (CustomerFacade) clientBucket.getFacade();

		Collection<Coupon> coupons = customerFacade.getCoupons(customerId);

		return coupons;
	}

	@POST
	@Path("/get_customer_coupons_by_type")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCouponsByType(
			@FormParam("CUSTOMER_ID") int customerId,
			@FormParam("TYPE") String type) throws JSONException,
			CouponSystemException {

		CustomerFacade customerFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		customerFacade = (CustomerFacade) clientBucket.getFacade();

		CouponType couponType = CouponType.valueOf(type);

		Collection<Coupon> coupons = customerFacade
				.getAllPurchasedCouponsByType(couponType, customerId);

		return coupons;
	}

	@POST
	@Path("/get_customer_coupons_by_price")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCouponsByPrice(
			@FormParam("CUSTOMER_ID") int customerId,
			@FormParam("PRICE") double price) throws JSONException,
			CouponSystemException {

		CustomerFacade customerFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		customerFacade = (CustomerFacade) clientBucket.getFacade();

		Collection<Coupon> coupons = customerFacade
				.getAllPurchasedCouponsByPrice(price, customerId);

		return coupons;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {

		return "Hello CustomerService";
	}

}
