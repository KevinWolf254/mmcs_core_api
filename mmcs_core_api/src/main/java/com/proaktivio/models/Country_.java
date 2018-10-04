package com.proaktivio.models;

import java.util.Set;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Country.class)
public abstract class Country_ {

	public static volatile SingularAttribute<Country, Long> id;
	public static volatile SingularAttribute<Country, String> name;	
	public static volatile SingularAttribute<Country, Integer> code;
	public static volatile SetAttribute<Country, Set<ServiceProvider>> serviceProviders;
	public static volatile SetAttribute<Country, Set<Client>> clients;
	public static volatile SetAttribute<Country, Set<Payment>> payments;
	public static volatile SetAttribute<Country, Set<InternationalPrice>> internationalPrices;
	public static volatile SetAttribute<Country, Set<InternationalCost>> internationalCosts;
}