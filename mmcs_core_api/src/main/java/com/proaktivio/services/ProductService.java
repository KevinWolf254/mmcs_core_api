package com.proaktivio.services;

import java.util.List;
import java.util.Optional;

import com.proaktivio.models.Product;
import com.proaktivio.models.ServiceProvider;

public interface ProductService {

	/**
	 * 
	 * @param productName
	 * @return Product
	 */
	public Product findByName(String productName);
	
	public List<Product> findByServiceProviders(ServiceProvider telecom);

	public List<Product> findByServiceProvidersId(Long id);
	
	public Optional<Product> findById(Long id);

}
