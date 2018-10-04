package com.proaktivio.services;

import java.util.Optional;

import com.proaktivio.models.Client;
import com.proaktivio.models.CreditDisbursment;
import com.proaktivio.models.Sale;
import com.proaktivio.pojo.Disbursement;

public interface CreditDisbursementService {
	/**
	 * 
	 * @param sale
	 * @return CreditDisbursment
	 */
	public Optional<CreditDisbursment> findBySale(Sale sale);

	/**
	 * 
	 * @param creditDisbursment
	 * @return CreditDisbursment
	 */
	public CreditDisbursment save(CreditDisbursment creditDisbursment);

	/**
	 * finds all credit disbursements for a particular client 
	 * with its pending attribute matching the pendingStatus 
	 * 
	 * @param client
	 * @param pendingStatus
	 * @return DisbursementReport
	 */
	public Disbursement findByClient(Client client, boolean pendingStatus);
}
