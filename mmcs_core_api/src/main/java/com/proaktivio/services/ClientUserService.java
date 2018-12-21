package com.proaktivio.services;

import java.util.Optional;
import java.util.Set;

import com.proaktivio.models.Client;
import com.proaktivio.models.ClientUser;
import com.proaktivio.pojo.Credit;
import com.proaktivio.pojo.UserReport;

public interface ClientUserService {

	public Credit signIn(String user_email);
	
	public UserReport addUser(Long client_id, String email);
	
	/**
	 * finds a user by their email
	 * @param user_email
	 * @return Optional<ClientUser>
	 */
	public Optional<ClientUser> findByEmail(String user_email);
	
	/**
	 * finds all users of a certain client 
	 * @param client
	 * @return Set<ClientUser>
	 */
	public Set<ClientUser> findByClient(Client client);

	/**
	 * finds all users  by the associated 
	 * client's id
	 * @param id
	 * @return Set<ClientUser>
	 */
	public Set<ClientUser> findByClientId(Long id);
	
	/**
	 * saves a new user
	 * @param admin
	 * @return ClientUser
	 */
	public ClientUser save(ClientUser user);

	/**
	 * deletes an user
	 * @param user
	 */
	public void delete(ClientUser user);
}
