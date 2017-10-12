package com.gazorpazorp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gazorpazorp.client.AccountClient;
import com.gazorpazorp.client.DeliveryClient;
import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.Delivery;
import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.TrackingEvent;
import com.gazorpazorp.model.TrackingEventType;
import com.gazorpazorp.model.dto.DeliveryStatusDto;
import com.gazorpazorp.repository.TrackingEventRepository;

import reactor.core.publisher.Flux;

@Service
public class TrackingService {

	@Autowired
	TrackingEventRepository trackingEventRepo;
	@Autowired
	DeliveryClient deliveryClient;
	@Autowired
	AccountClient accountClient;
	
	@Transactional(readOnly=false)
	public DeliveryStatusDto getTrackingInfoById(Long deliveryId) throws Exception {
		if (!verifyCustomer(deliveryId))
			throw new Exception ("You are not authorized to track this delivery");
		DeliveryStatusDto status = aggregateTrackingEvents(deliveryId);
		return status;
	}	
	
	public String createFirstTrackingEvent (Long deliveryId) throws Exception {
		List<TrackingEvent> events = trackingEventRepo.findByDeliveryId(deliveryId);
		if (events == null || events.isEmpty()) {
			TrackingEvent ev = new TrackingEvent();
			ev.setDeliveryId(deliveryId);
			ev.setLocation(null);
			ev.setTrackingEventType(TrackingEventType.AWAITING_ACCEPTANCE);
			trackingEventRepo.save(ev);
			return "www.liquorintransit.party/api/tracking/"+deliveryId;
		} else {
			throw new Exception ("Tracking for this delivery has already begun");
		}
	}
	
	public Boolean createEvent(TrackingEvent trackingEvent, Long deliveryId, boolean verify) throws Exception {
		if (verify && !verifyDriver(deliveryId)) {
			throw new Exception ("Not authorized to post updates to this delivery");
		} else {
			trackingEvent.setDeliveryId(deliveryId);
			trackingEventRepo.save(trackingEvent);
			return true;
		}
	}
	
	//Getting functions
	@Transactional(readOnly=false)
	public DeliveryStatusDto aggregateTrackingEvents(Long deliveryId) throws Exception{		
		Flux<TrackingEvent>trackingEvents = Flux.fromStream(trackingEventRepo.findByDeliveryIdOrderByCreatedAtDesc(deliveryId));
		DeliveryStatusDto status = trackingEvents
			//	.takeWhile(trackingEvent -> trackingEvent.getTrackingEventType() != TrackingEventType.DELIVERED)
				.reduceWith(() -> new DeliveryStatusDto(), DeliveryStatusDto::incorporate)
				.get();
		
		TrackingEvent ev = trackingEventRepo.findTopByDeliveryIdOrderByCreatedAtDesc(deliveryId);
		if (ev != null)
			status.setLocation(ev.getLocation());
		else 
			status.setLocation(null);
		return status;
	}
	
	public boolean verifyCustomer(Long deliveryId) throws Exception{
		Customer customer = getCustomer();
		Delivery delivery = getDelivery(deliveryId);
		
		if (delivery==null || customer==null)
			throw new Exception("Delivery or customer does not exist");
		
		return delivery.getDropoff().getCustomerId().equals(customer.getId());
	}
	public boolean verifyDriver(Long deliveryId) throws Exception {
		Driver driver = getDriver();
		Delivery delivery = getDelivery(deliveryId);
		
		if (delivery == null || driver == null) 
			throw new Exception("Delivery or driver does not exist");
		
		return delivery.getDriverId() == driver.getId();		
	}
	private Customer getCustomer() {
		return accountClient.getCustomer();
	}
	private Driver getDriver() {
		return accountClient.getDriver();
	}
	private Delivery getDelivery(Long deliveryId) throws Exception {
		Delivery delivery = deliveryClient.getDeliveryById(deliveryId);
		if (delivery == null) 
			throw new Exception ("Delivery you are tracking does not exist");
		return delivery;
	}
}
