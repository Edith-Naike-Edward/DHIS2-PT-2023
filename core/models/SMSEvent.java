package org.openmrs.module.icare.core.models;

import org.openmrs.module.icare.ICareConfig;

public class SMSEvent extends ICareConfig {
	
	private String event;
	
	public SMSEvent() {
	}
	
	public SMSEvent(String event) {
		this.event = event;
	}
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	@Override
	public String toString() {
		return "SMSEvent{" + "event='" + event + '\'' + '}';
	}
}
