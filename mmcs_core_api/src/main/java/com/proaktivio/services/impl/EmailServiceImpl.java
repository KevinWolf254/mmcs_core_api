package com.proaktivio.services.impl;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.proaktivio.pojo.EmailMessage;
import com.proaktivio.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
    private JavaMailSender mailSender;
	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Async
	@Override
	public void sendEmail(final EmailMessage email) {
		try {
			final SimpleMailMessage activationEmail = new SimpleMailMessage();
	        activationEmail.setTo(email.getTo_address());
	        activationEmail.setSubject(email.getSubject());
	        activationEmail.setText(email.getBody());
	        mailSender.send(activationEmail);
		} catch (MailException e) {
			log.info("MailException: "+e.getMessage());
		}catch (Exception e) {
			log.info("Exception: "+e.getMessage());
		}
	}
	
	@Override
	public void sendEmail(final EmailMessage email, final DataSource attachment) {
		final MimeMessage message = mailSender.createMimeMessage();
		try {
			final MimeMessageHelper helper = new MimeMessageHelper(message, Boolean.TRUE, CharEncoding.UTF_8);
			helper.setTo(email.getTo_address());
			helper.setSubject(email.getSubject());
			helper.setText(email.getBody(), false);
			helper.addAttachment("invoice.pdf", attachment);
	        mailSender.send(message);
		} catch (MessagingException e) {
			log.info("MessagingException: "+e.getMessage());
		}catch (Exception e) {
			log.info("Exception: "+e.getMessage());
		}
	}
}
