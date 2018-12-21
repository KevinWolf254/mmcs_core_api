package com.proaktivio.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Country;
import com.proaktivio.repositories.CountryRepository;
import com.proaktivio.services.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository repository;
	
	@Override
	public Country findByName(final String name) {
		return repository.findByName(name);
	}

	@Override
	public List<Country> findAll() {
		return repository.findAll();
	}

	@Override
	public Country findByCurrency(final String currency) {
		return repository.findByCurrency(currency);
	}

	@Override
	public Country findByClientsId(final Long id) {
		return repository.findByClientsId(id);
	}
}
