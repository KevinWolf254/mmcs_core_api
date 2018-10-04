package com.proaktivio.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import com.proaktivio.models.Charge;
import com.proaktivio.models.Charge_;
import com.proaktivio.models.Price;
import com.proaktivio.models.Price_;
import com.proaktivio.models.Product;
import com.proaktivio.models.Product_;
import com.proaktivio.repositories.PriceRepositoryCustom;

public class PriceRepositoryImpl implements PriceRepositoryCustom {

	@PersistenceUnit
	private EntityManagerFactory factory;
	
	@Override
	public Price find(Charge charge, Product product) {
		final EntityManager manager = factory.createEntityManager();
		Price result = null;
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Price> query = builder.createQuery(Price.class);
			
			Root<Price> requestRoot = query.from(Price.class);			

			Join<Price, Charge> joinCharge = requestRoot.join(Price_.charge);

			Join<Price, Product> joinProduct = requestRoot.join(Price_.product);
			
			query = query.select(requestRoot).distinct(true)
					.where(builder.and(builder.equal(joinProduct.get(Product_.id), product.getId()), 
							builder.equal(joinCharge.get(Charge_.id), charge.getId())));
			
			result = manager.createQuery(query).getSingleResult();
		} catch(NoResultException nre){
			return null;
		}finally {
			manager.close();
		}
		return result;
	}

}
