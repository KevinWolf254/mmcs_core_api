package com.proaktivio.pojo;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.proaktivio.models.Client;

@SuppressWarnings("serial")
public class SignUpEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final Client client;

    public SignUpEvent(final Client client, 
    		final Locale locale, final String appUrl) {
        super(client);
        this.client = client;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public Client getClient() {
        return client;
    }

}
