package com.proaktivio.repositories;

import java.util.List;

import com.proaktivio.models.Country;
import com.proaktivio.models.Product;

public interface ProductRepositoryCustom{
	
	/**
	 * Finds all products that have a name like 
	 * the expression
	 * @param like - name of the product
	 * @return List<Product>
	 */
	public List<Product> findByNameLike(String like);
	
	public Product find(String nameLike, Country clientCountry);
}
