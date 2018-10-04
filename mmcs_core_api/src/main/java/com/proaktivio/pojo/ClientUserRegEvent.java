package com.proaktivio.pojo;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.proaktivio.models.ClientUser;

@SuppressWarnings("serial")
public class ClientUserRegEvent extends ApplicationEvent {

    private final String rawPassword;
    private final Locale locale;
    private final ClientUser user;
	public ClientUserRegEvent(final ClientUser user, final Locale locale, 
			final String rawPassword) {
		super(user);
		this.rawPassword = rawPassword;
		this.locale = locale;
		this.user = user;
	}
	public String getRawPassword() {
		return rawPassword;
	}
	public Locale getLocale() {
		return locale;
	}
	public ClientUser getUser() {
		return user;
	}
}
