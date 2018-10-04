package com.proaktivio.services.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proaktivio.models.Client;
import com.proaktivio.models.CreditDisbursment;
import com.proaktivio.models.Sale;
import com.proaktivio.pojo.Disbursement;
import com.proaktivio.repositories.CreditDisbursmentRepository;
import com.proaktivio.services.CreditDisbursementService;
import com.proaktivio.services.SaleService;

@Service
public class CreditDisbursmentServiceImpl implements CreditDisbursementService {

	@Autowired
	private CreditDisbursmentRepository repository;
	@Autowired
	private SaleService saleService;
	@Override
	public Optional<CreditDisbursment> findBySale(Sale sale) {
		return repository.findBySale(sale);
	}

	@Override
	public CreditDisbursment save(CreditDisbursment creditDisbursment) {
		final CreditDisbursment saved = repository.save(creditDisbursment);
		return saved;
	}

	@Override
	public Disbursement findByClient(Client client, boolean pendingStatus) {
		final Set<Sale> sales = saleService.findByClient(client);
		final Set<CreditDisbursment> disbursements = sales.stream()
				.map(sale->{
					final Optional<CreditDisbursment> disbursement = findBySale(sale);
					if(disbursement.isPresent()) 
						return disbursement.get();
					return null;
					})
				.filter(dis->dis!=null)
				.filter(dis->dis.isPending()==pendingStatus)
				.collect(Collectors.toSet());
		return new Disbursement(disbursements.size(), disbursements);
	}
}
