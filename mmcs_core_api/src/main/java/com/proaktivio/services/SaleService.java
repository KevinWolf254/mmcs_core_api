package com.proaktivio.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.proaktivio.models.Client;
import com.proaktivio.models.Sale;
import com.proaktivio.pojo.Report;
import com.proaktivio.pojo.SaleReport;
import com.proaktivio.pojo._Payment;
import com.proaktivio.pojo._Sale;

/**
 * 
 * @author kevin
 * @version 1.0.0
 * @since 2018
 */
public interface SaleService {
	
	/**
	 * saves a Sale 
	 * @param sale
	 * @return Sale
	 */
	public Sale save(Sale sale);

	/**
	 * receives a sale notification from Africa's talking
	 * API and formats it to the Sale object
	 * @param sale
	 * @return
	 */
	public SaleReport saveMpesa(_Sale sale);

	/**
	 * confirms that a sale has been made.
	 * it return optional to check if the 
	 * sale was made or not
	 * @param code
	 * @return Optional<Payment> 
	 */
	public Optional<Sale> findByCode(String code);
	
	/**
	 * finds a Sale made to a particular client
	 * @param client
	 * @return List<Sale> 
	 */
	public Set<Sale> findByClient(Client client); 
	
	/**
	 * finds a Sale by matching its payment details
	 * @param payment
	 * @return Sale
	 */
//	public Sale findByPayment(Payment payment);
	
	/**
	 * finds Sales made in a particular day
	 * @param date
	 * @return List<Sale> 
	 */
	public Set<Sale> findByDate(Date date);
	
	/**
	 * finds Sales made between a particular period
	 * @param from
	 * @param to
	 * @return List<Sale> 
	 */
	public List<Sale> findBtwnDates(Date from, Date to);
	
	/**
	 * finds Sales made to a particular client between a 
	 * particular period
	 * @param from
	 * @param to
	 * @param id
	 * @return List<Sale> 
	 */
	public List<Sale> findBtwnDates(Date from, Date to, Long id);
	
	/**
	 * finds Sales of a particular client that their payment
	 * have been either confirmed-Boolean.TRUE or 
	 * not-Boolean.FALSE
	 * @param confirm
	 * @param id
	 * @return
	 */
	public Set<Sale> findByCreditDisbursed(boolean confirm, Long id);
	
	/**
	 * calculates all unconfirmed payments made by the client
	 * @param client
	 * @return PendingReport
	 */
//	public PendingDisbursementReport findUnDisbursedPayments(final Client client);

	/**
	 * confirms if a Sale was made by Client
	 * it also checks if the amount specified is the
	 * correct amount
	 * @param email
	 * @param code
	 * @param amount
	 * @return boolean
	 */
	public boolean isValid(final Sale sale, final String code, final double amount);

	public Report commitPayment(_Payment confirm);
}
