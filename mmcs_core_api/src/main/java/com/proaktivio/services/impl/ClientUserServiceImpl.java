package com.proaktivio.services.impl;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Client;
import com.proaktivio.models.ClientUser;
import com.proaktivio.pojo.Credit;
import com.proaktivio.pojo.Disbursement;
import com.proaktivio.pojo.UserReport;
import com.proaktivio.repositories.ClientUserRepository;
import com.proaktivio.services.ClientService;
import com.proaktivio.services.ClientUserService;
import com.proaktivio.services.CreditDisbursementService;

@Service
public class ClientUserServiceImpl implements ClientUserService {

	@Autowired
	private ClientUserRepository repository;
	@Autowired
	private CreditDisbursementService disService;
	@Autowired
	private ClientService clientService;
	private static final Logger log = LoggerFactory.getLogger(ClientUserServiceImpl.class);

	@Override
	public Optional<ClientUser> findByEmail(String user_email) {
		return repository.findByEmail(user_email);
	}

	@Override
	public Set<ClientUser> findByClient(Client client) {
		return repository.findByClient(client);
	}

	@Override
	public Set<ClientUser> findByClientId(Long id) {
		return repository.findByClientId(id);
	}

	@Override
	public ClientUser save(ClientUser details) {
		ClientUser user = null;
		try {
			user = repository.save(details);
		} catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new ClientUser();
		}
		return user;
	}

	@Override
	public void delete(ClientUser user) {
		repository.delete(user);		
	}

	@Override
	public Credit signIn(String user_email) {
		final Optional<ClientUser>  user = findByEmail(user_email);
			if(!user.isPresent())
				return new Credit(400, "failed","user does not exist");

			final Client client = clientService.findByUsersEmail(user_email).get();
			final Disbursement disbursement = disService.findByClient(client, Boolean.TRUE);
			return new Credit(200, "success", "successfully signed in", client, disbursement);
	}

	@Override
	public UserReport addUser(Long client_id, String email) {
		final Optional<Client> client  = clientService.findById(client_id);
		if(!client.isPresent())
			return new UserReport(400, "failed", "invalid request", new ClientUser());
		final ClientUser user = save(new ClientUser(email, client.get()));
		return new UserReport(200, "", "successfully created user", user);
	}
}
