package com.proaktivio.pojo;

import java.util.Collection;

public class Sms {

	private String sender;
	private String senderId;
	private String recipients;
	private Collection<PhoneNos> phoneNosTotals;
	private String message;
	public Sms() {
		super();
	}
	public Sms(String sender, String senderId, String recipients, 
			Collection<PhoneNos> phoneNosTotals, String message) {
		super();
		this.sender = sender;
		this.senderId = senderId;
		this.recipients = recipients;
		this.phoneNosTotals = phoneNosTotals;
		this.message = message;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Collection<PhoneNos> getPhoneNosTotals() {
		return phoneNosTotals;
	}
	public void setPhoneNosTotals(Collection<PhoneNos> phoneNosTotals) {
		this.phoneNosTotals = phoneNosTotals;
	}
}
