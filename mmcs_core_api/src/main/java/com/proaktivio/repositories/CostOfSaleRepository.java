package com.proaktivio.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Client;
import com.proaktivio.models.CostOfSale;
import com.proaktivio.models.Product;

public interface CostOfSaleRepository extends JpaRepository<CostOfSale, Long> {

	public Set<CostOfSale> findByClient(Client client);

	public Set<CostOfSale> findByNumber(String number);
	
	public Set<CostOfSale> findByProduct(Product product);
}
