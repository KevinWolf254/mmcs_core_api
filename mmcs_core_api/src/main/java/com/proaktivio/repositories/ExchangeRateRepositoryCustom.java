package com.proaktivio.repositories;

import java.math.BigDecimal;

import com.proaktivio.models.Country;
import com.proaktivio.models.ExchangeRate;

public interface ExchangeRateRepositoryCustom {

	public ExchangeRate find(Country from, Country to);

	public BigDecimal exchange(Country from, Country to, double amount);
}
