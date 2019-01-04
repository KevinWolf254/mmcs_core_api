package com.proaktivio.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.proaktivio.AfricasTalkingGateway;
import com.proaktivio.configurations.AfricasTalkingProperties;
import com.proaktivio.enums.ChargeType;
import com.proaktivio.models.Charge;
import com.proaktivio.models.Client;
import com.proaktivio.models.ClientUser;
import com.proaktivio.models.CostOfSale;
import com.proaktivio.models.Country;
import com.proaktivio.models.InternationalPrice;
import com.proaktivio.models.Inventory;
import com.proaktivio.models.Price;
import com.proaktivio.models.Product;
import com.proaktivio.models.SenderId;
import com.proaktivio.models.ServiceProvider;
import com.proaktivio.pojo.DeliveryReport;
import com.proaktivio.pojo.EmailMessage;
import com.proaktivio.pojo.ServiceProviderReport;
import com.proaktivio.pojo.Sms;
import com.proaktivio.pojo.SmsDeliveryReport;
import com.proaktivio.services.ChargesService;
import com.proaktivio.services.ClientService;
import com.proaktivio.services.ClientUserService;
import com.proaktivio.services.CostOfSaleService;
import com.proaktivio.services.CountryService;
import com.proaktivio.services.EmailService;
import com.proaktivio.services.ExchangeRateService;
import com.proaktivio.services.InternationalPriceService;
import com.proaktivio.services.InventoryService;
import com.proaktivio.services.PriceService;
import com.proaktivio.services.ProductService;
import com.proaktivio.services.SenderIdService;
import com.proaktivio.services.ServiceProviderService;
import com.proaktivio.services.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private CountryService countryService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientUserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CostOfSaleService costOfSaleService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PriceService priceService;
	@Autowired
	private InternationalPriceService intPriceService;
	@Autowired
	private ChargesService chargeService;
	@Autowired
	private ExchangeRateService rateService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private SenderIdService senderIdService;
	@Autowired
	private ServiceProviderService serviceProviderService;

	@Autowired
	private AfricasTalkingProperties properties;
	
	@Value("${spring.mail.username}")
	private String supportEmail;
	
	private AfricasTalkingGateway gateway;
	private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
	private double totalCost;
	
	@Override
	public SmsDeliveryReport sendSms(final Sms sms) {
		
		gateway = new AfricasTalkingGateway(properties.getUsername(), properties.getKey());	
		
		final Optional<ClientUser> _user = userService.findByEmail(sms.getSender());
		if(!_user.isPresent())
			return new SmsDeliveryReport(400, "error", "emial does not exist");
		
		final ClientUser user = _user.get();
		final Client client = clientService.findByUsersEmail(user.getEmail()).get();
		final Country clientCountry = countryService.findByClientsId(client.getId());
		
		//get senderId
		final Optional<SenderId> _senderId = senderIdService.findByName(sms.getSenderId());
		final String senderIdName;
		final Collection<ServiceProviderReport> phoneNosTotals = sms.getPhoneNosTotals();

		final String message = sms.getMessage();
		final Charge charge = chargeService.findByType(ChargeType.BASIC);
		Set<Country> raised_countries = new HashSet<Country>();
		
		if(!_senderId.isPresent())
			senderIdName = "";
		else {
			senderIdName = _senderId.get().getName();
			raised_countries = _senderId.get().getCountries();	
		}

		//total cost is in client's currency
		final BigDecimal totalCost = estimateCost(clientCountry, raised_countries, senderIdName, phoneNosTotals, message,
				charge);
		
		final BigDecimal credit = BigDecimal.valueOf(client.getCreditAmount());			
		final Country kenya = countryService.findByName("KENYA");
		if(totalCost.compareTo(credit) == 1) 
        	return new SmsDeliveryReport(400, "Credit Insufficient", "Kindly top up credit");
	
		final Inventory _inventory = inventoryService.findByCountry(clientCountry);
		final BigDecimal inventory = BigDecimal.valueOf(_inventory.getAmount());
		
		if(inventory.compareTo(totalCost) == -1) {
			final BigDecimal kes_topUp = rateService.exchange(clientCountry, kenya,	totalCost.doubleValue());
			emailService.sendEmail(new EmailMessage(supportEmail, 
					"Insufficient Inventory", 
					"Required Inventory: "+clientCountry.getCurrency()+
					" "+totalCost.doubleValue()+
					". Top up amount: "+kenya.getCurrency()+
					" "+kes_topUp.doubleValue()));
			
        		return new SmsDeliveryReport(200, "Success", "Confirmation received. Kindly wait for"
        				+ "10 to 20 minutes and resend sms");
		}  
		final DeliveryReport apiReport = sendToApi(message, sms.getRecipients(), senderIdName, kenya);
		
		if(apiReport.getCode() != 200)
			return new SmsDeliveryReport(apiReport.getCode(), apiReport.getTitle(), apiReport.getMessage());
		
		//fee charged by africa's talking (its in kes_)
        final double aft_Fee = apiReport.getTotalCost().doubleValue();
        //save the cost (cost to proaktiv_io)
		costOfSaleService.save(new CostOfSale(apiReport.getMessageId(), ChargeType.BASIC, kenya, 
        		aft_Fee, null, client));
        
        //deduct client's credit
        credit.subtract(totalCost);
        clientService.save(client);
        
        //deduct inventory
        inventory.subtract(totalCost);
		inventory.setScale(2, RoundingMode.HALF_EVEN);
		_inventory.setAmount(inventory.doubleValue());
		inventoryService.save(_inventory);
		return new SmsDeliveryReport(apiReport.getCode(), apiReport.getTitle(), apiReport.getMessage(), apiReport.getMessageId(), 
				apiReport.getReceived(), apiReport.getRejected(), apiReport.getRejectedNos());
	}

	public BigDecimal estimateCost(final Country clientCountry, final Set<Country> raised_countries,
			final String senderId, final Collection<ServiceProviderReport> phoneNosTotals, 
			final String message, final Charge charge) {
		
		totalCost = 0;
		//when sender id is empty charge local price in native country and charge international for the rest
		if(senderId.isEmpty() || senderId == null || senderId == "") 
			phoneNosTotals.stream()
				.forEach(report->{
					//get product e.g. sms_ke and the country
					final Optional<ServiceProvider> provider = serviceProviderService.findByName(report.getProvider().getName());
					final Optional<Product> product = productService.findById(provider.get().getProduct().getId());	
					final Country prodCountry = product.get().getCountry();					
					//get the set price for the product
					final Price price = priceService.find(charge, product.get());
					final InternationalPrice intPrice = price.getInternationalPrice();
					//since no sender id was specified, charge local price for local sms_ and charge international prices for the rest
					if(prodCountry.getId().equals(clientCountry.getId())) {
						//multiply price by number of subscribers						
						double price_ = price.getTotal() * report.getSubscribers();
						totalCost = totalCost + price_;
					}else {
						//multiply price by number of subscribers						
						final double price_ = price.getTotal() * report.getSubscribers();
						
						totalCost = totalCost + rateService.exchange(intPrice.getCurrency(), clientCountry, 
													price_).doubleValue();	
					}
				});
		//when sender id is present should include checking the countries it was raised in
		else
			phoneNosTotals.stream()
				.forEach(report->{
					//get product e.g. sms_ke and the country
					final Optional<ServiceProvider> provider = serviceProviderService.findByName(report.getProvider().getName());
					final Optional<Product> product = productService.findById(provider.get().getProduct().getId());
					final Country prodCountry = product.get().getCountry();
					//get the set price for the product
					final Price price = priceService.find(charge, product.get());
					final InternationalPrice intPrice = intPriceService.findByPricesId(price.getId()).get();

					//check among the raised countries if the product is present
					final boolean isRaised = raised_countries.stream().anyMatch(country_-> prodCountry.equals(country_));
					if(isRaised) {
						//multiply price by number of subscribers
						final double price_ = price.getTotal() * report.getSubscribers();						
						if(price.getCurrency().getId().equals(clientCountry.getId())) 
							totalCost = totalCost + price_;
						else
							totalCost = totalCost +	rateService.exchange(prodCountry, clientCountry, price_).doubleValue();
					}else {
						//multiply price by number of subscribers
						double price_ = price.getTotal() * report.getSubscribers();
						totalCost = totalCost +	rateService.exchange(intPrice.getCurrency(), clientCountry, 
								price_).doubleValue();	
					}
				});
		
		if(message.length() > 160)
			totalCost = totalCost * 2;
		return BigDecimal.valueOf(totalCost);
	}
	
	private DeliveryReport sendToApi(final String message, final String recipients, final String senderId,
			final Country kenya) {
		String messageId = null;
        int received = 0;
        int rejected = 0;
        final StringBuilder rejectedNos = new StringBuilder();          

        final BigDecimal aft_Fees = BigDecimal.ZERO; 
		try {
			//send sms_ to gateway and receive a response
			final JSONArray results = gateway.sendMessage(recipients, message, senderId);
			messageId = results.getJSONObject(0).getString("messageId");  
			
			for( int i = 0; i < results.length(); ++i ) {
			    final JSONObject result = results.getJSONObject(i);

				final String sCost = result.getString("cost");
				final Country finalCostCurrency = countryService.findByCurrency(sCost
						.substring(0, 3).trim());
				double finalCost = Double.parseDouble(sCost.substring(3).trim());

				if(result.getString("status").equals("Success"))
					received = received++;
				else {
					rejectedNos.append(result.getString("number"));
					rejected = rejected++;
				}  
				if(!finalCostCurrency.equals(kenya)) {
					final BigDecimal kes_finalCost = rateService.exchange(finalCostCurrency, kenya,
							finalCost);
					finalCost = kes_finalCost.doubleValue();
				}  
				aft_Fees.add(BigDecimal.valueOf(finalCost));    		
			}
		} catch (NumberFormatException e) {
        	log.info("NumberFormatException: "+e.getMessage());
    		return new DeliveryReport(400, "failed", "something happened. could not complete sending");
		} catch (JSONException e) {
        	log.info("JSONException: "+e.getMessage());
    		return new DeliveryReport(400, "failed", "something happened. could not complete sending");
		} catch (Exception e) {
        	log.info("Exception: "+e.getMessage());
    		return new DeliveryReport(400, "failed", "something happened. could not complete sending");
		}	        
		aft_Fees.setScale(2, RoundingMode.HALF_EVEN);
		return new DeliveryReport(200, "success", "", messageId, received, rejected, 
				rejectedNos.toString(), aft_Fees);
	}
}
