package com.proaktivio.services;

import java.util.List;

import com.proaktivio.models.Country;

public interface CountryService {

	/**
	 * 
	 * @param name
	 * @return
	 */
	public Country findByName(String name);

	/**
	 * 
	 * @return List<Country>
	 */
	public List<Country> findAll();

	/**
	 * 
	 * @param currency
	 * @return Country
	 */
	public Country findByCurrency(String currency);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Country findByClientsId(Long id);
}
