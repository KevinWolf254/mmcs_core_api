package com.proaktivio.services;

import java.util.Set;

import com.proaktivio.models.Client;
import com.proaktivio.models.CostOfSale;
import com.proaktivio.models.Product;

public interface CostOfSaleService {

	/**
	 * 
	 * @param costOfSale
	 * @return
	 */
	public CostOfSale save(CostOfSale costOfSale);
	
	/**
	 * 
	 * @param client
	 * @return
	 */
	public Set<CostOfSale> findByClient(Client client);

	/**
	 * 
	 * @param number
	 * @return
	 */
	public Set<CostOfSale> findByNumber(String number);
	
	/**
	 * 
	 * @param product
	 * @return
	 */
	public Set<CostOfSale> findByProduct(Product product);
}
