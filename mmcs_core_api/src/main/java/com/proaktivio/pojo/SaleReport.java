package com.proaktivio.pojo;

import com.proaktivio.models.CostOfSale;
import com.proaktivio.models.Payment;
import com.proaktivio.models.Sale;

public class SaleReport extends Report{

	private Sale sale;
	private Payment payment;
	private CostOfSale cost;
	
	public SaleReport() {
		super();
	}
	public SaleReport(Sale sale, Payment payment, CostOfSale cost) {
		super();
		this.sale = sale;
		this.payment = payment;
		this.cost = cost;
	}
	public SaleReport(int code, String title, String message, 
			Sale sale, Payment payment, CostOfSale cost) {
		super(code, title, message);
		this.sale = sale;
		this.payment = payment;
		this.cost = cost;
	}
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public CostOfSale getCost() {
		return cost;
	}
	public void setCost(CostOfSale cost) {
		this.cost = cost;
	}	
}
