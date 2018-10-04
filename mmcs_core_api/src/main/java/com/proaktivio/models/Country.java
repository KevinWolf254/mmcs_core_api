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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name="country")
public class Country {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	@Column(name="code", length = 4, nullable=false, unique=true)
	private String code;
	
	@Column(name="currency", length = 3, nullable=false, unique=true)
	private String currency;
	
	@OneToMany(mappedBy = "country", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ServiceProvider> serviceProviders = new HashSet<ServiceProvider>();

	@OneToMany(mappedBy = "country", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Client> clients = new HashSet<Client>();

	@OneToMany(mappedBy = "currency", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Payment> payments = new HashSet<Payment>();
	
	@OneToMany(mappedBy = "currency", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<InternationalPrice> internationalPrices = new HashSet<InternationalPrice>();
	
	@OneToMany(mappedBy = "currency", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<InternationalCost> internationalCosts = new HashSet<InternationalCost>();

	@ManyToMany(mappedBy = "countries",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<SenderId> senderIds = new HashSet<SenderId>();
	
	@ManyToMany(mappedBy = "currency",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<CostOfSale> costsOfSales = new HashSet<CostOfSale>();

	@OneToMany(mappedBy = "from", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ExchangeRate> froms = new HashSet<ExchangeRate>();
	
	@OneToMany(mappedBy = "to", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ExchangeRate> tos = new HashSet<ExchangeRate>();
	
	@OneToMany(mappedBy = "currency", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Cost> Costs = new HashSet<Cost>();
	
	public Country() {
		super();
	}
	public Country(String name, String code, String currency) {
		super();
		this.name = name;
		this.code = code;
		this.currency = currency;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@JsonIgnore
	public Set<ServiceProvider> getServiceProviders() {
		return serviceProviders;
	}
	public void setServiceProviders(Set<ServiceProvider> serviceProviders) {
		this.serviceProviders = serviceProviders;
	}
	@JsonIgnore
	public Set<Client> getClients() {
		return clients;
	}
	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}
	@JsonIgnore
	public Set<Payment> getPayments() {
		return payments;
	}
	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}
	@JsonIgnore
	public Set<InternationalPrice> getInternationalPrices() {
		return internationalPrices;
	}
	public void setInternationalPrices(Set<InternationalPrice> internationalPrices) {
		this.internationalPrices = internationalPrices;
	}
	@JsonIgnore
	public Set<InternationalCost> getInternationalCosts() {
		return internationalCosts;
	}
	public void setInternationalCosts(Set<InternationalCost> internationalCosts) {
		this.internationalCosts = internationalCosts;
	}
	@JsonIgnore
	public Set<SenderId> getSenderIds() {
		return senderIds;
	}
	public void setSenderIds(Set<SenderId> senderIds) {
		this.senderIds = senderIds;
	}
	@JsonIgnore
	public Set<CostOfSale> getCostsOfSales() {
		return costsOfSales;
	}
	public void setCostsOfSales(Set<CostOfSale> costs) {
		this.costsOfSales = costs;
	}
	@JsonIgnore
	public Set<ExchangeRate> getFroms() {
		return froms;
	}
	public void setFroms(Set<ExchangeRate> froms) {
		this.froms = froms;
	}
	@JsonIgnore
	public Set<ExchangeRate> getTos() {
		return tos;
	}
	public void setTos(Set<ExchangeRate> tos) {
		this.tos = tos;
	}
	@JsonIgnore
	public Set<Cost> getCosts() {
		return Costs;
	}
	public void setCosts(Set<Cost> costs) {
		Costs = costs;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
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
		Country other = (Country) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
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
		builder.append("Country [id=").append(id)
				.append(", name=").append(name)
				.append(", code=").append(code)
				.append(", currency=").append(currency)
				.append("]");
		return builder.toString();
	}
}
