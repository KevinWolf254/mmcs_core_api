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
@Table(name="product")
public class Product {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;
	
	@OneToMany(mappedBy = "product", orphanRemoval=true,
	fetch = FetchType.LAZY, 
	cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ServiceProvider> serviceProviders = new HashSet<ServiceProvider>();
	
	@OneToMany(mappedBy = "product", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Sale> sales = new HashSet<Sale>();
	
	@OneToMany(mappedBy = "product", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<CostOfSale> costsOfSales = new HashSet<CostOfSale>();
	
	@OneToMany(mappedBy = "product", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Cost> costs = new HashSet<Cost>();
	
	@OneToMany(mappedBy = "product", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Price> prices = new HashSet<Price>();
	
	public Product() {
		super();
	}
	public Product(String name, Country country) {
		super();
		this.name = name;
		this.country = country;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	@JsonIgnore
	public Set<Sale> getSales() {
		return sales;
	}
	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}
	@JsonIgnore
	public Set<CostOfSale> getCostsOfSales() {
		return costsOfSales;
	}
	public void setCostsOfSales(Set<CostOfSale> costsOfSales) {
		this.costsOfSales = costsOfSales;
	}
	@JsonIgnore
	public Set<Cost> getCosts() {
		return costs;
	}
	public void setCosts(Set<Cost> costs) {
		this.costs = costs;
	}
	@JsonIgnore
	public Set<Price> getPrices() {
		return prices;
	}
	public void setPrices(Set<Price> prices) {
		this.prices = prices;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Product [id=").append(id)
				.append(", name=").append(name)
				.append(", ").append(country)
				.append("]");
		return builder.toString();
	}
}
