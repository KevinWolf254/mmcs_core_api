package com.proaktivio.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Client;
import com.proaktivio.models.SenderId;


public interface SenderIdRepository extends JpaRepository<SenderId, Long> {

	public Optional<SenderId> findByName(String name);
	
	public Set<SenderId> findByClientId(Long id);
	
	public Set<SenderId> findByClient(Client client);

	public Set<SenderId> findByClientName(String name);
}
