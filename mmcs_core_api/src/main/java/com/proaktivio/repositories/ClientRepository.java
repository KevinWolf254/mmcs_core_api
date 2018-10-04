package com.proaktivio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
	public Client findByName(String name);
	
	public Client findByUsersEmail(String email);
}
