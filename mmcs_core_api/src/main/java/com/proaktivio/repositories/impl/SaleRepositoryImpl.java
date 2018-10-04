package com.proaktivio.repositories.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.proaktivio.models.Client;
import com.proaktivio.models.Client_;
import com.proaktivio.models.Sale;
import com.proaktivio.models.Sale_;
import com.proaktivio.repositories.SaleRepositoryCustom;


public class SaleRepositoryImpl implements SaleRepositoryCustom {

	@PersistenceUnit
	private EntityManagerFactory factory;

	@Override
	public List<Sale> findBtwnDates(Date from, Date to) {
		final EntityManager manager = factory.createEntityManager();
		List<Sale> sales = new ArrayList<Sale>();
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
			
			Root<Sale> requestRoot = query.from(Sale.class);			
			Path<Date> date = requestRoot.get(Sale_.date);
			
			query = query.select(requestRoot).distinct(true)
					.where(builder.between(date, from, to));
			
			sales = manager.createQuery(query).getResultList();
		} catch(NoResultException nre){
			return null;
		}finally {
			manager.close();
		}
		return sales;
	}
	
	@Override
	public List<Sale> findBtwnDates(Date from, Date to, Long id) {
		final EntityManager manager = factory.createEntityManager();
		List<Sale> sales = new ArrayList<Sale>();
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
			
			Root<Sale> requestRoot = query.from(Sale.class);			
			Path<Date> date = requestRoot.get(Sale_.date);

			Join<Sale, Client> join = requestRoot.join(Sale_.client);
			
			query = query.select(requestRoot).distinct(true)
					.where(builder.and(builder.between(date, from, to), 
							builder.equal(join.get(Client_.id), id)));
			
			sales = manager.createQuery(query).getResultList();
		} catch(NoResultException nre){
			return null;
		}finally {
			manager.close();
		}
		return sales;
	}
}
