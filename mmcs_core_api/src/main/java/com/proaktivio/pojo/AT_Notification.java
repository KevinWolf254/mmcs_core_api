package com.proaktivio.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Mpesa Payment notification received from Africa's talking API
 * @author kevin Kanyi
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AT_Notification {

	private String transactionId;
	private String category;
	private String provider;
	private String providerRefId;	
	private String clientAccount;
	private String providerChannel;
	private String productName;	
	private String sourceType;
	private String source;	
	private String destinationType;	
	private String destination;	
	private String value;	
	private String transactionFee;	
	private String providerFee;
	private String status;	
	private String description;	
	private RequestMetaData requestMetadata;	
	private ProviderMetaData providerMetadata;	
	private String transactionDate;
	
	public AT_Notification() {
		super();
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getProviderRefId() {
		return providerRefId;
	}
	public void setProviderRefId(String providerRefId) {
		this.providerRefId = providerRefId;
	}
	public String getProviderChannel() {
		return providerChannel;
	}
	public void setProviderChannel(String providerChannel) {
		this.providerChannel = providerChannel;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSourceType() {
		return sourceType;
	}
	public String getClientAccount() {
		return clientAccount;
	}
	public void setClientAccount(String clientAccount) {
		this.clientAccount = clientAccount;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTransactionFee() {
		return transactionFee;
	}
	public void setTransactionFee(String transactionFee) {
		this.transactionFee = transactionFee;
	}
	public String getProviderFee() {
		return providerFee;
	}
	public void setProviderFee(String providerFee) {
		this.providerFee = providerFee;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public RequestMetaData getRequestMetadata() {
		return requestMetadata;
	}
	public void setRequestMetadata(RequestMetaData requestMetadata) {
		this.requestMetadata = requestMetadata;
	}
	public ProviderMetaData getProviderMetadata() {
		return providerMetadata;
	}
	public void setProviderMetadata(ProviderMetaData providerMetadata) {
		this.providerMetadata = providerMetadata;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}	
}
