package com.proaktivio.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

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
import com.proaktivio.pojo.Invoice;
import com.proaktivio.pojo.Report;
import com.proaktivio.pojo.SaleReport;
import com.proaktivio.pojo.PaymentConfirmation;
import com.proaktivio.pojo.AT_Notification;
import com.proaktivio.repositories.SaleRepository;
import com.proaktivio.services.ClientService;
import com.proaktivio.services.ClientUserService;
import com.proaktivio.services.CostOfSaleService;
import com.proaktivio.services.CountryService;
import com.proaktivio.services.CreditDisbursementService;
import com.proaktivio.services.EmailService;
import com.proaktivio.services.ExchangeRateService;
import com.proaktivio.services.InvoiceService;
import com.proaktivio.services.InventoryService;
import com.proaktivio.services.PaymentService;
import com.proaktivio.services.ProductService;
import com.proaktivio.services.SaleService;
import com.proaktivio.services.SenderIdService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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
	@Autowired
	private InvoiceService invoiceService;
	
	private static final Logger log = LoggerFactory.getLogger(SaleServiceImpl.class);
	
	@Value("${spring.mail.username}")
	private String proactiveEmail;
	
	@Override
	public Sale save(final Sale sale) {
		Sale sale_ = null;
		try {
			sale_ = repository.save(sale);
		} catch (Exception e) {		
			log.error("Exception: "+e.getMessage());
			return new Sale();
		}
		return sale_;
	}
	
	@Override
	public Optional<Sale> findByInvoiceNo(final String invoiceNo) {
		 return repository.findByInvoiceNo(invoiceNo);
	}
	
	@Override
	public Optional<Sale> findByCode(final String code) {
		return repository.findByCode(code);
	}
	
	@Override
	public Set<Sale> findByClient(final Client client) {
		final Set<Sale> sales = repository.findByClient(client);
		return sales;
	}
	
	@Override
	public Set<Sale> findByDate(final Date date) {
		return repository.findByDate(date);
	}
	
	@Override
	public List<Sale> findBtwnDates(final Date from, final Date to) {
		return repository.findBtwnDates(from, to);
	}
	
	@Override
	public List<Sale> findBtwnDates(final Date from, final Date to, final Long id) {
		return repository.findBtwnDates(from, to, id);
	}

	@Override
	public Set<Sale> findByCreditDisbursed(final boolean confirm, final Long id) {
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
	public SaleReport convertNotification(final AT_Notification _sale) {

		final String _transactionFeeCurrency= _sale.getTransactionFee().substring(0, 3);
		final Country transactionFeeCurrency = countryService.findByCurrency(_transactionFeeCurrency);
		
		final double transactionFee = Double.parseDouble(_sale.getTransactionFee().substring(4).trim());

		final String value = _sale.getValue();
		final String _paymentCurrency = value.substring(0, 3);		
		final Country paymentCurrency = countryService.findByCurrency(_paymentCurrency);
		final double paymentAmount_ = Double.parseDouble(value.substring(4).trim());

		final boolean successful = isSuccessful(_sale.getStatus());
		
		if(!successful) 
			//save sale with code/providerRefId being null
			return new SaleReport();
		
		final Sale sale = save(new Sale( _sale.getProviderRefId(), SaleType.MPESAC2B, successful, 
				value));

		final Payment payment = paymentService.save(new Payment(_sale.getSource(), paymentCurrency,
				paymentAmount_, sale));
		
		final CostOfSale transactionFee_cost = costService.save(new CostOfSale(sale.getCode(), 
				ChargeType.TRANSACTION_FEE, transactionFeeCurrency, transactionFee));
		
		//send email to proAktiv-io _admin notifying them about the sale
		final SaleReport report = new SaleReport(200, "success", "successfully added mpesa notification", 
				sale, payment, transactionFee_cost);
		
		StringBuilder builder = new StringBuilder("Transaction Id: ")
				.append(_sale.getProviderRefId())
				.append(" ,Client: ")
				.append(_sale.getClientAccount())
				.append(" ,Amount: ")
				.append(_sale.getValue());
		emailService.sendEmail(new EmailMessage(proactiveEmail, "New Sale",builder.toString()));
		
		return report;
	}
	
	private boolean isSuccessful(final String _status) {
		return _status.equals("Success");
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
	public Report confirmation(final PaymentConfirmation details) {
		
		final Optional<Sale> _sale = findByCode(details.getMpesaNo());
		if(!_sale.isPresent()) 
			return new Report(400, "failed", "Mpesa Transaction Number not found");
		final Sale sale = _sale.get();
		final boolean valid = isValid(sale, details.getEmail(), details.getAmount());
		
		if(!valid)
			return new Report(400, "failed", "Payment is invalid.");
		//get client
		final ClientUser user = userService.findByEmail(details.getEmail()).get();
		final Client client = user.getClient();

		//set invoice no
		final Country country = countryService.findByClientsId(client.getId());
		final String invoiceNo = invoiceService.generateInvoiceNo(country.getCurrency());
		sale.setInvoiceNo(invoiceNo);
		
		//get product
		final Product product = productService.findByName(details.getProductName());
		final Payment payment = paymentService.findBySale(sale).get();
		final Set<CostOfSale> costs = costService.findByNumber(details.getMpesaNo());
		costs.stream()
				.forEach(cost->{
					cost.setProduct(product);
					cost.setClient(client);
					costService.save(cost);
				});
		
		//confirm if payment was for sender id
		if(details.getProductType().equals(ProductType.SENDER_ID)) {
			return senderIdConfirmation(details, sale, client, user, product);	
		}
		return unitsConfirmation(sale, client, user, product, payment);		
	}

	private Report unitsConfirmation(final Sale sale, final Client client, final ClientUser user, 
			final Product product, final Payment payment) {
		final CreditDisbursment disburse = disbursementService.save(new CreditDisbursment(true, sale));
		//update the sale with product and client information
		final String invoiceNo = sale.getInvoiceNo();
		sale.setProduct(product);
		sale.setClient(client);
		save(sale);
		//add client's credit
			//check if there is sufficient inventory
		final Inventory inventory = inventoryService.findByCountry(client.getCountry());
		BigDecimal invAmount = BigDecimal.valueOf(inventory.getAmount());
			//convert payment amount to clients currency
		BigDecimal amount = null;
		
		if(payment.getCurrency().equals(client.getCountry())) {
			amount = new BigDecimal(payment.getAmount());
		}else
			amount = rateService.exchange(payment.getCurrency(), client.getCountry(), payment.getAmount());
		if(invAmount.compareTo(amount) == -1 || invAmount.doubleValue() == 0.0) {
			//send email with invoice to user
			sendInvoice(new Invoice(invoiceNo, client.getCustomerId(), client.getName(), user.getEmail(), 
					user.getPhoneNo(), "Purchase of Units.", sale.getType().toString(), sale.getDate(), 
					sale.getCode(), payment.getCurrency().getCurrency(),payment.getAmount()));
			
			return new Report(200, "success", "confirmation received. Amount will "
					+ "be credited to your account after 10 to 20 minutes.");
		}
		//subtract inventory
		invAmount = invAmount.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);
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

		//send email with invoice to user
		sendInvoice(new Invoice(invoiceNo, client.getCustomerId(), client.getName(), user.getEmail(), 
				user.getPhoneNo(), "Purchase of Units.", sale.getType().toString(), sale.getDate(), 
				sale.getCode(), payment.getCurrency().getCurrency(),payment.getAmount()));
				
		return new Report(200, "success", "amount has been credited to your account");
	}

	private Report senderIdConfirmation(final PaymentConfirmation payment, final Sale sale, final Client client, final ClientUser user,
			final Product product) {
		//confirm if sender id is already taken or it belongs to the client
		final Optional<SenderId> _senderId = senderIdService.findByName(payment.getSenderId());
		String senderId_ = null;
		if(_senderId.isPresent()) {
			final SenderId senderId = _senderId.get();
			//check if it belongs to the client
			if(!senderId.getClient().equals(client)) 
				return new Report(400, "failed", "sender id not available"); 
			
			//add accepted countries for sender id
			senderId.getCountries().add(product.getCountry());
			senderIdService.save(senderId);
			senderId_ = senderId.getName();
		}else {
			senderId_ = payment.getSenderId();
			//save the sender id
			senderIdService.save(new SenderId(senderId_, client, product.getCountry()));
		}
		//update the sale with product and client information
		final String invoiceNo = sale.getInvoiceNo();
		sale.setProduct(product);
		sale.setClient(client);
		save(sale);

		//send email with invoice to user
		sendInvoice(new Invoice(invoiceNo, client.getCustomerId(), client.getName(), user.getEmail(), 
						user.getPhoneNo(), "Purchase of Sender Id - "+senderId_, sale.getType().toString(), sale.getDate(), 
						sale.getCode(), payment.getCurrency(),payment.getAmount()));
				
		return new Report(200, "success", "sender id confirmation successful");
	}

	private void sendInvoice(final Invoice invoice) {
    	final InputStream input = this.getClass().getResourceAsStream("/jasper/invoice/invoice_v1.jrxml");
    	try {
			final JasperDesign design = JRXmlLoader.load(input);
			final JasperReport report = JasperCompileManager.compileReport(design);			
			final Map<String, Object> params = new HashMap<>();			
			params.put("INVOICE_DETAILS", invoice);	
			
		    final JRDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("empty"));
		    final JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
		    
		    final ByteArrayOutputStream output = new ByteArrayOutputStream();
		    JasperExportManager.exportReportToPdfStream(print, output);
		    
		    final DataSource attachment = new ByteArrayDataSource(output.toByteArray(), "application/pdf");
		    
		    final EmailMessage email = new EmailMessage(invoice.getUserEmail(), "Invoice", "Find attached your invoice");
		    emailService.sendEmail(email, attachment);
	        
    	} catch (JRException e) {
			log.info("JRException: "+e.getMessage());
    	}  catch (Exception e) {
			log.info("Exception: "+e.getMessage());
		}
	}
}
