package com.proaktivio.repositories;

import com.proaktivio.models.Charge;
import com.proaktivio.models.Price;
import com.proaktivio.models.Product;

public interface PriceRepositoryCustom {

	public Price find(Charge charge, Product product);
}
