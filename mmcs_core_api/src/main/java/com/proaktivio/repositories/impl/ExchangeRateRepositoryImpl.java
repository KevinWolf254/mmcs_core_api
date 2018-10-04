package com.proaktivio.repositories.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.proaktivio.models.Country;
import com.proaktivio.models.ExchangeRate;
import com.proaktivio.models.ExchangeRate_;
import com.proaktivio.repositories.ExchangeRateRepositoryCustom;

public class ExchangeRateRepositoryImpl implements ExchangeRateRepositoryCustom {

	@PersistenceUnit
	private EntityManagerFactory factory;
	
	@Override
	public ExchangeRate find(Country from, Country to) {		
		final EntityManager manager = factory.createEntityManager();
		ExchangeRate result = null;
		try {
			final CriteriaBuilder builder = manager.getCriteriaBuilder();
			final CriteriaQuery<ExchangeRate> query = builder.createQuery(ExchangeRate.class);
			final Root<ExchangeRate> rate = query.from(ExchangeRate.class);
			query.where(builder.equal(rate
							.get(ExchangeRate_.from), from),
					(builder.and(builder.equal(rate
							.get(ExchangeRate_.to), to))));
			
			result = manager.createQuery(query).getSingleResult();
			
		} catch(NoResultException nre){
			return new ExchangeRate();
		}finally {
			manager.close();
		}
		return result;
	}

	@Override
	public BigDecimal exchange(Country from, Country to, double amount) {
		final ExchangeRate rate = find(from, to);
		final BigDecimal result =  BigDecimal.valueOf(amount);
		final BigDecimal rate_ =  BigDecimal.valueOf(rate.getRate());
		return result.multiply(rate_).setScale(2, RoundingMode.HALF_EVEN);		
	}
}
