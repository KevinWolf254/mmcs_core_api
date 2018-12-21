package com.proaktivio.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.ServiceProvider;
import com.proaktivio.repositories.ServiceProviderRepository;
import com.proaktivio.services.ServiceProviderService;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

	@Autowired
	private ServiceProviderRepository repository;
	
	@Override
	public Optional<ServiceProvider> findByName(final String name) {
		return repository.findByName(name);
	}

}
