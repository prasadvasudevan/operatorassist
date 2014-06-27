package com.frontpaw.forecast.model;

public class DateValueMap {
	
	public String date;
	public String value;

	public DateValueMap() {
		// TODO Auto-generated constructor stub
		
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		String[] dateParts= date.split("-");	
		
		this.date = "Date.UTC("+dateParts[0]+"," +dateParts[1]+", 0)";
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
