package com.proaktivio.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.proaktivio.models.ClientUser;
import com.proaktivio.pojo.UserReport;
import com.proaktivio.pojo.Credit;
import com.proaktivio.pojo.Report;
import com.proaktivio.pojo.ClientUserRegEvent;
import com.proaktivio.services.ClientUserService;

@RestController
public class ClientUserController {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private ClientUserService userService;

	@PostMapping(value = "/user")
	public ResponseEntity<Object> signIn(
						@RequestParam("email") final String email){		
		final Credit report = userService.signIn(email);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	
	@PostMapping(value = "/user/{id}")
	public ResponseEntity<Object> register(@PathVariable("id") Long id, 						
	@RequestParam("email") String email, @RequestParam("password") String rawPassword, 
	WebRequest request){
		final UserReport report = userService.register(id, email);
		if(report.getCode() != 400)
			eventPublisher.publishEvent(new	ClientUserRegEvent(report.getUser(), request.getLocale(), 
					rawPassword));		
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/user/{email}")
	public ResponseEntity<Object> delete(
						@PathVariable("email") String email){

			final Optional<ClientUser> admin = userService.findByEmail(email);
			if(!admin.isPresent())
				return new ResponseEntity<Object>(new Report(400, "failed", "could not delete user"), 
						HttpStatus.OK);
			
			userService.delete(admin.get());
		return new ResponseEntity<Object>(new Report(200, "success", "deleted successfully"), 
				HttpStatus.OK);
	}
}
