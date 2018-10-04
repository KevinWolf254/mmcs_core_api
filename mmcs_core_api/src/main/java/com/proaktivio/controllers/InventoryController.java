package com.proaktivio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proaktivio.pojo.InventoryReport;
import com.proaktivio.services.InventoryService;

@RestController
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	//update inventory with the paid amount
	@PutMapping(value = "/inventory")
	public ResponseEntity<Object> add(@RequestParam("client") final Long id, @RequestParam("amount") final double amount,
			@RequestParam("payment") final String payment_currency,	@RequestParam("client") final String client_currency) {	
		
		final InventoryReport report = inventoryService.add(id, amount, payment_currency, client_currency);
		if(report.getCode() == 400)
			return new ResponseEntity<Object>(report, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(report, HttpStatus.OK);		
	}
}
