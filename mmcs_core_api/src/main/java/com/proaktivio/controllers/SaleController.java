package com.proaktivio.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proaktivio.models.Client;
import com.proaktivio.models.Sale;
import com.proaktivio.pojo.PurchaseRequest;
import com.proaktivio.pojo.Report;
import com.proaktivio.pojo.SaleReport;
import com.proaktivio.pojo.PaymentConfirmation;
import com.proaktivio.pojo.AT_Notification;
import com.proaktivio.services.ClientService;
import com.proaktivio.services.SaleService;

@RestController
public class SaleController {
	@Autowired
	private SaleService saleService;
	@Autowired
	private ClientService clientService;

	
	@GetMapping(value = "/sale/{id}")
	public ResponseEntity<Object> findByClient(@PathVariable("id") final Long id){
		final Optional<Client> client = clientService.findById(id);
		if(!client.isPresent())
			return new ResponseEntity<Object>(new ArrayList<Sale>(), HttpStatus.OK);		
		final Set<Sale> sales = saleService.findByClient(client.get());
		return new ResponseEntity<Object>(sales, HttpStatus.OK);
	}
	
	@PutMapping(value = "/sale")
	public ResponseEntity<Object> confirm(@RequestBody final PaymentConfirmation confirm) {
		final Report report = saleService.confirmation(confirm);		
		return new ResponseEntity<Object>(report, HttpStatus.OK);		
	}
	
	@PostMapping(value = "/sale")
	public ResponseEntity<Object> save(@RequestBody final AT_Notification _sale) {
		final SaleReport report = saleService.convertNotification(_sale);		
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	@PostMapping(value = "/sale/find")
	public ResponseEntity<Object> findBtwnDates(@RequestBody final PurchaseRequest request){
		final List<Sale> payments = saleService.findBtwnDates(request.getFrom(), request.getTo(), request.getId());
		return new ResponseEntity<Object>(payments, HttpStatus.OK);
	}
}
