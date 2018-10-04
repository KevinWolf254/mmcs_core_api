package com.proaktivio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proaktivio.pojo.Credit;
import com.proaktivio.services.ClientService;

@RestController
public class CreditController {
	
	@Autowired
	private ClientService clientService;
	
//	@PostMapping(value = "/credit")
//	public ResponseEntity<Object> find(@RequestParam("email") final String email) {
//		
//		final ClientAdmin admin = adminService.findByEmail(email).get();
//		final Client client = admin.getClient();
//		final CreditReport report = creditService.findCreditReport(client);
//		return new ResponseEntity<Object>(report, HttpStatus.OK);
//	}
	
//	@GetMapping(value = "/credit/{id}")
//	public ResponseEntity<Object> findByClientId(@PathVariable("id") final Long id) {
//		
//		final Client client = clientService.findById(id).get();
//		final CreditReport report = creditService.findCreditReport(client);
//		return new ResponseEntity<Object>(report, HttpStatus.OK);
//	}
	
//	@PutMapping(value = "/credit")
//	public ResponseEntity<Object> subtract(@RequestParam("client") final Long id,
//			@RequestParam("amount") final double amount) {
//		final ClientReport report = clientService.subtractCredit(id, amount);		
//		return new ResponseEntity<Object>(report, HttpStatus.OK);
//	}

}
