package com.proaktivio.controllers;

import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.proaktivio.models.Client;
import com.proaktivio.models.Token;
import com.proaktivio.pojo.SignUpEvent;
import com.proaktivio.pojo.SignUp;
import com.proaktivio.services.ClientService;
import com.proaktivio.services.ClientUserService;
import com.proaktivio.services.TokenService;

@RestController
public class ClientController {
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientUserService userService;
	@Autowired
	private TokenService tokenService;	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	@Autowired
    private MessageSource messages; 
	
	@Value("${mmcs.client.url.signin}")
	private String signInUrl;
	@Value("${mmcs.client.url.register}")
	private String registrationUrl;	
	
	@PostMapping(value = "/client")
	public ResponseEntity<Object> signUp(@RequestParam("client") String name, 
						@RequestParam("country") String country,
						@RequestParam("admin") String email, 
						@RequestParam("phoneNo") String phoneNo, WebRequest request){
		
		final SignUp report = clientService.signUp(name.trim(), country.trim(), email.trim(), phoneNo.trim());
		if(report.getCode() != 400) {
			final String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new SignUpEvent(report.getClient(), request.getLocale(), appUrl));
		}
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	
	@GetMapping(value = "/client/enable")
	public String enable(final WebRequest request, final Model model, 
			@RequestParam("token") final String token) {
	  
	    final Locale locale = request.getLocale();
	    
	    final Token token_ = tokenService.getToken(token);
	    if (token_ == null) {
	        final String message = messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("message", message);	        
	        return "" + message;
	    }
	     
	    final Client client = token_.getClient();
	    final Calendar cal = Calendar.getInstance();
	    if ((token_.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	//delete the token 
	    	tokenService.delete(token_);
	    	//delete the client & user
	    	clientService.delete(client);
	    	userService.delete(client.getUsers().stream().findFirst().get());
	        String messageValue = messages.getMessage("auth.message.expired", null, locale);
	        model.addAttribute("message", messageValue);
	        return "" + messageValue +" " + registrationUrl;
	    } 
	     
	    client.setEnabled(true); 
	    clientService.save(client);
	    tokenService.delete(token_);
	    return "Successfully activated account. Click link to go to sign in: " + signInUrl; 
	}
	
	@GetMapping(value = "/client")
	public ResponseEntity<Object> findById(@RequestParam("id") final Long id){	
		final Optional<Client> client = clientService.findById(id);
		if(!client.isPresent())
			return new ResponseEntity<Object>(new Client(), HttpStatus.OK);
		return new ResponseEntity<Object>(client, HttpStatus.OK);
	}
}
