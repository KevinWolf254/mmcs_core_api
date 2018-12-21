package com.proaktivio.services;

public interface InvoiceService {

	public String generateInvoiceNo(String currency);
	
	public String getCustomerId(String currency);
}
