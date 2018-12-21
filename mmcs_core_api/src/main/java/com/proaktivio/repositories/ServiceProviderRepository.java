package com.proaktivio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.ServiceProvider;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

	public Optional<ServiceProvider> findByName(String name);
}
