package com.proaktivio.repositories.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proaktivio.models.Country;
import com.proaktivio.models.ExchangeRate;
import com.proaktivio.models.ExchangeRate_;
import com.proaktivio.repositories.ExchangeRateRepositoryCustom;

public class ExchangeRateRepositoryImpl implements ExchangeRateRepositoryCustom {
	@PersistenceContext
	private EntityManager manager;
	
	private static final Logger log = LoggerFactory.getLogger(ExchangeRateRepositoryImpl.class);

	@Override
	public ExchangeRate find(final Country from, final Country to) {		
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
			log.info("NoResultException: "+nre.getMessage());
			return new ExchangeRate();
		}catch(Exception e){
			log.info("Exception: "+e.getMessage());
			return new ExchangeRate();
		}
		return result;
	}

	@Override
	public BigDecimal exchange(final Country from, final Country to, final double amount) {
		final ExchangeRate rate = find(from, to);
		final double result = amount * rate.getRate();
		return BigDecimal.valueOf(result).setScale(2, RoundingMode.HALF_EVEN);		
	}
}
