package com.proaktivio.controllers;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proaktivio.models.Client;
import com.proaktivio.models.Sale;
import com.proaktivio.pojo.Report;
import com.proaktivio.pojo.SaleReport;
import com.proaktivio.pojo._Payment;
import com.proaktivio.pojo._Sale;
import com.proaktivio.services.ClientService;
import com.proaktivio.services.SaleService;

@RestController
public class SaleController {
	@Autowired
	private SaleService saleService;
	@Autowired
	private ClientService clientService;
	
	@PostMapping(value = "/sale")
	public ResponseEntity<Object> save(@RequestBody _Sale _sale) {
		
		final SaleReport report = saleService.saveMpesa(_sale);		
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/sale/{id}")
	public ResponseEntity<Object> findByClient(@PathVariable("id") Long id){

		final Optional<Client> client = clientService.findById(id);
		if(!client.isPresent())
			return new ResponseEntity<Object>(new ArrayList<Sale>(), HttpStatus.OK);		
		final Set<Sale> sales = saleService.findByClient(client.get());
		return new ResponseEntity<Object>(sales, HttpStatus.OK);
	}
	
	@PostMapping(value = "/sale/find")
	public ResponseEntity<Object> findBtwnDates(@RequestParam("id") Long id,
		@RequestParam("from") final Date from, @RequestParam("to") final Date to){
		
		final List<Sale> payments = saleService.findBtwnDates(from, to, id);
		return new ResponseEntity<Object>(payments, HttpStatus.OK);
	}
	
	@PutMapping(value = "/sale")
	public ResponseEntity<Object> confirm(@RequestBody final _Payment confirm) {

		final Report report = saleService.commitPayment(confirm);		
		return new ResponseEntity<Object>(report, HttpStatus.OK);		
	}
}
