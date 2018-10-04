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
@Table(name="cost")
public class Cost {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "currency", nullable = false)
	private Country currency;
	
	@Column(name="tax", nullable=false)
	private double tax;
	
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
	@JoinColumn(name = "international_cost_id")
	private InternationalCost internationalCost;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	public Cost() {
		super();
	}
	public Cost(Country currency, double tax, double amount, Charge charge, Product product) {
		super();
		this.currency = currency;
		this.tax = tax;
		this.amount = amount;
		this.total = calculateTotal(tax, amount);
		this.charge = charge;
		this.product = product;
	}
	private double calculateTotal(double tax, double amount) {
		return (1 + tax) * amount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double tax, double amount) {
		this.total = calculateTotal(tax, amount);
	}
	public Charge getCharge() {
		return charge;
	}
	public void setCharge(Charge charge) {
		this.charge = charge;
	}
	public InternationalCost getInternationalCost() {
		return internationalCost;
	}
	public void setInternationalCost(InternationalCost internationalCost) {
		this.internationalCost = internationalCost;
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
		builder.append("Cost [id=").append(id)
				.append(", tax=").append(tax)
				.append(", amount=").append(amount)
				.append(", total=").append(total)
				.append(", charge=").append(charge)
				.append(", ").append(internationalCost)
				.append(", ").append(product)
				.append("]");
		return builder.toString();
	}
}
