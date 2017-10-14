package com.gazorpazorp.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.gazorpazorp.client.config.TokenRequestClientConfiguration;
import com.gazorpazorp.model.Delivery;

@FeignClient(name="order-and-delivery-service", configuration = TokenRequestClientConfiguration.class)
public interface DeliveryClient {
	
	@GetMapping(value="/internal/deliveries/{deliveryId}", consumes = "application/json")
	Delivery getDeliveryById(@PathVariable("deliveryId") Long deliveryId);
	
	@Async
	@PostMapping(value="/internal/complete/{deliveryId}")
	ResponseEntity completeDelivery(@PathVariable("deliveryId") Long deliveryId);
}

