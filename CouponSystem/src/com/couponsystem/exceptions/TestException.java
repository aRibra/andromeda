package com.couponsystem.exceptions;

public class TestException extends Exception {

	private int maxNumOfTransports;
	private String exceptionMsg;

	public TestException(int maxNumOfTransports) {
		this.maxNumOfTransports = maxNumOfTransports;
		this.exceptionMsg = "ParkingSpaceFullException: Parking Lot is full! - remove transports to be able to add another."
				+ " Number of current transports in lot is (max): "
				+ this.maxNumOfTransports;
	}

	public String getMaessage() {
		return this.exceptionMsg;
	}

	public int getMaxNumOfTransports() {
		return this.maxNumOfTransports;
	}

}
