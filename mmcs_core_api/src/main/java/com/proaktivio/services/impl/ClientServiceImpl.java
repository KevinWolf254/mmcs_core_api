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

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Autowired
	private CountryService countryService;
	@Autowired
	private ClientUserService userService;
	private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Override
	public SignUp signUp(String client_name, String country, 
			String user_email, String phoneNo) {
		
		final Country country_ = countryService.findByName(country);
		
		final Client client = save(new Client(client_name, country_));
				
		final ClientUser user = userService.save(new ClientUser(user_email, phoneNo, client));
		
		return new SignUp(200, "success","created organization successfully", 
				client, user);
	}
	
	@Override 
	public Optional<Client> findById(Long org_id){
		final Optional<Client> client = repository.findById(org_id);
		return client;
	}

	@Override
	public Client findByName(String organisationName) {
		return repository.findByName(organisationName);
	}
	
	@Override
	public Client findByUsersEmail(String email) {
		return repository.findByUsersEmail(email);
	}
	
	@Override
	public Client save(final Client client) { 	
		final Client client_ = repository.save(client);		
		log.info("###### saved: "+client_);
		return client_;
	}

	@Override
	public void delete(Client organisation) {
		repository.delete(organisation);		
	}
//	@Override
//	public ClientReport subtractCredit(Long id, double amount) {
//
//		final Optional<Client> client = findById(id);
//		if(!client.isPresent())
//			return new ClientReport(400, "failed", "transaction could not be completed", new Client());
//		final Client client_ = client.get();
//		final BigDecimal credit = BigDecimal.valueOf(client_.getCreditAmount());
//		final BigDecimal expense = BigDecimal.valueOf(amount);
//
//		credit.subtract(expense).setScale(2, RoundingMode.HALF_EVEN);
//		client_.setCreditAmount(credit.doubleValue());
//		final Client updated = save(client_);
//		
//		//update inventory associated with the organization
//		inventoryService.subtract(client_.getCountry(), expense);
//		return new ClientReport(200, "success", "transaction successfully completed", updated);
//	}
}
