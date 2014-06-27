package com.frontpaw.forecast.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.BeanUtils;

import com.frontpaw.forecast.common.util.CommonUtils;
import com.frontpaw.forecast.schema.Dates;
import com.frontpaw.forecast.schema.Values;

// TODO: Auto-generated Javadoc
/**
 * The Class Chart.
 */
public class Chart {

	/**
	 * Gets the measure type.
	 *
	 * @return the measure type
	 */
	public String getMeasureType() {
		return measureType;
	}

	/**
	 * Sets the measure type.
	 *
	 * @param measureType the new measure type
	 */
	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}

	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	/**
	 * Gets the model name.
	 *
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * Sets the model name.
	 *
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/** The model name. */
	public String modelName;
	
	/** The Name. */
	public String name;
	
	/** The measure type. */
	public String measureType;
	
	/** The values. */
	public List<List<Object>> values;
	
	/** The dates. */
	public com.frontpaw.forecast.model.Dates dates;
	
	public DateValueMap datevalue;
	
	/**
	 * Instantiates a new chart.
	 *
	 * @param modelNameArg the model name arg
	 * @param nameArg the name arg
	 * @param measureTypeArg the measure type arg
	 * @param valuesArg the values arg
	 * @param datesArg the dates arg
	 */
	public Chart(String modelNameArg, String nameArg, String measureTypeArg, Values valuesArg, Dates datesArg,DateValueMap datevalue)
	{
		this.modelName=modelNameArg;
		this.name=nameArg;
		this.measureType = measureTypeArg;
		this.datevalue=datevalue;
		this.values = new ArrayList<List<Object>>();
		for(String value: valuesArg.getValue())
		{
			List<Object> dataObj = new ArrayList<Object>();
			dataObj.add(datevalue.getDate());
			if(value.equalsIgnoreCase("-")){
				dataObj.add(Double.valueOf(0));
			}else{
				dataObj.add(Double.valueOf(value));
			}
			
			values.add(dataObj);
		}
		
		this.dates = extactDates(datesArg);
	}
	
	/**
	 * Instantiates a new chart.
	 *
	 * @param modelNameArg the model name arg
	 * @param nameArg the name arg
	 * @param measureTypeArg the measure type arg
	 * @param valuesArg the values arg
	 * @param datesArg the dates arg
	 * @throws ParseException 
	 */
	
	public Chart(String modelNameArg, String nameArg, String measureTypeArg, Values valuesArg, Dates datesArg, int size, DateValueMap datevalue) throws ParseException
	{
		this.modelName=modelNameArg;
		this.name=nameArg;
		this.measureType = measureTypeArg;
		this.datevalue=datevalue;
		this.values = new ArrayList<List<Object>>();
		int count = 0 ;
		List<String> dates = datesArg.getDate() ;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		TimeZone zone =  TimeZone.getTimeZone("UTC");
		format.setTimeZone(zone);
		Map<String,Date> convertedDatesMap = 	CommonUtils.convertedDateMap(datesArg);
		//System.out.println("date size  chart class..................." + size);
		
		for(String value: valuesArg.getValue())
		{
			List<Object> dataObj = new ArrayList<Object>();
			//datevalue.setDate(dates.getcount));
			//new Date(dates.get(count));
			dataObj.add(convertedDatesMap.get(((dates.get(count)))));
			if(value.equalsIgnoreCase("-")){
				dataObj.add(Double.valueOf(0));
			}else{
				dataObj.add(Double.valueOf(value));
			}				
			if( size > 0  && size > count)
			{
				values.add(dataObj);
			}
			else if(size > 0 && size <= count){
				break;
			}else {
				values.add(dataObj);	
			}
				
			count++;
		}
		
		this.dates = extactDates(datesArg,size);
	}
	
	private com.frontpaw.forecast.model.Dates extactDates(Dates dates, int size){
		com.frontpaw.forecast.model.Dates date = new com.frontpaw.forecast.model.Dates();
		//System.out.println("size:*****"+size);
		if(size>dates.getDate().size())
			size=dates.getDate().size();
		if(size > 0 ){
			date.setDate(dates.getDate().subList(0, size));
		}else{
			BeanUtils.copyProperties(dates, date);
		}
		
	return 	date;
	}
	
	private com.frontpaw.forecast.model.Dates extactDates(Dates dates){
		com.frontpaw.forecast.model.Dates date = new com.frontpaw.forecast.model.Dates();
		BeanUtils.copyProperties(dates, date);
		
		//date.setDates(date.getDates().subList(0, size));
		
	return 	date;
	}
	
}