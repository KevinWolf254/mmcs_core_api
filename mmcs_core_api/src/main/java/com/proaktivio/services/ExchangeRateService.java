package com.proaktivio.services;

import java.math.BigDecimal;

import com.proaktivio.models.Country;
import com.proaktivio.models.ExchangeRate;

public interface ExchangeRateService {

	public ExchangeRate find(Country from, Country to);
	
	public ExchangeRate save(ExchangeRate newRate);

	public BigDecimal exchange(Country from, Country to, double amount);
}
