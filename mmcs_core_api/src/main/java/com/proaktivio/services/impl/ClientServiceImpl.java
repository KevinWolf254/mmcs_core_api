package com.proaktivio.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Client;
import com.proaktivio.models.ClientUser;
import com.proaktivio.models.Country;
import com.proaktivio.pojo.SignUp;
import com.proaktivio.repositories.ClientRepository;
import com.proaktivio.services.ClientService;
import com.proaktivio.services.ClientUserService;
import com.proaktivio.services.CountryService;
import com.proaktivio.services.InvoiceService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Autowired
	private CountryService countryService;
	@Autowired
	private InvoiceService generatorService;
	@Autowired
	private ClientUserService userService;
	private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Override
	public SignUp signUp(final String client_name, final String country, 
			final String user_email, final String phoneNo) {
		Client client = null;
		final Country country_ = countryService.findByName(country);
		
		final Optional<Client> client_ = repository.findByName(client_name);

		if(client_.isPresent())
			return new SignUp(400, "failed", "organisation already exists");
		else {
			final String customerId = generatorService.getCustomerId(country_.getCurrency());
			client = save(new Client(customerId, client_name, country_));
		}				
		final ClientUser user = userService.save(new ClientUser(user_email, phoneNo, client));
				
		return new SignUp(200, "success","created organization successfully", 
				client, user);
	}
	@Override 
	public Optional<Client> findByCustomerId(final String customerId){
		final Optional<Client> client = repository.findByCustomerId(customerId);
		return client;
	}
	
	@Override 
	public Optional<Client> findById(final Long org_id){
		final Optional<Client> client = repository.findById(org_id);
		return client;
	}

	@Override
	public Optional<Client> findByName(final String organisationName) {
		return repository.findByName(organisationName);
	}
	
	@Override
	public Optional<Client> findByUsersEmail(final String email) {
		return repository.findByUsersEmail(email);
	}
	
	@Override
	public Client save(final Client details) { 	
		Client client = null;
		try {
			client = repository.save(details);
		} catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new Client();
		}		
		return client;
	}

	@Override
	public void delete(final Client organisation) {
		repository.delete(organisation);		
	}
}
