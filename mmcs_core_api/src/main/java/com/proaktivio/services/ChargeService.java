package com.proaktivio.services;

import com.proaktivio.enums.ChargeType;
import com.proaktivio.models.Charge;

public interface ChargeService {

	public Charge findByType(ChargeType type);
}
