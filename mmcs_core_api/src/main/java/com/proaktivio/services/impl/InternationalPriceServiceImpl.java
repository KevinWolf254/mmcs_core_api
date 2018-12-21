package com.proaktivio.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.InternationalPrice;
import com.proaktivio.repositories.InternationalPriceRepository;
import com.proaktivio.services.InternationalPriceService;

@Service
public class InternationalPriceServiceImpl implements InternationalPriceService {

	@Autowired
	private InternationalPriceRepository repository;
	
	@Override
	public Optional<InternationalPrice> findByPricesId(Long id) {
		return repository.findByPricesId(id);
	}

}
