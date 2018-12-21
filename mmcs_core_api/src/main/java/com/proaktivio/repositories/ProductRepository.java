package com.proaktivio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Product;
import com.proaktivio.models.ServiceProvider;

public interface ProductRepository extends JpaRepository<Product, Long> {

	public Product findByName(String productName);

	public List<Product> findByServiceProviders(ServiceProvider telecom);

	public List<Product> findByServiceProvidersId(Long id);
}
