package com.proaktivio.services;

import java.util.Set;

import com.proaktivio.models.Charge;
import com.proaktivio.models.Price;
import com.proaktivio.models.Product;

public interface PriceService {

	/**
	 * 
	 * @param product
	 * @return
	 */
	public Set<Price> findByProduct(Product product);

	public Price find(Charge charge, Product product);
}
