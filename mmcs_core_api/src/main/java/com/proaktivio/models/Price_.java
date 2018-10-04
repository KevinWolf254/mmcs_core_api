package com.proaktivio.models;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Price.class)
public abstract class Price_ {
	
	public static volatile SingularAttribute<Price, Long> id;		
	public static volatile SingularAttribute<Price, Country> currency;	
	public static volatile SingularAttribute<Price, Double> tax;
	public static volatile SingularAttribute<Price, Double> margin;
	public static volatile SingularAttribute<Price, Double> amount;
	public static volatile SingularAttribute<Price, Double> total;	
	public static volatile SingularAttribute<Price, Charge> charge;	
	public static volatile SingularAttribute<Price, InternationalPrice> internationalPrice;	
	public static volatile SingularAttribute<Price, Product> product;		
}
