package com.proaktivio.services.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.services.ClientService;
import com.proaktivio.services.InvoiceService;
import com.proaktivio.services.SaleService;

@Service
public class GeneratorServiceImpl implements InvoiceService{
	@Autowired
	private SaleService saleService;
	@Autowired
	private ClientService clientService;
	private static final String ALPHA_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBERS = "0123456789";
	private static final String ALPHA_NUMERIC = ALPHA_UPPER + NUMBERS;

	@Override
	public String getCustomerId(final String currency) {
		String customerId = null;
		//goal KE-MT89G7
		do {
			final StringBuilder builder = new StringBuilder()
				.append(currency.substring(0, 2))
				.append("-")
				.append(generate(6));
			customerId = builder.toString();
		}while(clientService.findByCustomerId(customerId).isPresent());
		return customerId;	
	}
	
	@Override
	public String generateInvoiceNo(final String currency) {
		String invoiceNo = null;
		//goal INV-KE-MT89G7
		do {
			final StringBuilder builder = new StringBuilder("INV")
				.append("-")
				.append(currency.substring(0, 2))
				.append("-")
				.append(generate(6));
		invoiceNo = builder.toString();
		}while(saleService.findByInvoiceNo(invoiceNo).isPresent());
		return invoiceNo;
	}

	private String generate(final int length) {
		final Random random = new Random();
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i < length; i++) {
			final int first = random.nextInt(ALPHA_NUMERIC.length());
			final int last = first + 1;
			builder.append(ALPHA_NUMERIC.substring(first, last));
		}
		return builder.toString();
	}
}
