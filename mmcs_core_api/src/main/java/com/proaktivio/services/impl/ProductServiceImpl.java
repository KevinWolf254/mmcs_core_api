package com.proaktivio.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Product;
import com.proaktivio.models.ServiceProvider;
import com.proaktivio.repositories.ProductRepository;
import com.proaktivio.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Override
	public Product findByName(final String productName) {
		return repository.findByName(productName);
	}
	
	@Override
	public List<Product> findByServiceProviders(final ServiceProvider telecom) {
		return repository.findByServiceProviders(telecom);
	}
	
	@Override
	public List<Product> findByServiceProvidersId(final Long id) {
		return repository.findByServiceProvidersId(id);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return repository.findById(id);
	}

}
