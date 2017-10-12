package com.gazorpazorp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.TrackingEvent;
import com.gazorpazorp.model.dto.DeliveryStatusDto;
import com.gazorpazorp.service.TrackingService;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {
	
	@Autowired
	TrackingService trackingService;
	
	
	@GetMapping("/{trackingId}")
	@PreAuthorize("#oauth2.hasScope('customer')")
	public ResponseEntity getTrackingStatusById (@PathVariable("trackingId") Long deliveryId) throws Exception {
		return Optional.ofNullable(trackingService.getTrackingInfoById(deliveryId))
				.map(s -> new ResponseEntity<DeliveryStatusDto>(s, HttpStatus.OK))
				.orElseThrow(() -> new Exception("An unknown error has occured."));
	}
	
	@PostMapping("/{trackingId}")
	@PreAuthorize("#oauth2.hasAnyScope('driver','system')")
	public ResponseEntity createTrackingEvent(@RequestBody TrackingEvent trackingEvent, @PathVariable("trackingId") Long deliveryId) throws Exception {
		return Optional.ofNullable(trackingService.createEvent(trackingEvent, deliveryId))
				.map(t -> new ResponseEntity(HttpStatus.OK))
				.orElseThrow(() -> new Exception("Failed to create event"));
	}
	
	@PostMapping("/new")
	@PreAuthorize("#oauth2.hasScope('system')")
	public ResponseEntity<String> createFirstEvent (@RequestParam Long deliveryId) throws Exception {
		return Optional.ofNullable(trackingService.createFirstTrackingEvent(deliveryId))
				.map(o -> new ResponseEntity<String>(o, HttpStatus.OK))
				.orElseThrow(() -> new Exception("Could not create sample!"));
	}
	
	


	
	
}
