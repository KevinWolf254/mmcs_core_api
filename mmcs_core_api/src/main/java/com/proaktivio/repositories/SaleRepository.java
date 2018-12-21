package com.proaktivio.repositories;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.models.Client;
import com.proaktivio.models.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long>, SaleRepositoryCustom{

	/**
	 * finds a Sale by its mpesa transaction id
	 * @param code
	 * @return Optional<Sale>
	 */
	public Optional<Sale> findByCode(String code);
	
	/**
	 * finds a Sale by its invoice number
	 * @param invoiceNo - unique id assigned to all confirmed payments
	 * @return Optional<Sale>
	 */
	Optional<Sale> findByInvoiceNo(String invoiceNo);
	
	/**
	 * finds all Sales made to a particular client
	 * @param client
	 * @return
	 */
	public Set<Sale> findByClient(Client client);

	/**
	 * finds all Sales made in a particular date
	 * @param date
	 * @return
	 */
	public Set<Sale> findByDate(Date date);

	/**
	 * finds all Sales made to a particular client
	 * @param id - the client's id
	 * @return
	 */
	public Set<Sale> findByClientId(Long id);

}
