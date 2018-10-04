package com.proaktivio.pojo;

import com.proaktivio.models.ClientUser;

public class UserReport extends Report {

	private ClientUser user;

	public UserReport() {
		super();
	}

	public UserReport(int code, String title, 
			String message, ClientUser user) {
		super(code, title, message);
		this.user = user;
	}

	public ClientUser getUser() {
		return user;
	}

	public void setUser(ClientUser user) {
		this.user = user;
	}
	
}
