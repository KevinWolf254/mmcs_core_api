package com.proaktivio.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.proaktivio.models.ClientUser;
import com.proaktivio.pojo.EmailMessage;
import com.proaktivio.pojo.ClientUserRegEvent;
import com.proaktivio.services.EmailService;

@Service
public class ClientUserRegListener implements ApplicationListener<ClientUserRegEvent> {
  
	@Autowired
	private EmailService emailService;
	
    @Override
    public void onApplicationEvent(ClientUserRegEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(ClientUserRegEvent event) {
        final ClientUser user = event.getUser();

        String subject = "Account Created";
        final StringBuilder builder = new StringBuilder();
		builder
				.append("Credentials: ")
				.append("Username: ")
				.append(user.getEmail())
				.append(" password: ")
				.append(event.getRawPassword());
		String message =  builder.toString();
         
		emailService.sendEmail(new EmailMessage(user.getEmail(), subject, message));
    }

}
