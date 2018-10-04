package com.proaktivio.models;

import java.util.Set;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.proaktivio.enums.ChargeType;

@StaticMetamodel(Charge.class)
public abstract class Charge_ {

	public static volatile SingularAttribute<Charge, Long> id;		
	public static volatile SingularAttribute<Charge, ChargeType> type;	
	public static volatile SetAttribute<Charge, Set<Cost>> costs;
	public static volatile SetAttribute<Charge, Set<Price>> prices;
	
}
