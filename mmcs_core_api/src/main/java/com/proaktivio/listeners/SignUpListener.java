package com.proaktivio.listeners;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.proaktivio.configurations.ProaktivioProperties;
import com.proaktivio.models.Client;
import com.proaktivio.models.ClientUser;
import com.proaktivio.pojo.EmailMessage;
import com.proaktivio.pojo.SignUpEvent;
import com.proaktivio.services.ClientUserService;
import com.proaktivio.services.EmailService;
import com.proaktivio.services.TokenService;

@Service
public class SignUpListener implements ApplicationListener<SignUpEvent> {

	@Autowired
    private TokenService tokenService;  
	@Autowired
	private ClientUserService adminService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ProaktivioProperties properties;
    @Autowired
    private MessageSource messages;
	
    @Override
    public void onApplicationEvent(final SignUpEvent event) {
        this.confirmRegistration(event);
    }
 
	private void confirmRegistration(final SignUpEvent event) {
        final Client client = event.getClient();

        final String token = UUID.randomUUID().toString();
        
        //save token
        tokenService.createToken(client, token);

        //on registration only one administrator will be present in the database
		final Set<ClientUser> admins = adminService.findByClientId(client.getId());
		final String adminEmail = admins.stream().collect(Collectors.toList()).get(0).getEmail();

        final String subject = "Account Activation";
        final StringBuilder builder = new StringBuilder();
		builder.append(messages.getMessage("message.regSucc", null, event.getLocale()))
				.append(" ")
				.append(properties.getHostAddress())
				.append(event.getAppUrl())
				.append("/client/enable?token=")
				.append(token);
		String message =  builder.toString();
         
		emailService.sendEmail(new EmailMessage(adminEmail, subject, message));
    }
}
