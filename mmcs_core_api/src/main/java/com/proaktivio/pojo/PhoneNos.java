package com.proaktivio.pojo;

import com.proaktivio.models.ServiceProvider;

public class PhoneNos {

	private ServiceProvider serviceProvider;
	private long totals;
	public PhoneNos() {
		super();
	}
	public PhoneNos(ServiceProvider telecom, long totals) {
		super();
		this.serviceProvider = telecom;
		this.totals = totals;
	}
	public long getTotals() {
		return totals;
	}
	public void setTotals(int totals) {
		this.totals = totals;
	}
	public ServiceProvider getTelecom() {
		return serviceProvider;
	}
	public void setTelecom(ServiceProvider telecom) {
		this.serviceProvider = telecom;
	}
}
