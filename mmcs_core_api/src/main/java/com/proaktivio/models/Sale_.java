package com.proaktivio.models;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.proaktivio.enums.SaleType;

@StaticMetamodel(Sale.class)
public abstract class Sale_ {

	public static volatile SingularAttribute<Sale, Long> id;	
	public static volatile SingularAttribute<Sale, String> invoiceNo;
	public static volatile SingularAttribute<Sale, String> code;	
	public static volatile SingularAttribute<Sale, SaleType> type;		
	public static volatile SingularAttribute<Sale, Boolean> successful;	
	public static volatile SingularAttribute<Sale, String> amountInfo;
	public static volatile SingularAttribute<Sale, Date> date;	
	public static volatile SingularAttribute<Sale, Product> product;
	public static volatile SingularAttribute<Sale, Client> client;
}
