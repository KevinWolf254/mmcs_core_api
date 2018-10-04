package com.proaktivio.pojo;

import com.proaktivio.enums.ProductType;

public class _Payment {

	private String productName;
	private ProductType type;
	private String email;
	private double amount;
	private String mpesaNo;
	private String senderId;
	
	public _Payment() {
		super();
	}
	public _Payment(String productName, ProductType type, String email, double amount, 
			String mpesaNo, String senderId) {
		super();
		this.productName = productName;
		this.type = type;
		this.email = email;
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
	public ProductType getType() {
		return type;
	}
	public void setType(ProductType type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
