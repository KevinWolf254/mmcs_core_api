package com.proaktivio.services;

import java.util.Optional;

import com.proaktivio.models.Payment;
import com.proaktivio.models.Sale;

public interface PaymentService {

	/**
	 * 
	 * @param payment
	 * @return
	 */
	public Payment save(Payment payment);

	/**
	 * 
	 * @param sale
	 * @return
	 */
	public Optional<Payment> findBySale(Sale sale);

}
