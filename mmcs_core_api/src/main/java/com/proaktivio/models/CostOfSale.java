package com.proaktivio.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.proaktivio.enums.ChargeType;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, 
property="id")
@Entity
@Table(name="cost_of_sale")
public class CostOfSale {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="number", nullable=false)
	private String number;
	
	@Column(name="type")
	private ChargeType type;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "currency")
	private Country currency;
	
	@Column(name="amount", nullable=false)
	private double amount;
	
	@Column(name="date", nullable=false)
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "product_id")
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "client_id")
	private Client client;

	public CostOfSale() {
		super();
	}
	public CostOfSale(String number, ChargeType type, Country currency, double amount, 
			Product product, Client client) {
		super();
		this.number = number;
		this.type = type;
		this.currency = currency;
		this.amount = amount;
		this.date = new Date();
		this.product = product;
		this.client = client;
	}
	public CostOfSale(String number, ChargeType type, Country currency, double amount) {
		super();
		this.number = number;
		this.type = type;
		this.currency = currency;
		this.amount = amount;
		this.date = new Date();	
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public ChargeType getType() {
		return type;
	}
	public void setType(ChargeType type) {
		this.type = type;
	}
	public Country getCurrency() {
		return currency;
	}
	public void setCurrency(Country currency) {
		this.currency = currency;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("CostOfSale [id=").append(id)
				.append(", number=").append(number)
				.append(", type=").append(type)
				.append(", currency=").append(currency)
				.append(", amount=").append(amount)
				.append(", date=").append(date)
				.append(", ").append(product)
				.append(", ").append(client)
				.append("]");
		return builder.toString();
	}
}
