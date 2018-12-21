package com.proaktivio.pojo;

import com.proaktivio.models.Country;

public class ChargesReport extends Report{

	private Country currency;
	private double estimatedCost;
	private int totalContacts;
	public Country getCurrency() {
		return currency;
	}
	public ChargesReport() {
		super();
	}
	public ChargesReport(int code, String title, String message) {
		super(code, title, message);
	}
	public ChargesReport(int code, String title, String message, Country currency, double charge, int totalContacts) {
		super(code, title, message);
		this.currency = currency;
		this.estimatedCost = charge;
		this.totalContacts = totalContacts;
	}
	public void setCurrency(Country currency) {
		this.currency = currency;
	}
	public double getEstimatedCost() {
		return estimatedCost;
	}
	public void setEstimatedCost(double estimatedCost) {
		this.estimatedCost = estimatedCost;
	}
	public int getTotalContacts() {
		return totalContacts;
	}
	public void setTotalContacts(int totalContacts) {
		this.totalContacts = totalContacts;
	}
}
