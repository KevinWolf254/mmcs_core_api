package com.proaktivio.models;

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

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="price")
public class Price {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "currency", nullable = false)
	private Country currency;
	
	@Column(name="tax", nullable=false)
	private double tax;
	
	@Column(name="margin", nullable=false)
	private double margin;
	
	@Column(name="amount", nullable=false)
	private double amount;
	
	@Column(name="total", nullable=false)
	private double total;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "charge_id", nullable = false)
	private Charge charge;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "international_price_id")
	private InternationalPrice internationalPrice;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	public Price() {
		super();
	}
	public Price(Country currency, double tax, double margin, double amount, Charge charge, Product product) {
		super();
		this.currency = currency;
		this.tax = tax;
		this.margin = margin;
		this.amount = amount;
		this.total = calculateTotal(tax, margin, amount);
		this.charge = charge;
		this.product = product;
	}
	private double calculateTotal(double tax, double margin, double amount) {
		return (1 + tax) * ((1 + margin) * amount);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Country getCurrency() {
		return currency;
	}
	public void setCurrency(Country currency) {
		this.currency = currency;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getMargin() {
		return margin;
	}
	public void setMargin(double margin) {
		this.margin = margin;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double tax, double margin, double amount) {
		this.total = calculateTotal(tax, margin, amount);
	}
	public Charge getCharge() {
		return charge;
	}
	public void setCharge(Charge charge) {
		this.charge = charge;
	}
	public InternationalPrice getInternationalPrice() {
		return internationalPrice;
	}
	public void setInternationalPrice(InternationalPrice internationalPrice) {
		this.internationalPrice = internationalPrice;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Price [id=").append(id)
				.append(", tax=").append(tax)
				.append(", margin=").append(margin)
				.append(", amount=").append(amount)
				.append(", total=").append(total)
				.append(", ").append(charge)
				.append(", ").append(internationalPrice)
				.append(", ").append(product)
				.append("]");
		return builder.toString();
	}
}
