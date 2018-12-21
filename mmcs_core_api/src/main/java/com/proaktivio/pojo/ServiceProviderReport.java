package com.proaktivio.pojo;

import com.proaktivio.models.ServiceProvider;

public class ServiceProviderReport {

	private ServiceProvider provider;
	private long subscribers;
	public ServiceProviderReport() {
		super();
	}
	public ServiceProviderReport(ServiceProvider provider, long subscribers) {
		super();
		this.provider = provider;
		this.subscribers = subscribers;
	}
	public ServiceProvider getProvider() {
		return provider;
	}
	public void setProvider(ServiceProvider provider) {
		this.provider = provider;
	}
	public long getSubscribers() {
		return subscribers;
	}
	public void setSubscribers(long subscribers) {
		this.subscribers = subscribers;
	}
	@Override
	public String toString() {
		return "ServiceProviderReport [provider=" + provider + ", subscribers=" + subscribers + "]";
	}	
}
