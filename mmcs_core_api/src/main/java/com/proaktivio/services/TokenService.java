package com.proaktivio.services;

import com.proaktivio.models.Client;
import com.proaktivio.models.Token;

public interface TokenService {

	/**
	 * 
	 * @param client
	 * @param token
	 */
	public void createToken(final Client client, final String token);

	/**
	 * 
	 * @param existingVerificationToken
	 * @return Token
	 */
	public Token recreateToken(String existingVerificationToken);
	
	/**
	 * 
	 * @param VerificationToken
	 * @return Token
	 */
	public Token getToken(final String VerificationToken);

	/**
	 * 
	 * @param verificationToken
	 */
	public void delete(Token verificationToken);

}
