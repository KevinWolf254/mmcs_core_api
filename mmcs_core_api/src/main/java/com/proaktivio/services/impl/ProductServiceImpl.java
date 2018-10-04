package com.proaktivio.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Country;
import com.proaktivio.models.Product;
import com.proaktivio.models.ServiceProvider;
import com.proaktivio.repositories.ProductRepository;
import com.proaktivio.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Override
	public Product findByName(String productName) {
		return repository.findByName(productName);
	}

	@Override
	public List<Product> findByNameLike(String like) {
		return repository.findByNameLike(like);
	}

	@Override
	public Product findByServiceProviders(ServiceProvider telecom) {
		return repository.findByServiceProviders(telecom);
	}

	@Override
	public Product find(String nameLike, Country clientCountry) {
		return repository.find(nameLike, clientCountry);
	}

}
