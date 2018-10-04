package com.proaktivio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>, ExchangeRateRepositoryCustom{


}
