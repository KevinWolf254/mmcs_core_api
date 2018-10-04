package com.proaktivio.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Payment;
import com.proaktivio.models.Sale;
import com.proaktivio.repositories.PaymentRepository;
import com.proaktivio.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository repository;
	
	@Override
	public Payment save(final Payment payment) {
		final Payment payment_ = repository.save(payment);
		return payment_;
	}
	
	@Override
	public Optional<Payment> findBySale(final Sale sale){
		return repository.findBySale(sale);
	}
}
