package com.proaktivio.repositories;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Client;
import com.proaktivio.models.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long>, SaleRepositoryCustom{

	public Optional<Sale> findByCode(String code);

	public Set<Sale> findByClient(Client client);

	public Set<Sale> findByDate(Date date);

	public Set<Sale> findByClientId(Long id);

}
