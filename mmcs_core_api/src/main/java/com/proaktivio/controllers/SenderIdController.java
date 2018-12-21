package com.proaktivio.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proaktivio.models.SenderId;
import com.proaktivio.pojo.Report;
import com.proaktivio.pojo.SenderIdRequest;
import com.proaktivio.services.SenderIdService;

@RestController
public class SenderIdController {
	@Autowired
	private SenderIdService senderIdService;

	@GetMapping(value = "/senderId/{id}")
	public ResponseEntity<Object> sendSms(@PathVariable("id") final Long id){
		final Set<SenderId> response = senderIdService.findByClientId(id);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	@GetMapping(value = "/senderId/byName/{name}")
	public ResponseEntity<SenderId> findByName(@PathVariable("name") final String name){
		SenderId senderId = null;
		final Optional<SenderId> result = senderIdService.findByName(name);
		if(result.isPresent())
			senderId = result.get();
		else
			senderId = new SenderId();
		return new ResponseEntity<SenderId>(senderId, HttpStatus.OK);
	}
	@PostMapping(value = "/senderId")
	public ResponseEntity<Object> save(@RequestBody final SenderIdRequest request){
		final Report response = senderIdService.process(request);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
//	@PutMapping(value = "/secure/senderId")
//	public ResponseEntity<Object> update(@RequestBody final SenderIdRequest request){
//		final Report response = senderIdService.process(request);
//		return new ResponseEntity<Object>(response, HttpStatus.OK);
//	}
}
