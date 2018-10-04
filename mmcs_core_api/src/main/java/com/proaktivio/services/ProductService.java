package com.proaktivio.services;

import java.util.List;

import com.proaktivio.models.Country;
import com.proaktivio.models.Product;
import com.proaktivio.models.ServiceProvider;

public interface ProductService {

	/**
	 * 
	 * @param productName
	 * @return Product
	 */
	public Product findByName(String productName);

	/**
	 * Finds all products that have a name like 
	 * the expression
	 * @param like - name of the product
	 * @return List<Product>
	 */
	public List<Product> findByNameLike(String like);

	public Product findByServiceProviders(ServiceProvider telecom);

	public Product find(String nameLike, Country clientCountry);
}
