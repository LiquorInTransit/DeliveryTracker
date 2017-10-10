package com.gazorpazorp.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "SAMPLE")
public class TrackingEvent {

	private Long id;
	private Long deliveryId;
	
	@Enumerated(EnumType.STRING)
	private TrackingEventType trackingEventType;

	private Double latitude;
	private Double longitude;

	public TrackingEvent() {
	}

	private Timestamp createdAt;

	@PrePersist
	void onCreate() {
		this.setCreatedAt(new Timestamp(new Date().getTime()));
	}

	@Id
	@GenericGenerator(name = "incrementGenerator", strategy = "org.hibernate.id.IncrementGenerator")
	@GeneratedValue(generator = "incrementGenerator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public TrackingEventType getCartEventType() {
		return trackingEventType;
	}
	public void setCartEventType(TrackingEventType trackingEventType) {
		this.trackingEventType = trackingEventType;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
