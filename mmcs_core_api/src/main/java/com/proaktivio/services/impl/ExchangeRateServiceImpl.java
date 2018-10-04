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

//	@Override
//	public ExchangeRate findByCountry(final Country country) {
//		return repository.findByCountry(country);
//	}
	
	@Override
	public ExchangeRate save(final ExchangeRate eRate) {
		final ExchangeRate rate = repository.save(eRate);
		log.info("###### saved: "+rate);
		return rate;
	}

	@Override
	public ExchangeRate find(Country from, Country to) {
		return repository.find(from, to);
	}

	@Override
	public BigDecimal exchange(Country from, Country to, double amount) {
		return repository.exchange(from, to, amount);
	}

//	@Override
//	public BigDecimal convert(final Country from, final Country to, final double amount) {
//		final BigDecimal amount_ = BigDecimal.valueOf(amount);
//		final ExchangeRate rate = repository.findByCountry(from);
//		if(to.getCode() == 250) {
//			amount_.multiply(BigDecimal.valueOf(rate.getRwf()));
//		}else if(to.getCode() == 254) {
//			amount_.multiply(BigDecimal.valueOf(rate.getKes()));
//		}else if(to.getCode() == 255) {
//			amount_.multiply(BigDecimal.valueOf(rate.getTzs()));
//		}else if(to.getCode() == 256) {
//			amount_.multiply(BigDecimal.valueOf(rate.getUgx()));
//		}else if(to.getCode() == 358) {
//			amount_.multiply(BigDecimal.valueOf(rate.getEur()));
//		}else
//			amount_.multiply(BigDecimal.valueOf(rate.getUsd()));
//		return amount_.setScale(2, RoundingMode.HALF_EVEN);
//	}
}
