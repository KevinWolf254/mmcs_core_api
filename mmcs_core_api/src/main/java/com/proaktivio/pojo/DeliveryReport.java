package com.proaktivio.pojo;

import java.math.BigDecimal;

public class DeliveryReport extends Report {

	private String messageId;
    private int received;
    private int rejected;
    private String rejectedNos;
    private BigDecimal totalCost;
	public DeliveryReport() {
		super();
	}
	public DeliveryReport(int code, String title, String message, String messageId, int received, int rejected,
			String rejectedNos, BigDecimal totalCost) {
		super(code, title, message);
		this.messageId = messageId;
		this.received = received;
		this.rejected = rejected;
		this.rejectedNos = rejectedNos;
		this.totalCost = totalCost;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public int getReceived() {
		return received;
	}
	public void setReceived(int received) {
		this.received = received;
	}
	public int getRejected() {
		return rejected;
	}
	public void setRejected(int rejected) {
		this.rejected = rejected;
	}
	public String getRejectedNos() {
		return rejectedNos;
	}
	public void setRejectedNos(String rejectedNos) {
		this.rejectedNos = rejectedNos;
	}
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	} 
}
