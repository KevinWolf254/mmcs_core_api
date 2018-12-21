package com.proaktivio.pojo;

import com.proaktivio.enums.ProductType;

public class PaymentConfirmation {

	private String productName;
	private ProductType productType;
	private PaymentType paymentType;
	private String email;
	private String currency;
	private double amount;
	private String mpesaNo;
	private String senderId;
	
	public PaymentConfirmation() {
		super();
	}	
	public PaymentConfirmation(String productName, ProductType productType, PaymentType paymentType, String email,
			String currency, double amount, String mpesaNo, String senderId) {
		super();
		this.productName = productName;
		this.productType = productType;
		this.paymentType = paymentType;
		this.email = email;
		this.currency = currency;
		this.amount = amount;
		this.mpesaNo = mpesaNo;
		this.senderId = senderId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getMpesaNo() {
		return mpesaNo;
	}
	public void setMpesaNo(String mpesaNo) {
		this.mpesaNo = mpesaNo;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}		
}
