package com.proaktivio.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proaktivio.models.Charge;
import com.proaktivio.models.Charge_;
import com.proaktivio.models.Price;
import com.proaktivio.models.Price_;
import com.proaktivio.models.Product;
import com.proaktivio.models.Product_;
import com.proaktivio.repositories.PriceRepositoryCustom;

public class PriceRepositoryImpl implements PriceRepositoryCustom {
	@PersistenceContext
	private EntityManager manager;
	private static final Logger log = LoggerFactory.getLogger(PriceRepositoryImpl.class);

	@Override
	public Price find(Charge charge, Product product) {
		Price result = null;
		try {
			final CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Price> query = builder.createQuery(Price.class);
			
			final Root<Price> requestRoot = query.from(Price.class);			

			final Join<Price, Charge> joinCharge = requestRoot.join(Price_.charge);

			final Join<Price, Product> joinProduct = requestRoot.join(Price_.product);
			
			query = query.select(requestRoot).distinct(true)
					.where(builder.and(builder.equal(joinProduct.get(Product_.id), product.getId()), 
							builder.equal(joinCharge.get(Charge_.id), charge.getId())));
			
			result = manager.createQuery(query).getSingleResult();
		} catch(NoResultException nre){
			log.info("NoResultException: "+nre.getMessage());
			return new Price();
		}catch(Exception e){
			log.info("Exception: "+e.getMessage());
			return new Price();
		}
		return result;
	}

}
