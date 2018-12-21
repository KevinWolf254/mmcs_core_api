package com.proaktivio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.InternationalPrice;

public interface InternationalPriceRepository extends JpaRepository<InternationalPrice, Long> {

	public Optional<InternationalPrice> findByPricesId(Long id);
}
