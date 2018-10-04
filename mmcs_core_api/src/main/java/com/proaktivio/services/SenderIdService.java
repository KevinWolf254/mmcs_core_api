package com.proaktivio.services;

import java.util.Optional;

import com.proaktivio.models.Client;
import com.proaktivio.models.SenderId;

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
	public SenderId findByClient(Client client);
	
	/**
	 * finds a SenderId by its associated
	 * client's id 
	 * @param id
	 * @return SenderId
	 */
	public SenderId findByClientId(Long id);
	
	/**
	 * finds a SenderId by its associated
	 * client's name
	 * @param name
	 * @return SenderId
	 */
	public SenderId findByClientName(String name);

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
}
