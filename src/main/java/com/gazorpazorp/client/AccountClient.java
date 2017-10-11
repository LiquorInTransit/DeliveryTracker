package com.gazorpazorp.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Driver;

@FeignClient(name="account-service")
public interface AccountClient {
	@GetMapping(value="/me", consumes = "application/json")
	Customer getCustomer();
	
	@GetMapping(value="/drivers/me", consumes = "application/json")
	Driver getDriver();
}
