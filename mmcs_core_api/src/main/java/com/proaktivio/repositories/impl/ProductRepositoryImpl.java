package com.proaktivio.repositories.impl;

import java.util.ArrayList;
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

import com.proaktivio.models.Country;
import com.proaktivio.models.Country_;
import com.proaktivio.models.Product;
import com.proaktivio.models.Product_;
import com.proaktivio.repositories.ProductRepositoryCustom;


public class ProductRepositoryImpl implements ProductRepositoryCustom {

	@PersistenceUnit
	private EntityManagerFactory factory;

	@Override
	public List<Product> findByNameLike(String like) {

		final EntityManager manager = factory.createEntityManager();
		List<Product> products = new ArrayList<Product>();
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Product> query = builder.createQuery(Product.class);
			
			Root<Product> requestRoot = query.from(Product.class);			
			Path<String> name = requestRoot.get(Product_.name);
			
			query = query.select(requestRoot).distinct(true)
					.where(builder.like(name, "%"+like+"%"));
			
			products = manager.createQuery(query).getResultList();
		} catch(NoResultException nre){
			return null;
		}finally {
			manager.close();
		}
		return products;
	}

	@Override
	public Product find(String nameLike, Country clientCountry) {
		final EntityManager manager = factory.createEntityManager();
		Product result = null;
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Product> query = builder.createQuery(Product.class);
			
			Root<Product> requestRoot = query.from(Product.class);			
			Path<String> name = requestRoot.get(Product_.name);
			
			Join<Product, Country> join = requestRoot.join(Product_.country);
			
			query = query.select(requestRoot).distinct(true)
					.where(builder.and(builder.like(name, "%"+nameLike+"%")), 
							builder.equal(join.get(Country_.id), clientCountry.getId()));
			
			result = manager.createQuery(query).getSingleResult();
		} catch(NoResultException nre){
			return new Product();
		}finally {
			manager.close();
		}
		return result;
	}

//	@Override
//	public List<Sale> findBtwnDates(Date from, Date to) {
//		final EntityManager manager = factory.createEntityManager();
//		List<Sale> sales = new ArrayList<Sale>();
//		try {
//			CriteriaBuilder builder = manager.getCriteriaBuilder();
//			CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
//			
//			Root<Sale> requestRoot = query.from(Sale.class);			
//			Path<Date> date = requestRoot.get(Sale_.date);
//			
//			query = query.select(requestRoot).distinct(true)
//					.where(builder.between(date, from, to));
//			
//			sales = manager.createQuery(query).getResultList();
//		} catch(NoResultException nre){
//			return null;
//		}finally {
//			manager.close();
//		}
//		return sales;
//	}
//	
//	@Override
//	public List<Sale> findBtwnDates(Date from, Date to, Long id) {
//		final EntityManager manager = factory.createEntityManager();
//		List<Sale> sales = new ArrayList<Sale>();
//		try {
//			CriteriaBuilder builder = manager.getCriteriaBuilder();
//			CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
//			
//			Root<Sale> requestRoot = query.from(Sale.class);			
//			Path<Date> date = requestRoot.get(Sale_.date);
//
//			Join<Sale, Client> join = requestRoot.join(Sale_.client);
//			
//			query = query.select(requestRoot).distinct(true)
//					.where(builder.and(builder.between(date, from, to), 
//							builder.equal(join.get(Client_.id), id)));
//			
//			sales = manager.createQuery(query).getResultList();
//		} catch(NoResultException nre){
//			return null;
//		}finally {
//			manager.close();
//		}
//		return sales;
//	}
}
