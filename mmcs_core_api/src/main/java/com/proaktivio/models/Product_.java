package com.proaktivio.models;

import java.util.Set;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, Long> id;	
	public static volatile SingularAttribute<Product, String> name;	
	public static volatile SingularAttribute<Product, Country> country;		
	public static volatile SetAttribute<Product, Set<ServiceProvider>> serviceProviders;	
	public static volatile SetAttribute<Product, Set<Sale>> sales;
	public static volatile SetAttribute<Product, Set<CostOfSale>> costsOfSales;	
	public static volatile SetAttribute<Product, Set<Cost>> costs;
	public static volatile SetAttribute<Product, Set<Price>> prices;
}
