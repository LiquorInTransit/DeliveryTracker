package com.gazorpazorp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gazorpazorp.client.DeliveryClient;
import com.gazorpazorp.model.TrackingEvent;
import com.gazorpazorp.repository.TrackingEventRepository;

@Service
public class TrackingService {

	@Autowired
	TrackingEventRepository trackingEventRepo;
	@Autowired
	DeliveryClient sampleClient;
	

	public TrackingEvent getTrackingInfoById(Long sampleId) {
		return trackingEventRepo.findById(sampleId).get();
	}	
	
	public TrackingEvent createTrackingEvent (TrackingEvent sample) throws Exception {
		return trackingEventRepo.save(sample);
	}
}
