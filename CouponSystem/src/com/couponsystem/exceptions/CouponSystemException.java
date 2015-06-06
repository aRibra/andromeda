package com.couponsystem.exceptions;

import java.util.HashMap;
import java.util.Map;

public class CouponSystemException extends Exception {

	private String couponSystemExceptionMessage;
	private String exceptionStackTrace;
	private Map<String, String> exceptions = new HashMap<String, String>();
	CouponSystemExceptionsProperties exception;

	public CouponSystemException(String couponSystemExceptionMessage) {

		this.setMessage(couponSystemExceptionMessage);

	}

	public CouponSystemException(String couponSystemExceptionMessage,
			String exceptionStackTrace) {

		this.setMessage(couponSystemExceptionMessage);
		this.setExceptionStackTrace(exceptionStackTrace);

	}

	public void setMessage(String couponSystemExceptionMessage) {
		this.couponSystemExceptionMessage = couponSystemExceptionMessage;
	}

	public String getMaessage() {
		return this.couponSystemExceptionMessage;
	}

	public String getExceptionStackTrace() {
		return exceptionStackTrace;
	}

	public void setExceptionStackTrace(String exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}

}
