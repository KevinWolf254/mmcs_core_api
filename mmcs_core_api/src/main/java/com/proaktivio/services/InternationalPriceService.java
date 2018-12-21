package com.proaktivio.services;

import java.util.Optional;

import com.proaktivio.models.InternationalPrice;

public interface InternationalPriceService {

	public Optional<InternationalPrice> findByPricesId(Long id);
}
