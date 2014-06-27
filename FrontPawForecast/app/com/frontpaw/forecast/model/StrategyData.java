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
public class StrategyData {
	
	
	private String strategyName;
	
	private List<Date> strategyDate;
	
	private String strategyValue;
	
	private String StrategyTiming;
	
	private String userName;
	
	private Params params;
	
	private String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public List<Date> getStrategyDate() {
		return strategyDate;
	}

	public void setStrategyDate(List<Date> strategyDate) {
		this.strategyDate = strategyDate;
	}

	public String getStrategyValue() {
		return strategyValue;
	}

	public void setStrategyValue(String strategyValue) {
		this.strategyValue = strategyValue;
	}

	public String getStrategyTiming() {
		return StrategyTiming;
	}

	public void setStrategyTiming(String strategyTiming) {
		StrategyTiming = strategyTiming;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Params getParams() {
		return params;
	}

	public void setParams(Params params) {
		this.params = params;
	}	
	
}
