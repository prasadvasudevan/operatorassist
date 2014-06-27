/**
 * 
 */
package com.frontpaw.forecast.model;

import java.util.Date;
import java.util.List;

import com.frontpaw.forecast.schema.Params;

/**
 * @author Project
 *
 */
public class EventData {
	
	
	private String eventName;
	
	private List<Date> eventDate;
	
	private String eventValue;
	
	private String ebbUserName;
	
	private String userName;
	
	private String eventType;
	
	private Params params;
	
	private String name;
	
	private String ebbUserSetParamName;
	
	private String ebbName;
	
	public String getEbbName() {
		return ebbName;
	}



	public void setEbbName(String ebbName) {
		this.ebbName = ebbName;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEbbUserSetParamName() {
		return ebbUserSetParamName;
	}



	public void setEbbUserSetParamName(String ebbUserSetParamName) {
		this.ebbUserSetParamName = ebbUserSetParamName;
	}



	public Params getParams() {
		return params;
	}



	public void setParams(Params params) {
		this.params = params;
	}



	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}



	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}



	/**
	 * @return the eventDate
	 */
	public List<Date> getEventDate() {
		return eventDate;
	}



	/**
	 * @param eventDate the eventDate to set
	 */
	public void setEventDate(List<Date> eventDate) {
		this.eventDate = eventDate;
	}



	/**
	 * @return the eventValue
	 */
	public String getEventValue() {
		return eventValue;
	}



	/**
	 * @param eventValue the eventValue to set
	 */
	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}



	/**
	 * @return the ebbUserName
	 */
	public String getEbbUserName() {
		return ebbUserName;
	}



	/**
	 * @param ebbUserName the ebbUserName to set
	 */
	public void setEbbUserName(String ebbUserName) {
		this.ebbUserName = ebbUserName;
	}



	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}



	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}



	/**
	 * 
	 */
	public EventData() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}



	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	
}
