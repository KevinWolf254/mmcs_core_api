package com.proaktivio.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Client;
import com.proaktivio.models.SenderId;
import com.proaktivio.repositories.SenderIdRepository;
import com.proaktivio.services.SenderIdService;

@Service
public class SenderIdServiceImpl implements SenderIdService {
	@Autowired
	private SenderIdRepository repository;
	private static final Logger log = LoggerFactory.getLogger(SenderIdServiceImpl.class);		

	@Override
	public Optional<SenderId> findByName(final String name) {
		return repository.findByName(name);
	}
	
	@Override
	public SenderId save(final SenderId shortCode) {
		final SenderId shortCode_ = repository.save(shortCode);
		log.info("##### saved: "+shortCode_);
		return shortCode_;
	}
	
	@Override
	public SenderId findByClient(final Client client) {
		return repository.findByClient(client);
	}
	
	@Override
	public SenderId findByClientId(final Long id) {
		return repository.findByClientId(id);
	}

	@Override
	public SenderId findByClientName(final String name) {
		return repository.findByClientName(name);
	}
	
	@Override
	public Boolean exists(final String name) {
		final Optional<SenderId> shortCode = findByName(name);
		if(!shortCode.isPresent())
			return false;
		return true;
	}
}
