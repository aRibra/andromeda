package com.couponsystem;

public class CouponDbNames {

	// *** Tables' names ***//
	public static final String ADMIN = "ADMIN";
	public static final String COMPANY = "COMPANY";
	public static final String CUSTOMER = "CUSTOMER";
	public static final String COUPON = "COUPON";
	public static final String CUSTOMER_COUPON = "CUSTOMER_COUPON";
	public static final String COMPANY_COUPON = "COMPANY_COUPON";
	// *** END Tables' names ***//

	// *** Columns' names ***//
	public static final String ADMIN_ID = "ID";
	public static final String ADMIN_ADMIN_NAME = "ADMIN_NAME";
	public static final String ADMIN_PASSWORD = "PASSWORD";
	public static final String ADMIN_CLIENT_TYPE = "CLIENT_TYPE";

	public static final String COMPANY_ID = "ID";
	public static final String COMPANY_COMP_NAME = "COMP_NAME";
	public static final String COMPANY_PASSWORD = "PASSWORD";
	public static final String COMPANY_EMAIL = "EMAIL";
	public static final String COMPANY_CLIENT_TYPE = "CLIENT_TYPE";

	public static final String CUSTOMER_ID = "ID";
	public static final String CUSTOMER_CUST_NAME = "CUST_NAME";
	public static final String CUSTOMER_PASSWORD = "PASSWORD";
	public static final String CUSTOMER_CLIENT_TYPE = "CLIENT_TYPE";

	public static final String COUPON_ID = "ID";
	public static final String COUPON_TITLE = "TITLE";
	public static final String COUPON_START_DATE = "START_DATE";
	public static final String COUPON_END_DATE = "END_DATE";
	public static final String COUPON_AMOUNT = "AMOUNT";
	public static final String COUPON_TYPE = "TYPE";
	public static final String COUPON_MESSAGE = "MESSAGE";
	public static final String COUPON_PRICE = "PRICE";
	public static final String COUPON_IMAGE = "IMAGE";

	public static final String CUSTOMER_COUPON_CUST_ID = "CUST_ID";
	public static final String CUSTOMER_COUPON_COUPON_ID = "COUPON_ID";

	public static final String COMPANY_COUPON_COMP_ID = "COMP_ID";
	public static final String COMPANY_COUPON_COUPON_ID = "COUPON_ID";

	// *** END Columns' names ***//

}
