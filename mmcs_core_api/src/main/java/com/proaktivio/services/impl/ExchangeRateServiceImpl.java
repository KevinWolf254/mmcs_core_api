package com.proaktivio.services.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Country;
import com.proaktivio.models.ExchangeRate;
import com.proaktivio.repositories.ExchangeRateRepository;
import com.proaktivio.services.ExchangeRateService;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

	@Autowired
	private ExchangeRateRepository repository;
	private static final Logger log = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
	
	@Override
	public ExchangeRate save(final ExchangeRate eRate) {
		ExchangeRate rate = null;
		try {
			rate = repository.save(eRate);
		} catch (Exception e) {
			log.info("Exception: "+e.getMessage());
		}
		return rate;
	}

	@Override
	public ExchangeRate find(final Country from, final Country to) {
		return repository.find(from, to);
	}

	@Override
	public BigDecimal exchange(final Country from, final Country to, final double amount) {
		return repository.exchange(from, to, amount);
	}
}
