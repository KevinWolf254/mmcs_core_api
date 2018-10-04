package com.proaktivio.pojo;

import com.proaktivio.models.Client;

public class Credit extends Report {

	private Client client;
	private Disbursement disbursement;
	public Credit() {
		super();
	}
	public Credit(int code, String title, String message, Client client, Disbursement disbursement) {
		super(code, title, message);
		this.client = client;
		this.disbursement = disbursement;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Disbursement getDisbursement() {
		return disbursement;
	}
	public void setDisbursement(Disbursement disbursement) {
		this.disbursement = disbursement;
	}	
}
