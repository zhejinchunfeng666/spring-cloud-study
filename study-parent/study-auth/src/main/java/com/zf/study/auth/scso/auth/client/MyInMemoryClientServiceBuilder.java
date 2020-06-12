package com.zf.study.auth.scso.auth.client;

import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;

public class MyInMemoryClientServiceBuilder extends InMemoryClientDetailsServiceBuilder{
	@Override
	protected void addClient(String clientId, ClientDetails value) {
		super.addClient(clientId, value);
	}
}
