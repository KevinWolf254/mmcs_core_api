package com.proaktivio.services;

import javax.activation.DataSource;

import com.proaktivio.pojo.EmailMessage;

/**
 * 
 * @author kevin
 * @version 1.0.0
 * @since 2018
 */
public interface EmailService {

	/**
	 * sends email
	 * @param email
	 */
	public void sendEmail(EmailMessage email);
	
	/**
	 * sends an email with an attachment 
	 * @param email
	 * @param attachment
	 */
	public void sendEmail(EmailMessage email, DataSource attachment);
}
