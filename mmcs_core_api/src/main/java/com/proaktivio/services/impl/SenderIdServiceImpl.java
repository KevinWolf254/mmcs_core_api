package com.proaktivio.services.impl;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.enums.ProductType;
import com.proaktivio.models.Client;
import com.proaktivio.models.SenderId;
import com.proaktivio.pojo.PaymentConfirmation;
import com.proaktivio.pojo.PaymentType;
import com.proaktivio.pojo.Report;
import com.proaktivio.pojo.SenderIdRequest;
import com.proaktivio.repositories.SenderIdRepository;
import com.proaktivio.services.SaleService;
import com.proaktivio.services.SenderIdService;

@Service
public class SenderIdServiceImpl implements SenderIdService {
	@Autowired
	private SenderIdRepository repository;
	@Autowired
	private SaleService saleService;
	
	private static final Logger log = LoggerFactory.getLogger(SenderIdServiceImpl.class);		

	@Override
	public Optional<SenderId> findByName(final String name) {
		return repository.findByName(name);
	}
	
	@Override
	public SenderId save(final SenderId name) {
		SenderId senderId = null;
		try {
			senderId = repository.save(name);
		} catch (Exception e) {
			log.info("Exception: "+e.getMessage());
			return new SenderId();
		}
		return senderId;
	}
	
	@Override
	public Set<SenderId> findByClient(final Client client) {
		return repository.findByClient(client);
	}
	
	@Override
	public Set<SenderId> findByClientId(final Long id) {
		return repository.findByClientId(id);
	}

	@Override
	public Set<SenderId> findByClientName(final String name) {
		return repository.findByClientName(name);
	}
	
	@Override
	public Boolean exists(final String name) {
		final Optional<SenderId> shortCode = findByName(name);
		if(!shortCode.isPresent())
			return false;
		return true;
	}

	@Override
	public Report process(final SenderIdRequest request) {
		return create(request);
	}

	private Report create(final SenderIdRequest request) {
		final PaymentConfirmation confirmation = new PaymentConfirmation(request.getProduct(), 
				ProductType.SENDER_ID, PaymentType.MPESAC2B, request.getEmail(), request.getCurrency(), request.getAmount(), 
				request.getTransNo(), request.getSenderId());
		final Report report = saleService.confirmation(confirmation);
		return report;
	}
}
