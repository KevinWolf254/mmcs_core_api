package com.proaktivio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Country;
import com.proaktivio.models.Inventory;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	public Inventory findByCountry(Country country);

}
