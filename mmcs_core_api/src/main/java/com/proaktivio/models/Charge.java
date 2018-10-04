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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.proaktivio.enums.ChargeType;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="charge")
public class Charge {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="type", nullable=false)
	private ChargeType type;
	
	@OneToMany(mappedBy = "charge", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Cost> costs = new HashSet<Cost>();
	
	@OneToMany(mappedBy = "charge", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Price> prices = new HashSet<Price>();

	public Charge() {
		super();
	}
	public Charge(ChargeType type) {
		super();
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ChargeType getType() {
		return type;
	}
	public void setType(ChargeType type) {
		this.type = type;
	}
	public Set<Cost> getCosts() {
		return costs;
	}
	public void setCosts(Set<Cost> costs) {
		this.costs = costs;
	}
	public Set<Price> getPrices() {
		return prices;
	}
	public void setPrices(Set<Price> prices) {
		this.prices = prices;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Charge [id=").append(id)
				.append(", type=").append(type)
				.append("]");
		return builder.toString();
	}
}
