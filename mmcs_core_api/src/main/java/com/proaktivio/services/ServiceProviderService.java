package com.proaktivio.services;

import java.util.Optional;

import com.proaktivio.models.ServiceProvider;

public interface ServiceProviderService {

	public Optional<ServiceProvider> findByName(String name);
}
