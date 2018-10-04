package com.proaktivio.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
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
import com.proaktivio.pojo.DeliveryReport;
import com.proaktivio.pojo.EmailMessage;
import com.proaktivio.pojo.PhoneNos;
import com.proaktivio.pojo.Sms;
import com.proaktivio.pojo.SmsDeliveryReport;
import com.proaktivio.services.ChargeService;
import com.proaktivio.services.ClientService;
import com.proaktivio.services.ClientUserService;
import com.proaktivio.services.CostOfSaleService;
import com.proaktivio.services.CountryService;
import com.proaktivio.services.EmailService;
import com.proaktivio.services.ExchangeRateService;
import com.proaktivio.services.InventoryService;
import com.proaktivio.services.PriceService;
import com.proaktivio.services.ProductService;
import com.proaktivio.services.SenderIdService;
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
	private ChargeService chargeService;
	@Autowired
	private ExchangeRateService rateService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private SenderIdService senderIdService;
	
	@Value("${support.email}")
	private String email;
	
	@Value("${mmcs.aeon.at_username}")
	private String username;
	@Value("${mmcs.aeon.at_apikey}")
	private String apiKey;
	
	@Value("${mmcs.aeon.sender_id}")
	private String default_senderId;
	
	private AfricasTalkingGateway gateway;
	private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);

	@Override
	public SmsDeliveryReport sendSms(final Sms sms) {
		
		gateway = new AfricasTalkingGateway(username, apiKey);	
		
		final Optional<ClientUser> _user = userService.findByEmail(sms.getSender());
		if(!_user.isPresent())
			return new SmsDeliveryReport();
		
		final ClientUser user = _user.get();
		final Client client = clientService.findByUsersEmail(user.getEmail());
		final Country clientCountry = countryService.findByClientsId(client.getId());
		
		//get senderId
		final Optional<SenderId> _senderId = senderIdService.findByName(sms.getSenderId());
		final String senderIdName;
		final Collection<PhoneNos> phoneNosTotals = sms.getPhoneNosTotals();

		final String message = sms.getMessage();
		final Charge charge = chargeService.findByType(ChargeType.BASIC);
		
		if(!_senderId.isPresent()) 
			senderIdName = "";
		else
			senderIdName = _senderId.get().getName();
		final Set<Country> raised_countries = _senderId.get().getCountries();	

		final BigDecimal totalCost = estimateCost(clientCountry, raised_countries, senderIdName, phoneNosTotals, message,
				charge);
		
		final BigDecimal credit = BigDecimal.valueOf(client.getCreditAmount());			
		final Country kenya = countryService.findByName("KENYA");
		if(totalCost.compareTo(credit) == 1) 
        	return new SmsDeliveryReport(400, "Credit Insufficient", "Kindly top up credit", "", 0, 0, "");
	
		final Inventory _inventory = inventoryService.findByCountry(client.getCountry());
		final BigDecimal inventory = BigDecimal.valueOf(_inventory.getAmount());
		
		if(inventory.compareTo(totalCost) == -1) {
			final BigDecimal kes_topUp = rateService.exchange(client.getCountry(), kenya,
					totalCost.doubleValue());
			emailService.sendEmail(new EmailMessage(email, 
					"Insufficient Inventory", 
					"Required Inventory: "+client.getCountry().getCurrency()+
					" "+totalCost.doubleValue()+
					". Top up amount: "+kenya.getCurrency()+
					" "+kes_topUp.doubleValue()));
			
        		return new SmsDeliveryReport(200, "Success", "Confirmation received. Kindly wait for"
        				+ "10 to 20 minutes and reconfirm", "", 0, 0, "");
		}  
		final DeliveryReport apiReport = sendToApi(message, sms.getRecipients(), senderIdName, kenya);
		
		final String messageId = apiReport.getMessageId();	final int code = apiReport.getCode();		
		final int received = apiReport.getReceived(); final int rejected = apiReport.getRejected();
		final String rejectedNos = apiReport.getRejectedNos(); final String d_message = apiReport.getMessage();
		final String title = apiReport.getTitle();
		
		if(code == 200) {			
	        final Product product = productService.find("SMS", clientCountry);
			final Price price = priceService.find(charge, product);			
	        final double total = apiReport.getTotalCost().doubleValue();
	        
			costOfSaleService.save(new CostOfSale(messageId, ChargeType.BASIC, kenya, 
	        		total, product, client));
	        
	        //change back to clients currency
	        final BigDecimal _clientCost = rateService.exchange(kenya, clientCountry, total);
	        
	        final double beforeTax = (1 + price.getMargin()) * _clientCost.doubleValue();
	        final double afterTax = (1 + price.getTax()) * beforeTax;
	        
	        final BigDecimal clientCost = BigDecimal.valueOf(afterTax);
	        
	        //deduct client's credit
	        credit.subtract(clientCost);
	        clientService.save(client);
	        
	        //deduct inventory
	        inventory.subtract(clientCost);
			inventory.setScale(2, RoundingMode.HALF_EVEN);
			_inventory.setAmount(inventory.doubleValue());
			inventoryService.save(_inventory);
		}
		return new SmsDeliveryReport(code, title, d_message, messageId, received, rejected, rejectedNos);
	}

	private BigDecimal estimateCost(final Country clientCountry, final Set<Country> raised_countries,
			final String senderId, final Collection<PhoneNos> phoneNosTotals, final String message,
			final Charge charge) {
		
		final BigDecimal totalCost = BigDecimal.ZERO;
		if(senderId.isEmpty() || senderId == null) 
			phoneNosTotals.stream()
				.forEach(total->{
					final Product product = productService.findByServiceProviders(total.getTelecom());
					final Country prodCountry = product.getCountry();
					final Price price = priceService.find(charge, product);
					final InternationalPrice intPrice = price.getInternationalPrice();
					if(prodCountry.equals(clientCountry)) 
						totalCost.add(BigDecimal.valueOf(price.getAmount()));
					else
						totalCost.add(rateService.exchange(intPrice.getCurrency(), clientCountry, 
													intPrice.getAmount()));				
				});
		else
			phoneNosTotals.stream()
				.forEach(total->{
					final Product product = productService.findByServiceProviders(total.getTelecom());
					final Country prodCountry = product.getCountry();
					final Price price = priceService.find(charge, product);
					final InternationalPrice intPrice = price.getInternationalPrice();
					final boolean isRaised = raised_countries
												.stream().anyMatch(country_-> prodCountry.equals(country_));
					if(isRaised) {
						if(price.getCurrency().equals(clientCountry)) 
							totalCost.add(BigDecimal.valueOf(price.getAmount()));
						else
							totalCost.add(rateService.exchange(prodCountry, clientCountry, price.getAmount()));
					}else 
						totalCost.add(rateService.exchange(intPrice.getCurrency(), clientCountry, 
								intPrice.getAmount()));			
				});
		
		if(message.length() > 160)
			totalCost.multiply(BigDecimal.valueOf(2));
		return totalCost;
	}
	
	private DeliveryReport sendToApi(final String message, final String recipients, final String senderId,
			final Country kenya) {
		String messageId = null;
        int received = 0;
        int rejected = 0;
        final StringBuilder rejectedNos = new StringBuilder();          

        final BigDecimal cost_amount = BigDecimal.ZERO; 
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
				cost_amount.add(BigDecimal.valueOf(finalCost));    		
			}
		} catch (NumberFormatException e) {
    		return new DeliveryReport(400, "failed", "something happened. could not complete sending", "", 0, 0, 
    				"", BigDecimal.ZERO);
		} catch (JSONException e) {
    		return new DeliveryReport(400, "failed", "something happened. could not complete sending", "", 0, 0, 
    				"", BigDecimal.ZERO);
		} catch (Exception e) {
        	log.info("***** API Error: "+e.getMessage());
    		return new DeliveryReport(400, "failed", "something happened. could not complete sending", "", 0, 0, 
    				"", BigDecimal.ZERO);
		}	        
		cost_amount.setScale(2, RoundingMode.HALF_EVEN);
		return new DeliveryReport(200, "success", "", messageId, received, rejected, 
				rejectedNos.toString(), cost_amount);
	}
}
