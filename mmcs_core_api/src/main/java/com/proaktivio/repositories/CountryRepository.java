package com.proaktivio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

	public Country findByName(String name);

	public Country findByCurrency(String currency);

	public Country findByClientsId(Long id);
}
