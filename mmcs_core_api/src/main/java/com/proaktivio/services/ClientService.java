package com.proaktivio.services;

import java.util.Optional;

import com.proaktivio.models.Client;
import com.proaktivio.pojo.SignUp;

public interface ClientService {

	/**
	 * sing up a new client
	 * @param client_name
	 * @param country
	 * @param user_email
	 * @param senderId
	 * @param phoneNo
	 * @return SignUpReport
	 */
	public SignUp signUp(String client_name, String country,
			String user_email, String phoneNo);
	
	/**
	 * finds a client by its id
	 * @param id
	 * @return Client
	 */
	public Optional<Client> findById(final Long id);
	
	/**
	 * finds a client by its name
	 * @param name
	 * @return Client
	 */
	public Optional<Client> findByName(final String name);
	
	/**
	 * finds a client by a administrator's email
	 * @param email
	 * @return Client
	 */
	public Optional<Client> findByUsersEmail(String email);
	
	/**
	 * saves a new client
	 * @param client
	 * @return Client
	 */
	public Client save(final Client client);
	
	/**
	 * deletes a client
	 * @param client
	 */
	public void delete(final Client client);

	/**
	 * finds a client by CustomerId
	 * @param customerId
	 * @return
	 */
	public Optional<Client> findByCustomerId(String customerId);
	
	/**
	 * subtracts client credit and also the associated inventory
	 * @param id
	 * @param amount
	 * @return
	 */
//	public ClientReport subtractCredit(Long id, double amount);

}
