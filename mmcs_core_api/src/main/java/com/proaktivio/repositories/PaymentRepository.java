package com.proaktivio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Payment;
import com.proaktivio.models.Sale;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	public Optional<Payment> findBySale(Sale sale);
}
