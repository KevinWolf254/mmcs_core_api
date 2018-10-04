package com.proaktivio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proaktivio.enums.ChargeType;
import com.proaktivio.models.Charge;

public interface ChargeRepository extends JpaRepository<Charge, Long> {

	public Charge findByType(ChargeType type);
}
