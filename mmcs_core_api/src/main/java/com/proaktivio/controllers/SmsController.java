package com.proaktivio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proaktivio.pojo.Sms;
import com.proaktivio.pojo.SmsDeliveryReport;
import com.proaktivio.services.SmsService;

@RestController
public class SmsController {
	@Autowired
	private SmsService smsService;

	@PostMapping(value = "/sms")
	public ResponseEntity<Object> sendSms(@RequestBody Sms sms){
		final SmsDeliveryReport report = smsService.sendSms(sms);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
}
