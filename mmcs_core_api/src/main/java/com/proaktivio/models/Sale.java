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
import com.proaktivio.enums.SaleType;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, 
property="id")
@Entity
@Table(name="sale")
public class Sale {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="invoice_no")
	private String invoiceNo;
	
	@Column(name="code", nullable=false, unique=true)
	private String code;
	
	@Column(name="type", nullable=false)
	private SaleType type;
	
	@Column(name="successful", nullable=false)
	private boolean successful;
	
	@Column(name="amount_info", nullable=false)
	private String amountInfo;
	
	@Column(name="date", nullable=false)
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	public Sale() {
		super();
	}
	public Sale(String code, SaleType type, boolean successful, String amountInfo,
			Product product, Client client) {
		super();
		this.code = code;
		this.type = type;
		this.successful = successful;
		this.amountInfo = amountInfo;
		this.date = new Date();
		this.product = product;
		this.client = client;
	}
	public Sale(String code, SaleType type, boolean successful, String amountInfo) {
		super();
		this.code = code;
		this.type = type;
		this.successful = successful;
		this.amountInfo = amountInfo;
		this.date = new Date();	
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public SaleType getType() {
		return type;
	}
	public void setType(SaleType type) {
		this.type = type;
	}
	public boolean isSuccessful() {
		return successful;
	}
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	public String getAmountInfo() {
		return amountInfo;
	}
	public void setAmountInfo(String amountInfo) {
		this.amountInfo = amountInfo;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		Sale other = (Sale) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Sale [id=").append(id)
				.append(", invoiceNo=").append(invoiceNo)
				.append(", code=").append(code)
				.append(", type=").append(type)
				.append(", successful=").append(successful)
				.append(", amountInfo=").append(amountInfo)
				.append(", date=").append(date)
				.append(", ").append(product)
				.append(", ").append(client)
				.append("]");
		return builder.toString();
	}
}
