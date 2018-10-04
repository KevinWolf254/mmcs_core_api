package com.proaktivio.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Client;
import com.proaktivio.models.Country;
import com.proaktivio.models.Inventory;
import com.proaktivio.pojo.InventoryReport;
import com.proaktivio.repositories.InventoryRepository;
import com.proaktivio.services.ClientService;
import com.proaktivio.services.CountryService;
import com.proaktivio.services.ExchangeRateService;
import com.proaktivio.services.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository repository;

	@Autowired
	private ClientService clientService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private ExchangeRateService rateService;
	
	@Override
	public Inventory findById(final Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Inventory findByCountry(final Country country) {
		return repository.findByCountry(country);
	}
	
	@Override
	public Inventory save(final Inventory aeon) {
		final Inventory inventory = repository.save(aeon);			
		log.info("###### Saved: "+inventory);	
		return inventory;
	}
	
	@Override
	public Inventory subtract(final Country country, final BigDecimal cost) {
		final Inventory inventory = repository.findByCountry(country);		
		final BigDecimal amount = BigDecimal.valueOf(inventory.getAmount());
		
		if(amount.compareTo(cost) == 1 || amount.compareTo(cost) == 0)
			amount.subtract(cost).setScale(2, RoundingMode.HALF_EVEN);
		else
			//subtract itself so as to be zero
			amount.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);
		
		inventory.setAmount(amount.doubleValue());
		return save(inventory);		
	}
	private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

	@Override
	public InventoryReport add(Long id, double amount, String payment_currency, String client_currency) {

		final Optional<Client> client = clientService.findById(id);
		if(!client.isPresent())
			return new InventoryReport(400, "invalid request", "could not find client", new Inventory());
		
		final Country client_country = countryService.findByCurrency(client_currency);
		final Country payment_country = countryService.findByCurrency(payment_currency);
		final BigDecimal amount_ = rateService.exchange(payment_country, client_country, amount);
		
		final Inventory inventory = findByCountry(client_country);
		final BigDecimal inventoryAmount = BigDecimal.valueOf(inventory.getAmount());
		inventoryAmount.add(amount_).setScale(2, RoundingMode.HALF_EVEN);
		
		inventory.setAmount(inventoryAmount.doubleValue());
		final Inventory updated = save(inventory);
		return new InventoryReport(200, "success", "successfully updated", updated);
	}
	
}
