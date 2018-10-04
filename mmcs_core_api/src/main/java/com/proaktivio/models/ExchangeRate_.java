package com.proaktivio.models;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ExchangeRate.class)
public abstract class ExchangeRate_ {

	public static volatile SingularAttribute<ExchangeRate, Long> id;
	public static volatile SingularAttribute<ExchangeRate, Country> from;	
	public static volatile SingularAttribute<ExchangeRate, Country> to;
	public static volatile SingularAttribute<ExchangeRate, Double> rate;
}
