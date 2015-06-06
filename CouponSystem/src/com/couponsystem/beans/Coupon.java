package com.couponsystem.beans;

import java.util.Date;
import java.util.Calendar;

import com.couponsystem.CouponSystem;
import com.couponsystem.exceptions.CouponSystemException;

public class Coupon {

	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType couponType;
	private String message;
	private double price;
	private String image;

	public Coupon() {

	}

	public Coupon(String title, Date startDate, Date endDate, int amount,
			CouponType type, String message, double price, String image)
			throws CouponSystemException {
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setType(type);
		this.message = message;
		setPrice(price);
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) throws CouponSystemException {

		if (id < 0 || id > Long.MAX_VALUE)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error10"));

		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws CouponSystemException {

		if (title.isEmpty() || title == null)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error11"));

		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) throws CouponSystemException {

		Date today = new Date();
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(today);

		Calendar startDateCal = Calendar.getInstance();
		startDateCal.setTime(startDate);

		if (startDateCal.before(todayCal))
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error12"));

		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) throws CouponSystemException {

		Date today = new Date();
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(today);

		Calendar endDateCal = Calendar.getInstance();
		endDateCal.setTime(endDate);

		if (endDateCal.before(todayCal))
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error13"));

		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) throws CouponSystemException {

		if (amount < 0 || amount > Integer.MAX_VALUE)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error14"));

		this.amount = amount;
	}

	public CouponType getType() {
		return couponType;
	}

	public void setType(CouponType type) throws CouponSystemException {

		if (type == null)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error15"));

		this.couponType = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) throws CouponSystemException {

		if (amount < 0 || price > Double.MAX_VALUE)
			throw new CouponSystemException(
					CouponSystem.couponSystemExceptions
							.get("couponsystem.exception.error16"));

		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "--(Coupon)--" + "\n" + "ID=" + id + "\n" + "Title=" + title
				+ "\n" + "StartDate=" + startDate + "\n" + "EndDate=" + endDate
				+ "\n" + "Amount=" + amount + "\n" + "Type=" + couponType
				+ "\n" + "Message=" + message + "\n" + "Price=" + price + "\n"
				+ "Image=" + image + "\n" + "-----------";
	}
}
