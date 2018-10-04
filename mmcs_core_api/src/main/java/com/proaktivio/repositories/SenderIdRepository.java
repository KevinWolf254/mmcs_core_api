package com.proaktivio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Client;
import com.proaktivio.models.SenderId;


public interface SenderIdRepository extends JpaRepository<SenderId, Long> {

	public Optional<SenderId> findByName(String name);
	
	public SenderId findByClientId(Long id);
	
	public SenderId findByClient(Client client);

	public SenderId findByClientName(String name);
}
