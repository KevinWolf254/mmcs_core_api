package com.proaktivio.models;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ServiceProvider.class)
public abstract class ServiceProvider_ {

	public static volatile SingularAttribute<ServiceProvider, Long> id;
	public static volatile SingularAttribute<ServiceProvider, String> name;
	public static volatile SingularAttribute<ServiceProvider, Product> product;
}
