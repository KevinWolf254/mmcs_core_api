package com.proaktivio.services;

import com.proaktivio.pojo.Sms;
import com.proaktivio.pojo.SmsDeliveryReport;

public interface SmsService {

	/**
	 * sends an SMS to Africa's talking API
	 * @param smsInfo
	 * @return SmsDeliveryReport
	 */
	public SmsDeliveryReport sendSms(Sms smsInfo);
}
