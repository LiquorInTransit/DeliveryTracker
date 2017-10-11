package com.gazorpazorp.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.gazorpazorp.model.Location;
import com.gazorpazorp.model.TrackingEvent;
import com.gazorpazorp.model.TrackingEventType;

import reactor.core.publisher.Flux;

public class DeliveryStatusDto {
	
	private TrackingEventType status;
	private Location location;
	private List<TrackingEvent> events = new ArrayList<TrackingEvent>();
	
	public TrackingEventType getStatus() {
		return status;
	}
	public void setStatus(TrackingEventType status) {
		this.status = status;
	}
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public List<TrackingEvent> getEvents() {
		return events;
	}
	public void setEvents(List<TrackingEvent> events) {
		this.events = events;
	}
	
	
	public DeliveryStatusDto incorporate(TrackingEvent trackingEvent) {
		System.out.println(trackingEvent);
		Flux<TrackingEventType> invalidTrackingEventTypes = Flux.fromStream(Stream.of(TrackingEventType.UPDATE_LOCATION));
		if (invalidTrackingEventTypes.exists(trackingEventType -> !trackingEvent.getTrackingEventType().equals(trackingEventType)).get()) {
			events.add(trackingEvent);
		}
		if (!events.isEmpty())
			setStatus(events.get(0).getTrackingEventType());
		return this;
	}
	
}
