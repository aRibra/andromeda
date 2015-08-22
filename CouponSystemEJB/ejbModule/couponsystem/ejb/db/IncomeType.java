package couponsystem.ejb.db;

public enum IncomeType {

	CUSTOMER_PURCHASE("CUSTOMER_PURCHASE"), 
	COMPANY_NEW_COUPON("COMPANY_NEW_COUPON"), 
	COMPANY_UPDATE_COUPON("COMPANY_UPDATE_COUPON");

	private String desciprtion;

	private IncomeType(String desciption) {
		this.desciprtion = desciption;
	}

	public String getDescrition() {
		return desciprtion;
	}

}
