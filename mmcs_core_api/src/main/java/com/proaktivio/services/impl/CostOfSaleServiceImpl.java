package com.proaktivio.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Client;
import com.proaktivio.models.CostOfSale;
import com.proaktivio.models.Product;
import com.proaktivio.repositories.CostOfSaleRepository;
import com.proaktivio.services.CostOfSaleService;

@Service
public class CostOfSaleServiceImpl implements CostOfSaleService {

	@Autowired
	private CostOfSaleRepository repository;
	
	@Override
	public CostOfSale save(final CostOfSale costOfSale) {
		final CostOfSale cost = repository.save(costOfSale);
		return cost;
	}
	@Override
	public Set<CostOfSale> findByClient(final Client client) {
		return repository.findByClient(client);
	}
	@Override
	public Set<CostOfSale> findByNumber(final String number) {
		return repository.findByNumber(number);
	}
	@Override
	public Set<CostOfSale> findByProduct(final Product product) {
		return repository.findByProduct(product);
	}

}
