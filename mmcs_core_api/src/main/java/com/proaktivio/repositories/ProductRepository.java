package com.proaktivio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Product;
import com.proaktivio.models.ServiceProvider;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

	public Product findByName(String productName);

	public Product findByServiceProviders(ServiceProvider telecom);
}
