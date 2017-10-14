package com.gazorpazorp.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.TrackingEvent;
import com.gazorpazorp.service.TrackingService;

@RestController
@RequestMapping("/internal/tracking")
public class InternalTrackingController {

	
	@Autowired
	TrackingService trackingService;
	
	@PostMapping("/{trackingId}")
	@PreAuthorize("#oauth2.hasScope('system')")
	public ResponseEntity createTrackingEvent(@RequestBody TrackingEvent trackingEvent, @PathVariable("trackingId") UUID trackingId) throws Exception {
		return Optional.ofNullable(trackingService.createEvent(trackingEvent, trackingId, false))
				.map(t -> new ResponseEntity(HttpStatus.OK))
				.orElseThrow(() -> new Exception("Failed to create event"));
	}
}
