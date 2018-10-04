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

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, 
property="id")
@Entity
@Table(name="exchange_rate")
public class ExchangeRate {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "from")
	private Country from;

	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "to")
	private Country to;
	
	@Column(name="rate", nullable=false)
	private double rate;
	
	public ExchangeRate() {
		super();
	}	
	public ExchangeRate(Country from, Country to, double rate) {
		super();
		this.from = from;
		this.to = to;
		this.rate = rate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Country getFrom() {
		return from;
	}
	public void setFrom(Country from) {
		this.from = from;
	}
	public Country getTo() {
		return to;
	}
	public void setTo(Country to) {
		this.to = to;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ExchangeRate [id=").append(id)
				.append(", from=").append(from)
				.append(", to=").append(to)
				.append(", rate=").append(rate)
				.append("]");
		return builder.toString();
	}
}
