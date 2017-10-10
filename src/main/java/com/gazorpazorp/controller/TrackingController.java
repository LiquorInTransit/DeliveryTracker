package com.gazorpazorp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gazorpazorp.model.TrackingEvent;
import com.gazorpazorp.service.TrackingService;

@RestController
@RequestMapping("/api")
public class TrackingController {
	
	@Autowired
	TrackingService trackingService;
	
	
	@GetMapping("/{trackingId}")
	public ResponseEntity getSampleById (@PathVariable Long trackingId) throws Exception {
		return Optional.ofNullable(trackingService.getTrackingInfoById(trackingId))
				.map(s -> new ResponseEntity<TrackingEvent>(s, HttpStatus.OK))
				.orElseThrow(() -> new Exception("An unknown error has occured."));
	}
	
	@PostMapping
	public ResponseEntity<TrackingEvent> createSample (@RequestBody TrackingEvent trackingEvent) throws Exception {
		return Optional.ofNullable(trackingService.createTrackingEvent(trackingEvent))
				.map(o -> new ResponseEntity<TrackingEvent>(o, HttpStatus.OK))
				.orElseThrow(() -> new Exception("Could not create sample!"));
	}
	
	


	
	
}
