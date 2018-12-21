package com.proaktivio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proaktivio.pojo.ChargesReport;
import com.proaktivio.pojo.Sms;
import com.proaktivio.services.ChargesService;

@RestController
public class ChargesController {

	@Autowired
	private ChargesService chargesService;

	@PostMapping(value = "/charges")
	public ResponseEntity<Object> getCharges(@RequestBody Sms sms){
		final ChargesReport report = chargesService.calculate(sms);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
}