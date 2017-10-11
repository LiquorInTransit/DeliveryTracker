package com.gazorpazorp.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class Delivery {

	private Long id;
	private Long driverId;
	
	
	private Dropoff dropoff;
	
	
	public Delivery () {}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Dropoff getDropoff() {
		return dropoff;
	}
	public void setDropoff(Dropoff dropoff) {
		this.dropoff = dropoff;
	}
}
