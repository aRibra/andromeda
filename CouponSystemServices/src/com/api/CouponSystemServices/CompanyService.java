package com.api.CouponSystemServices;

import java.text.ParseException;
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
import com.couponsystem.beans.Coupon;
import com.couponsystem.beans.CouponType;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.facades.CompanyFacade;
import com.couponsystem.helper.classes.ClientBucket;
import com.sun.jersey.spi.resource.Singleton;

import couponsystem.ejb.db.Income;
import couponsystem.ejb.db.IncomeType;

@Singleton
@Path("/company_service")
public class CompanyService {

	@Context
	private HttpServletRequest request;

	private BusinessDelegate delegate;
	
	@PostConstruct
	public void init(){
		delegate = new BusinessDelegate();
	}
	
	@POST
	@Path("/view_income")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Income> viewIncomeByCompany() throws JSONException,
			CouponSystemException {

		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		
		Collection<Income> incomeCollection = delegate.viewIncomeByCompany(clientBucket.getClient().getClientId());

		return incomeCollection;
	}
	
	@POST
	@Path("/create_coupon")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject createCoupon(@FormParam("TITLE") String title,
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

		CompanyFacade companyFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		
		companyFacade = (CompanyFacade) clientBucket.getFacade();
		
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
		companyFacade.createCoupon(coupon);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "company created successfully");
		
		
		Income income = new Income();
		
		income.setExecutorName(clientBucket.getClient().getClientName());
		income.setDescription(IncomeType.COMPANY_NEW_COUPON);
		income.setAmount(100);
		delegate.storeIncome(income);

		return jsonResponse;
	}

	@POST
	@Path("/get_coupon")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCoupon(@FormParam("COUPON_ID") String couponId)
			throws JSONException, CouponSystemException {

		CompanyFacade companyFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		companyFacade = (CompanyFacade) clientBucket.getFacade();

		Coupon coupon = companyFacade.getCoupon(Integer.parseInt(couponId));

		return coupon;
	}

	@POST
	@Path("/get_all_coupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupons(@FormParam("COMP_ID") int companyId) throws JSONException,
			CouponSystemException {

		CompanyFacade companyFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		companyFacade = (CompanyFacade) clientBucket.getFacade();

		Collection<Coupon> coupons = companyFacade.getCoupons(companyId);

		return coupons;
	}

	@POST
	@Path("/update_coupon")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject updateCoupon(@FormParam("COUPON_ID") int id,
			@FormParam("TITLE") String title,
			@FormParam("AMOUNT") String amount,
			@FormParam("START_DATE_YEAR") String startDateYear,
			@FormParam("START_DATE_MONTH") String startDateMonth,
			@FormParam("START_DATE_DAY") String startDateDay,
			@FormParam("END_DATE_YEAR") String endDateYear,
			@FormParam("END_DATE_MONTH") String endDateMonth,
			@FormParam("END_DATE_DAY") String endDateDay,
			@FormParam("MESSAGE") String message,
			@FormParam("PRICE") String price, @FormParam("TYPE") String type,
			@FormParam("IMAGE") String image) throws JSONException,
			CouponSystemException, ParseException {

		CompanyFacade companyFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		companyFacade = (CompanyFacade) clientBucket.getFacade();

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

		Coupon coupon = new Coupon(title, startDate, endDate,
				Integer.parseInt(amount), CouponType.valueOf(type), message,
				Double.parseDouble(price), image);

		coupon.setId(id);

		companyFacade.updateCoupon(coupon);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("success", true);
		jsonResponse.put("message", "coupon updated successfully");
		
		
		Income income = new Income();
		
		income.setExecutorName(clientBucket.getClient().getClientName());
		income.setDescription(IncomeType.COMPANY_UPDATE_COUPON);
		income.setAmount(10);
		delegate.storeIncome(income);

		return jsonResponse;
	}

	@POST
	@Path("/get_all_company_coupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByType(@FormParam("COMP_ID") int id,
			@FormParam("TYPE") String type) throws JSONException,
			CouponSystemException {

		CompanyFacade companyFacade = null;
		ClientBucket clientBucket = null;

		HttpSession session = request.getSession(false);
		clientBucket = (ClientBucket) session.getAttribute("clientBucket");
		companyFacade = (CompanyFacade) clientBucket.getFacade();

		Collection<Coupon> coupons = companyFacade.getCouponByType(id,
				CouponType.valueOf(type));

		return coupons;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Service: CompanyService";
	}

}
