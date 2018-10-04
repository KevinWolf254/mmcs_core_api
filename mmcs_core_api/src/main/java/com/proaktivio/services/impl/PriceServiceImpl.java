package com.proaktivio.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Charge;
import com.proaktivio.models.Price;
import com.proaktivio.models.Product;
import com.proaktivio.repositories.PriceRepository;
import com.proaktivio.services.PriceService;

@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	private PriceRepository repository;
	
	@Override
	public Set<Price> findByProduct(Product product) {
		return repository.findByProduct(product);
	}
	
	@Override
	public Price find(Charge charge, Product product) {
		return repository.find(charge, product);
	}

}
