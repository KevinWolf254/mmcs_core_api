package com.proaktivio.services;

import java.math.BigDecimal;

import com.proaktivio.models.Country;
import com.proaktivio.models.Inventory;
import com.proaktivio.pojo.InventoryReport;

public interface InventoryService {
	
	/**
	 * amount is in the currency of the payment which the client 
	 * paid it with. It's then converted to the currency of the Client organization,
	 * finds the matching inventory and adds the amount.
	 * @param id
	 * @param amount
	 * @param payment_currency
	 * @param client_currency
	 * @return InventoryReport
	 */
	public InventoryReport add(Long id, double amount, String payment_currency, String client_currency);

	public Inventory findById(Long id);

	public Inventory findByCountry(Country country);
	
	public Inventory subtract(Country country, BigDecimal cost);

	public Inventory save(Inventory inventory);

}
