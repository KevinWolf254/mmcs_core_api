package com.proaktivio.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Client.class)
public abstract class Client_ {

	public static volatile SingularAttribute<Client, Long> id;
	public static volatile SingularAttribute<Client, String> name;	
	public static volatile SingularAttribute<Client, Double> creditAmount;
	public static volatile SingularAttribute<Client, Boolean> enabled;
	public static volatile SingularAttribute<Client, Date> createdOn;
	public static volatile SingularAttribute<Client, Country> country;
	public static volatile SetAttribute<Client, Set<ClientUser>> users;
	public static volatile SetAttribute<Client, Set<SenderId>> senderIds;
	public static volatile SetAttribute<Client, Set<Sale>> sales;
	public static volatile SetAttribute<Client, Set<CostOfSale>> costs;

}