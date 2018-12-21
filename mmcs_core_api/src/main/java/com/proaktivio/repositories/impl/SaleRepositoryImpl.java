package com.proaktivio.repositories.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proaktivio.models.Client;
import com.proaktivio.models.Client_;
import com.proaktivio.models.Sale;
import com.proaktivio.models.Sale_;
import com.proaktivio.repositories.SaleRepositoryCustom;


public class SaleRepositoryImpl implements SaleRepositoryCustom {

//	@PersistenceUnit
//	private EntityManagerFactory factory;
	@PersistenceContext
	private EntityManager manager;
	private static final Logger log = LoggerFactory.getLogger(SaleRepositoryImpl.class);

	@Override
	public List<Sale> findBtwnDates(final Date from, final Date to) {
//		final EntityManager manager = factory.createEntityManager();
		List<Sale> sales = new ArrayList<Sale>();
		try {
			final CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
			
			final Root<Sale> requestRoot = query.from(Sale.class);			
			final Path<Date> date = requestRoot.get(Sale_.date);
			
			query = query.select(requestRoot).distinct(true)
					.where(builder.between(date, from, to));
			
			sales = manager.createQuery(query).getResultList();
		} catch(NoResultException nre){
			log.info("NoResultException: "+nre.getMessage());
			return sales;
		}catch(Exception e){
			log.info("Exception: "+e.getMessage());
			return sales;
		}
		return sales;
	}
	
	@Override
	public List<Sale> findBtwnDates(final Date from, final Date to, final Long id) {
//		final EntityManager manager = factory.createEntityManager();
		List<Sale> sales = new ArrayList<Sale>();
		try {
			final CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
			
			final Root<Sale> requestRoot = query.from(Sale.class);			
			final Path<Date> date = requestRoot.get(Sale_.date);

			final Join<Sale, Client> join = requestRoot.join(Sale_.client);
			
			query = query.select(requestRoot).distinct(true)
					.where(builder.and(builder.between(date, from, to), 
							builder.equal(join.get(Client_.id), id)));
			
			sales = manager.createQuery(query).getResultList();
		}  catch(NoResultException nre){
			log.info("NoResultException: "+nre.getMessage());
			return sales;
		}catch(Exception e){
			log.info("Exception: "+e.getMessage());
			return sales;
		}
		return sales;
	}
}
