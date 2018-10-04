package com.proaktivio.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.enums.ChargeType;
import com.proaktivio.models.Charge;
import com.proaktivio.repositories.ChargeRepository;
import com.proaktivio.services.ChargeService;

@Service
public class ChargeServiceImpl implements ChargeService {

	@Autowired
	private ChargeRepository repository;
	
	@Override
	public Charge findByType(ChargeType type) {
		return repository.findByType(type);
	}

}
