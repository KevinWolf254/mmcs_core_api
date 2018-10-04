package com.proaktivio.models;

import java.util.Date;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, 
property="id")
@Entity
@Table(name="client")
public class Client {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="name", nullable = false, unique=true)
	private String name;
	
	@Column(name="credit_amount", nullable = false)
	private double creditAmount;
	
	@Column(name="enabled", nullable = false)
	private boolean enabled;
	
	@Column(name="created_on", nullable = false)
	private Date createdOn;
	
	@ManyToOne(fetch = FetchType.EAGER, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;

	@OneToMany(mappedBy = "client", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ClientUser> users = new HashSet<ClientUser>();

	@OneToMany(mappedBy = "client", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<SenderId> senderIds = new HashSet<SenderId>();
	
	@OneToMany(mappedBy = "client", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Sale> sales = new HashSet<Sale>();
	
	@OneToMany(mappedBy = "client", orphanRemoval=true,
			fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<CostOfSale> costs = new HashSet<CostOfSale>();
	
	public Client() {
		super();
	}
	public Client(String name, Country country) {
		super();
		this.name = name;
		this.creditAmount = 0.0;
		this.enabled = Boolean.FALSE;
		this.createdOn = new Date();
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
	public double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	@JsonIgnore
	public Set<ClientUser> getUsers() {
		return users;
	}
	public void setUsers(Set<ClientUser> users) {
		this.users = users;
	}
	@JsonIgnore
	public Set<SenderId> getSenderIds() {
		return senderIds;
	}
	public void setSenderIds(Set<SenderId> senderIds) {
		this.senderIds = senderIds;
	}
	@JsonIgnore
	public Set<Sale> getSales() {
		return sales;
	}
	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}
	@JsonIgnore
	public Set<CostOfSale> getCosts() {
		return costs;
	}
	public void setCosts(Set<CostOfSale> costs) {
		this.costs = costs;
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
		Client other = (Client) obj;
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
		builder.append("Client [id=").append(id)
				.append(", name=").append(name)
				.append(", creditAmount=").append(creditAmount)
				.append(", enabled=").append(enabled)
				.append(", createdOn=").append(createdOn)
				.append(", ").append(country)
				.append("]");
		return builder.toString();
	}
}
