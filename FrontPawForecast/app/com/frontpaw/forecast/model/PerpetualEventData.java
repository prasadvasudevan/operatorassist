package com.frontpaw.forecast.model;

import java.util.Date;
import java.util.List;

import com.frontpaw.forecast.schema.EventTiming;
import com.frontpaw.forecast.schema.Params;
import com.frontpaw.forecast.schema.StrategyTiming;

public class PerpetualEventData {

	
	private String name;
	
	private String userName;
	
	private String ebbUserParamName;
	
	private String ebbUserParamValue;
	
	private EventTiming eventTiming;
	
	private StrategyTiming strategyTiming;
	
	private Params params;	
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the ebbUserParamName
	 */
	public String getEbbUserParamName() {
		return ebbUserParamName;
	}


	/**
	 * @param ebbUserParamName the ebbUserParamName to set
	 */
	public void setEbbUserParamName(String ebbUserParamName) {
		this.ebbUserParamName = ebbUserParamName;
	}


	/**
	 * @return the ebbUserParamValue
	 */
	public String getEbbUserParamValue() {
		return ebbUserParamValue;
	}


	/**
	 * @param ebbUserParamValue the ebbUserParamValue to set
	 */
	public void setEbbUserParamValue(String ebbUserParamValue) {
		this.ebbUserParamValue = ebbUserParamValue;
	}

	/**
	 * @return the eventTiming
	 */
	public EventTiming getEventTiming() {
		return eventTiming;
	}
	
	/**
	 * @param eventTiming the eventTiming to set
	 */
	public void setEventTiming(EventTiming eventTiming) {
		this.eventTiming = eventTiming;
	}

	/**
	 * @return the strategyTiming
	 */
	public StrategyTiming getStrategyTiming() {
		return strategyTiming;
	}


	/**
	 * @param strategyTiming the strategyTiming to set
	 */
	public void setStrategyTiming(StrategyTiming strategyTiming) {
		this.strategyTiming = strategyTiming;
	}

	/**
	 * @return the params
	 */
	public Params getParams() {
		return params;
	}


	/**
	 * @param params the params to set
	 */
	public void setParams(Params params) {
		this.params = params;
	}


	public PerpetualEventData() {
		// TODO Auto-generated constructor stub
	}

	

}
