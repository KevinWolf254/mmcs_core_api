package com.proaktivio.services;

import com.proaktivio.enums.ChargeType;
import com.proaktivio.models.Charge;
import com.proaktivio.pojo.ChargesReport;
import com.proaktivio.pojo.Sms;

public interface ChargesService {

	/**
	 * Find Charge by the type of charge
	 * @param type
	 * @return
	 */
	public Charge findByType(ChargeType type);

	/**
	 * Calculates the charges associated with sending an sms
	 * @param sms
	 * @return
	 */
	public ChargesReport calculate(Sms sms);
}
