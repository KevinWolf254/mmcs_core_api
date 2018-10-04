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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, 
property="id")
@Entity
@Table(name="credit_disbursment")
public class CreditDisbursment {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="pending", nullable=false)
	private boolean pending;
	
	@Column(name="date")
	private Date date;
	
	@OneToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "sale_id", nullable = false)
	private Sale sale;

	public CreditDisbursment() {
		super();
	}
	public CreditDisbursment(boolean pending, Date date, Sale sale) {
		super();
		this.pending = pending;
		this.date = date;
		this.sale = sale;
	}
	public CreditDisbursment(boolean pending, Sale sale) {
		super();
		this.pending = pending;
		this.sale = sale;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isPending() {
		return pending;
	}
	public void setPending(boolean pending) {
		this.pending = pending;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@JsonIgnore
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("CreditDisbursment [id=").append(id)
				.append(", pending=").append(pending)
				.append(", date=").append(date)
				.append(", ").append(sale)
				.append("]");
		return builder.toString();
	}
}
