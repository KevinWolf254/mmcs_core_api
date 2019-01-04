INSERT INTO `marketme_db`.`country` (`name`, `code`, `currency`) VALUES ('RWANDA', '+250', 'RWF');
INSERT INTO `marketme_db`.`country` (`name`, `code`, `currency`) VALUES ('KENYA', '+254', 'KES');
INSERT INTO `marketme_db`.`country` (`name`, `code`, `currency`) VALUES ('TANZANIA', '+255', 'TZS');
INSERT INTO `marketme_db`.`country` (`name`, `code`, `currency`) VALUES ('UGANDA', '+256', 'UGX');
INSERT INTO `marketme_db`.`country` (`name`, `code`, `currency`) VALUES ('US', '+001', 'USD');
INSERT INTO `marketme_db`.`country` (`name`, `code`, `currency`) VALUES ('FINLAND', '+358', 'EUR');

INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SENDER_ID_RW', '1');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SENDER_ID_KE', '2');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SENDER_ID_TZ', '3');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SENDER_ID_UG', '4');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SENDER_ID_UG_MTN', '4');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SENDER_ID_UG_AFRICELL', '4');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SENDER_ID_UG_AIRTEL', '4');

INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SMS_RW', '1');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SMS_RW_AIRTEL', '1');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SMS_KE', '2');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SMS_KE_AIRTEL', '2');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SMS_TZ', '3');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SMS_TZ_AIRTEL', '3');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SMS_UG', '3');
INSERT INTO `marketme_db`.`product` (`name`, `country_id`) VALUES ('SMS_UG_AIRTEL', '3');


INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('MTN_RW', '8');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('TIGO_RW', '8');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('AIRTEL_RW', '9');

INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('SAFARICOM_KE', '10');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('TELKOM_KE', '10');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('EQUITEL_KE', '10');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('AIRTEL_KE', '11');

INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('VODACOM_TZ', '12');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('TIGO_TZ', '12');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('SMILE_TZ', '12');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('HALOTEL_TZ', '12');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('AIRTEL_TZ', '13');

INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('MTN_UG', '14');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('AFRICELL_UG', '14');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('UT_UG', '14');
INSERT INTO `marketme_db`.`service_provider` (`name`, `product_id`) VALUES ('AIRTEL_UG', '15');

INSERT INTO `marketme_db`.`inventory` (`amount`, `country_id`) VALUES ('0', '1');
INSERT INTO `marketme_db`.`inventory` (`amount`, `country_id`) VALUES ('0', '2');
INSERT INTO `marketme_db`.`inventory` (`amount`, `country_id`) VALUES ('0', '3');
INSERT INTO `marketme_db`.`inventory` (`amount`, `country_id`) VALUES ('0', '4');

INSERT INTO `marketme_db`.`charge` (`type`) VALUES ('0');
INSERT INTO `marketme_db`.`charge` (`type`) VALUES ('1');
INSERT INTO `marketme_db`.`charge` (`type`) VALUES ('2');
INSERT INTO `marketme_db`.`charge` (`type`) VALUES ('3');

INSERT INTO `marketme_db`.`international_cost` (`currency`, `tax`, `amount`, `total`) VALUES ('5', '0.16', '0.125', '0.02');
INSERT INTO `marketme_db`.`international_cost` (`currency`, `tax`, `amount`, `total`) VALUES ('6', '0.16', '0.3125', '0.05');

INSERT INTO `marketme_db`.`international_price` (`currency`, `tax`, `margin`, `amount`, `total`) VALUES ('5', '0.16', '0.20', '0.02', '0.028');
INSERT INTO `marketme_db`.`international_price` (`currency`, `tax`, `margin`, `amount`, `total`) VALUES ('6', '0.16', '0.20', '0.05', '0.07');

INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`) VALUES ('1', '0.16', '7500', '8700', '2', '2');
INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`) VALUES ('4', '0.16', '215517.24', '250000', '5', '3');
INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`) VALUES ('4', '0.16', '215517.24', '250000', '6', '2');
INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`) VALUES ('4', '0.16', '215517.24', '250000', '7', '2');

INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`, `international_cost_id`) VALUES ('1', '0.16', '12.10', '14', '7', '1', '1');
INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`, `international_cost_id`) VALUES ('1', '0.16', '12.10', '14', '8', '1', '2');

INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`, `international_cost_id`) VALUES ('2', '0.16', '0.69', '0.8', '9', '1', '1');
INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`, `international_cost_id`) VALUES ('2', '0.16', '0.69', '0.8', '10', '1', '2');

INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`, `international_cost_id`) VALUES ('3', '0.16', '30.17', '35', '11', '1', '1');
INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`, `international_cost_id`) VALUES ('3', '0.16', '30.17', '35', '12', '1', '2');

INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`, `international_cost_id`) VALUES ('4', '0.16', '30.17', '35', '13', '1', '1');
INSERT INTO `marketme_db`.`cost` (`currency`, `tax`, `amount`, `total`, `product_id`, `charge_id`, `international_cost_id`) VALUES ('4', '0.16', '30.17', '35', '14', '1', '2');

INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`) VALUES ('1', '0.16', '0.20', '8700', '12110.40', '2', '2');
INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`) VALUES ('4', '0.16', '0.20', '250000', '290000', '5', '3');
INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`) VALUES ('4', '0.16', '0.20', '250000', '290000', '6', '2');
INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`) VALUES ('4', '0.16', '0.20', '250000', '290000', '7', '2');


INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`, `international_price_id`) VALUES ('1', '0.16', '0.20', '14', '19.50', '8', '1', '1');
INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`, `international_price_id`) VALUES ('1', '0.16', '0.20', '14', '19.50', '9', '1', '2');

INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`, `international_price_id`) VALUES ('2', '0.16', '0.20', '0.8', '1.12', '10', '1', '1');
INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`, `international_price_id`) VALUES ('2', '0.16', '0.20', '0.8', '1.12', '11', '1', '2');

INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`, `international_price_id`) VALUES ('3', '0.16', '0.20', '35', '48.72', '12', '1', '1');
INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`, `international_price_id`) VALUES ('3', '0.16', '0.20', '35', '48.72', '13', '1', '2');

INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`, `international_price_id`) VALUES ('4', '0.16', '0.20', '35', '48.72', '14', '1', '1');
INSERT INTO `marketme_db`.`price` (`currency`, `tax`, `margin`, `amount`, `total`, `product_id`, `charge_id`, `international_price_id`) VALUES ('4', '0.16', '0.20', '35', '48.72', '15', '1', '2');

INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('1', '2', '0.11');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('1', '3', '2.59');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('1', '4', '4.33');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('1', '5', '0.0011');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('1', '6', '0.00099');

INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('2', '1', '8.72');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('2', '3', '22.63');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('2', '4', '37.84');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('2', '5', '0.0099');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('2', '6', '0.0086');

INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('3', '1', '0.39');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('3', '2', '0.044');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('3', '4', '1.67');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('3', '5', '0.00044');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('3', '6', '0.00038');

INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('4', '1', '0.23');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('4', '2', '0.027');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('4', '3', '0.61');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('4', '5', '0.00027');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('4', '6', '0.00023');

INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('5', '1', '881.83');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('5', '2', '100.95');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('5', '3', '2285');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('5', '4', '3819.65');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('5', '6', '0.85');

INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('6', '1', '1037.34');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('6', '2', '118.75');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('6', '3', '2687.97');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('6', '4', '4493.26');
INSERT INTO `marketme_db`.`exchange_rate` (`from`, `to`, `rate`) VALUES ('6', '5', '1.18');
