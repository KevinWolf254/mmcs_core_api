package com.proaktivio.pojo;

import com.proaktivio.models.Client;
import com.proaktivio.models.ClientUser;

public class SignUp extends Report {

	private Client client;
	private ClientUser user;
	
	public SignUp() {
		super();
	}
	public SignUp(int code, String title, String message) {
		super(code, title, message);
	}
	public SignUp(int code, String title,	String message, 
			Client client, ClientUser user) {
		super(code, title, message);
		this.client = client;
		this.user = user;
	}	
	public SignUp(Report report, Client client, ClientUser user) {
		super(report.getCode(), report.getTitle(), report.getMessage());
		this.client = client;
		this.user = user;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public ClientUser getUser() {
		return user;
	}
	public void setUser(ClientUser user) {
		this.user = user;
	}
}
