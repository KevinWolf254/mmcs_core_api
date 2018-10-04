package com.proaktivio.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Client;
import com.proaktivio.models.ClientUser;

public interface ClientUserRepository extends JpaRepository<ClientUser, Long> {

	public Optional<ClientUser>  findByEmail(String email);
	
	public Set<ClientUser> findByClient(Client client);

	public Set<ClientUser> findByClientId(Long id);
}
