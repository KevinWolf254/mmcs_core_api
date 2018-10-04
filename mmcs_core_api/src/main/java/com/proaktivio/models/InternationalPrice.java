package com.proaktivio.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="international_price")
public class InternationalPrice {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "currency")
	private Country currency;
	
	@Column(name="tax", nullable=false)
	private double tax;
	
	@Column(name="margin", nullable=false)
	private double margin;

	@Column(name="amount", nullable=false)
	private double amount;
	
	@Column(name="total", nullable=false)
	private double total;
	
	@OneToMany(mappedBy = "internationalPrice", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Price> prices = new HashSet<Price>();
	
	public InternationalPrice() {
		super();
	}
	public InternationalPrice(Country currency, double tax, double amount) {
		super();
		this.currency = currency;
		this.tax = tax;
		this.amount = amount;
		this.total = calculateTotal(tax, amount);
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
	public double getMargin() {
		return margin;
	}
	public void setMargin(double margin) {
		this.margin = margin;
	}
	@JsonIgnore
	public Set<Price> getPrices() {
		return prices;
	}
	public void setPrices(Set<Price> prices) {
		this.prices = prices;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("InternationalPrice [id=").append(id)
				.append(", currency=").append(currency)
				.append(", tax=").append(tax)
				.append(", amount=").append(amount)
				.append(", total=").append(total)
				.append("]");
		return builder.toString();
	}
}
