package com.proaktivio.services.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.enums.ChargeType;
import com.proaktivio.models.Charge;
import com.proaktivio.models.Client;
import com.proaktivio.models.Country;
import com.proaktivio.models.SenderId;
import com.proaktivio.pojo.ChargesReport;
import com.proaktivio.pojo.Sms;
import com.proaktivio.repositories.ChargeRepository;
import com.proaktivio.services.ChargesService;
import com.proaktivio.services.ClientService;
import com.proaktivio.services.CountryService;
import com.proaktivio.services.SenderIdService;
import com.proaktivio.services.SmsService;

@Service
public class ChargesServiceImpl implements ChargesService {

	@Autowired
	private ChargeRepository repository;
	@Autowired
	private ClientService clientService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private SenderIdService senderIdService;

	@Override
	public Charge findByType(ChargeType type) {
		return repository.findByType(type);
	}

	@Override
	public ChargesReport calculate(final Sms sms) {
		final Optional<Client> client = clientService.findByUsersEmail(sms.getSender());
		
		if(!client.isPresent())
			return new ChargesReport(400, "failed", "user does not exist");
		final Country country = countryService.findByName(client.get().getCountry().getName());		
		final Charge charge = findByType(ChargeType.BASIC);
		
		//get senderId
		final Optional<SenderId> _senderId = senderIdService.findByName(sms.getSenderId());
		final String senderIdName;
		Set<Country> raised_countries = new HashSet<Country>();
		
		if(!_senderId.isPresent())
			senderIdName = "";
		else {
			senderIdName = _senderId.get().getName();
			raised_countries = _senderId.get().getCountries();	
		}		
		final BigDecimal estimatedCost = smsService.estimateCost(country, raised_countries, 
				senderIdName, sms.getPhoneNosTotals(), sms.getMessage(), charge);
		return new ChargesReport(200, "success", "charges were calculated", country, estimatedCost.doubleValue(), 0);
	}

}
