package com.proaktivio.pojo;

import java.util.Date;

public class Invoice {

	private String number;
	private String customerId;
	private String customerName;
	private String userEmail;
	private String userPhoneNo;
	private String purchaseDescription;
	private String purchaseBy;
	private Date purchaseDate;
	private String transactionId;
	private String currency;
	private double amount;
	public Invoice() {
		super();
	}
	public Invoice(String number, String customerId, String customerName, String userEmail, String userPhoneNo,
			String purchaseDescription, String purchaseBy, Date purchaseDate, String transactionId, String currency,
			double amount) {
		super();
		this.number = number;
		this.customerId = customerId;
		this.customerName = customerName;
		this.userEmail = userEmail;
		this.userPhoneNo = userPhoneNo;
		this.purchaseDescription = purchaseDescription;
		this.purchaseBy = purchaseBy;
		this.purchaseDate = purchaseDate;
		this.transactionId = transactionId;
		this.currency = currency;
		this.amount = amount;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPhoneNo() {
		return userPhoneNo;
	}
	public void setUserPhoneNo(String userPhoneNo) {
		this.userPhoneNo = userPhoneNo;
	}
	public String getPurchaseDescription() {
		return purchaseDescription;
	}
	public void setPurchaseDescription(String purchaseDescription) {
		this.purchaseDescription = purchaseDescription;
	}
	public String getPurchaseBy() {
		return purchaseBy;
	}
	public void setPurchaseBy(String purchaseBy) {
		this.purchaseBy = purchaseBy;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
