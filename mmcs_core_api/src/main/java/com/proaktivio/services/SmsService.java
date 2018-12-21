package com.proaktivio.services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import com.proaktivio.models.Charge;
import com.proaktivio.models.Country;
import com.proaktivio.pojo.ServiceProviderReport;
import com.proaktivio.pojo.Sms;
import com.proaktivio.pojo.SmsDeliveryReport;

public interface SmsService {

	/**
	 * sends an SMS to Africa's talking API
	 * @param smsInfo
	 * @return SmsDeliveryReport
	 */
	public SmsDeliveryReport sendSms(Sms smsInfo);
	
	/**
	 * Calculates an estimated cost for sending an SMS
	 * @param clientCountry
	 * @param raised_countries
	 * @param senderId
	 * @param phoneNosTotals
	 * @param message
	 * @param charge
	 * @return
	 */
	public BigDecimal estimateCost(Country clientCountry, Set<Country> raised_countries,
			String senderId, Collection<ServiceProviderReport> phoneNosTotals, String message,
			Charge charge);
}
