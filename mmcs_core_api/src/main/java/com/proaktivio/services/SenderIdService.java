package com.proaktivio.services;

import java.util.Optional;
import java.util.Set;

import com.proaktivio.models.Client;
import com.proaktivio.models.SenderId;
import com.proaktivio.pojo.Report;
import com.proaktivio.pojo.SenderIdRequest;

public interface SenderIdService {
	/**
	 * finds a SenderId by its name
	 * @param name
	 * @return Optional<SenderId>
	 */
	public Optional<SenderId> findByName(String name);
	
	/**
	 * finds a SenderId by its associated 
	 * client Object
	 * @param client
	 * @return SenderId
	 */
	public Set<SenderId> findByClient(Client client);
	
	/**
	 * finds a Set of SenderIds by their associated
	 * client's id 
	 * @param id
	 * @return SenderId
	 */
	public Set<SenderId> findByClientId(Long id);
	
	/**
	 * finds a SenderId by its associated
	 * client's name
	 * @param name
	 * @return SenderId
	 */
	public Set<SenderId> findByClientName(String name);

	/**
	 * checks if a SenderId name already 
	 * exists or not
	 * @param name
	 * @return Boolean
	 */
	public Boolean exists(String name);
	
	/**
	 * saves a SenderId Object
	 * @param SenderId
	 * @return
	 */
	public SenderId save(SenderId name);

	/**
	 * determines if request is for new sender id or 
	 * updating sender id and processes it accordingly
	 * @param request
	 * @return
	 */
	public Report process(SenderIdRequest request);
}
