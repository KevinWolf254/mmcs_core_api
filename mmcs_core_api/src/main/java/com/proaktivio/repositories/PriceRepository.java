package com.proaktivio.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Price;
import com.proaktivio.models.Product;

public interface PriceRepository extends JpaRepository<Price, Long>, PriceRepositoryCustom {

	public Set<Price> findByProduct(Product product);

}
