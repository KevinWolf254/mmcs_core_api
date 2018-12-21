package com.proaktivio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
	public Optional<Client> findByName(String name);
	
	public Optional<Client> findByUsersEmail(String email);

	public Optional<Client> findByCustomerId(String customerId);
}
