package com.proaktivio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.CreditDisbursment;
import com.proaktivio.models.Sale;

public interface CreditDisbursmentRepository extends JpaRepository<CreditDisbursment, Long> {

	public Optional<CreditDisbursment> findBySale(Sale sale);

}
