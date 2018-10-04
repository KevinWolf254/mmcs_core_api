package com.proaktivio.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.proaktivio.enums.ChargeType;
import com.proaktivio.enums.ProductType;
import com.proaktivio.enums.SaleType;
import com.proaktivio.models.Client;
import com.proaktivio.models.ClientUser;
import com.proaktivio.models.CostOfSale;
import com.proaktivio.models.Country;
import com.proaktivio.models.CreditDisbursment;
import com.proaktivio.models.Inventory;
import com.proaktivio.models.Payment;
import com.proaktivio.models.Product;
import com.proaktivio.models.Sale;
import com.proaktivio.models.SenderId;
import com.proaktivio.pojo.EmailMessage;
import com.proaktivio.pojo.Report;
import com.proaktivio.pojo.SaleReport;
import com.proaktivio.pojo._Payment;
import com.proaktivio.pojo._Sale;
import com.proaktivio.repositories.SaleRepository;
import com.proaktivio.services.ClientService;
import com.proaktivio.services.ClientUserService;
import com.proaktivio.services.CostOfSaleService;
import com.proaktivio.services.CountryService;
import com.proaktivio.services.CreditDisbursementService;
import com.proaktivio.services.EmailService;
import com.proaktivio.services.ExchangeRateService;
import com.proaktivio.services.InventoryService;
import com.proaktivio.services.PaymentService;
import com.proaktivio.services.ProductService;
import com.proaktivio.services.SaleService;
import com.proaktivio.services.SenderIdService;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepository repository;

	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientUserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private CostOfSaleService costService;
	@Autowired
	private CreditDisbursementService disbursementService;
	@Autowired
	private SenderIdService senderIdService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private ExchangeRateService rateService;
	@Autowired
	private CreditDisbursementService disbursmentService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private EmailService emailService;
	private static final Logger log = LoggerFactory.getLogger(SaleServiceImpl.class);

	@Value("${spring.mail.username}")
	private String proactiveEmail;

	@Override
	public Sale save(final Sale sale) {
		final Sale sale_ = repository.save(sale);
		log.info("Saved: "+sale_);
		return sale_;
	}

	@Override
	public Optional<Sale> findByCode(String code) {
		return repository.findByCode(code);
	}
	
	@Override
	public Set<Sale> findByClient(final Client client) {
		return repository.findByClient(client);
	}
	
	@Override
	public Set<Sale> findByDate(final Date date) {
		return repository.findByDate(date);
	}
	
	@Override
	public List<Sale> findBtwnDates(final Date from, Date to) {
		return repository.findBtwnDates(from, to);
	}
	
	@Override
	public List<Sale> findBtwnDates(final Date from, final Date to, final Long id) {
		return repository.findBtwnDates(from, to, id);
	}

	@Override
	public Set<Sale> findByCreditDisbursed(boolean confirm, Long id) {
		final Set<Sale> sales = repository.findByClientId(id);
		final Set<Sale> sales_ = new HashSet<Sale>(); 
		
		sales.stream().filter(sale ->{
			final Optional<CreditDisbursment> disbursed = disbursmentService.findBySale(sale);
			if(disbursed.isPresent())
				return (disbursed.get().isPending() == confirm);
			else
				return (disbursed.isPresent() == confirm);
		}).forEach(sale->{
			sales_.add(sale);
		});
		return sales_;
	}
	
	@Override
	public SaleReport saveMpesa(final _Sale _sale) {

		final String _transactionFeeCurrency= _sale.getTransactionFee().substring(0, 3);
		final Country transactionFeeCurrency = countryService.findByCurrency(_transactionFeeCurrency);
		
		final double transactionFee = Double.parseDouble(_sale.getTransactionFee()
				.substring(4).trim());

		final String value = _sale.getValue();
		final String _paymentCurrency = value.substring(0, 3);		
		final Country paymentCurrency = countryService.findByCurrency(_paymentCurrency);
		final double paymentAmount_ = Double.parseDouble(value.substring(4).trim());

		final boolean successful = isSuccessful(_sale.getStatus());
		
		if(!successful) {
			//save sale with code/providerRefId being null
			return new SaleReport();
		}
		final Sale sale = save(new Sale(_sale.getProviderRefId(), SaleType.MPESAC2B, successful, 
				value));

		final Payment payment = paymentService.save(new Payment(_sale.getSource(), paymentCurrency,
				paymentAmount_, sale));
		
		final CostOfSale transactionFee_cost = costService.save(new CostOfSale(sale.getCode(), 
				ChargeType.TRANSACTION_FEE, transactionFeeCurrency, transactionFee));
		
		//send email to proAktiv-io _admin notifying them about the sale
		final SaleReport report = new SaleReport(sale, payment, transactionFee_cost);
		
		emailService.sendEmail(new EmailMessage(proactiveEmail, "New Sale", "A new sale was made. Details"
				+ "are as follows: "+report.toString()));
		report.setCode(200);
		report.setTitle("success");
		report.setMessage("successfully added mpesa notification");
		
		return report;
	}
	
	private boolean isSuccessful(final String _status) {
		if(_status.equals("Success"))
			return true;
		else
			return false;
	}
	
	@Override
	public boolean isValid(final Sale sale, final String email, final double amount) {
		final Optional<Payment> payment = paymentService.findBySale(sale);
		if(!payment.isPresent())
			return false;
		if(payment.get().getAmount() != amount) 
			return false;
		final ClientUser user = userService.findByEmail(email).get();
		final Client client = user.getClient();
		if(!sale.isSuccessful()) {
			return false;
		}
		if(sale.getClient() != null) {
			final Optional<CreditDisbursment> disburse = disbursmentService.findBySale(sale);
			if(disburse.isPresent()) {
				if(!disburse.get().isPending())
					return false;
			}
		}else {
			//set the client
			sale.setClient(client);
			save(sale);
		}
		return true;
	}
	@Override
	public Report commitPayment(final _Payment confirm) {

		final Optional<Sale> _sale = findByCode(confirm.getMpesaNo());
		if(!_sale.isPresent()) 
			return new Report(400, "failed", "invalid payment confirmation");
		final Sale sale = _sale.get();
		final boolean valid = isValid(sale, confirm.getEmail(), confirm.getAmount());
		
		if(!valid)
			return new Report(400, "failed", "invalid payment confirmation");
		
		//get client
		final ClientUser user = userService.findByEmail(confirm.getEmail()).get();
		final Client client = user.getClient();
		
		//get product
		final Product product = productService.findByName(confirm.getProductName());
		final Payment payment = paymentService.findBySale(sale).get();
		final Set<CostOfSale> costs = costService.findByNumber(confirm.getMpesaNo());
		costs.stream()
				.forEach(cost->{
					cost.setProduct(product);
					cost.setClient(client);
					costService.save(cost);
				});
		//confirm if payment was for sender id
		if(confirm.getType().equals(ProductType.SENDER_ID)) {
			return commitSenderIdPayment(confirm, sale, client, product);	
		}
		return commitCreditPayment(sale, client, product, payment);		
	}

	private Report commitCreditPayment(final Sale sale, final Client client, final Product product,
			final Payment payment) {
		final CreditDisbursment disburse = disbursementService.save(new CreditDisbursment(true, sale));
		//update the sale with product and client information
		sale.setProduct(product);
		sale.setClient(client);
		save(sale);
		//add client's credit
			//check if there is sufficient inventory
		final Inventory inventory = inventoryService.findByCountry(client.getCountry());
		final BigDecimal invAmount = BigDecimal.valueOf(inventory.getAmount());
			//convert payment amount to clients currency
		final BigDecimal amount = rateService.exchange(payment.getCurrency(), client.getCountry(), 
				payment.getAmount());
		if(invAmount.compareTo(amount) == -1) {
			return new Report(200, "success", "confirmation received. Amount will"
					+ "be credited to your account after 10 to 20 minutes.");
		}
		//subtract inventory
		invAmount.subtract(amount);
		invAmount.setScale(2, RoundingMode.HALF_EVEN);
		//update inventory
		inventory.setAmount(invAmount.doubleValue());
		inventoryService.save(inventory);
		//add client's credit
		final BigDecimal credit = BigDecimal.valueOf(client.getCreditAmount());
		credit.add(amount);
		credit.setScale(2, RoundingMode.HALF_EVEN);
		client.setCreditAmount(credit.doubleValue());
		clientService.save(client);
		//update disbursement
		disburse.setPending(Boolean.FALSE);
		disbursementService.save(disburse);
		
		return new Report(200, "success", "amount has been credited to your account");
	}

	private Report commitSenderIdPayment(final _Payment confirm, final Sale sale, final Client client,
			final Product product) {
		//confirm if sender id is already taken or it belongs to the client
		final Optional<SenderId> _senderId = senderIdService.findByName(confirm.getSenderId());
		if(_senderId.isPresent()) {
			final SenderId senderId = _senderId.get();
			//check if it belongs to the client
			if(!senderId.getClient().equals(client)) {
				return new Report(400, "failed", "sender id not available"); 
			}
			//add accepted countries for sender id
			senderId.getCountries().add(product.getCountry());
			senderIdService.save(senderId);
		}else {
			//save the sender id
			senderIdService.save(new SenderId(confirm.getSenderId(), client,
					product.getCountry()));
		}
		//update the sale with product and client information
		sale.setProduct(product);
		sale.setClient(client);
		save(sale);
		
		return new Report(200, "success", "sender id confirmation successful");
	}
}
