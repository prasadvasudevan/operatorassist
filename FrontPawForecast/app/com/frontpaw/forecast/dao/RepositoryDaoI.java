/**
 * 
 */
package com.frontpaw.forecast.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.frontpaw.forecast.model.EventData;
import com.frontpaw.forecast.model.PerpetualEventData;
import com.frontpaw.forecast.schema.Dates;
import com.frontpaw.forecast.schema.EconomicUnit;
import com.frontpaw.forecast.schema.Metrics;
import com.frontpaw.forecast.schema.Models;
import com.frontpaw.forecast.schema.Statement;

// TODO: Auto-generated Javadoc
/**
 * The Interface RepositoryDaoI.
 *
 * @author Administrator
 */
public interface RepositoryDaoI {
	
	/**
	 * Read default xml.
	 *
	 * @param fileName the file name
	 * @return the list
	 */
	List<List<Statement>> readDefaultXml(String fileName,String email,InputStream input,ArrayList<String> forecastName);
	
	/**
	 * Creates the perpetual events.
	 *
	 * @param modelName the model name
	 * @return the list
	 */
//	List<PerpetualEventData> createPerpetualEvents(String modelName);
	
	/**
	 * Creates the perpetual events.
	 *
	 * @param modelName the model name
	 * @return the list
	 */
	List<List<String>> createPerpetualEvents(String modelName);
	
	/**
	 * Gets the perpetual height.
	 *
	 * @return the perpetual height
	 */
	int getPerpetualHeight();
	
	 /**
 	 * Gets the key measures.
 	 *
 	 * @return the key measures
 	 */
 	Set<String> getKeyMeasures();
	/**
	 * Gets the forcast models.
	 *
	 * @return the forcast models
	 */
	Models getForcastModels();
	
	/*Gets the Metrics*/
	Metrics getMetrics();
	 /**
 	 * Gets the measures data.
 	 *
 	 * @param modelName the model name
 	 * @return the measures data
 	 */
	EconomicUnit getEconomicUnit();
 	Map<String,String> getMeasuresData(String modelName);
	
	/**
	 * Gets the dates length map.
	 *
	 * @return the dates length map
	 */
	Map<String,Integer> getDatesLengthMap();
	
	/**
	 * Gets the dates.
	 *
	 * @return the dates
	 */
	Dates getdates();
	
	/**
	 * Gets the modelname.
	 *
	 * @return the modelname
	 */
	List<String> getmodelname();
	
	/**
	 * Creates the events data.
	 *
	 * @return the list
	 */
	Map<Integer,Object> createEventsData();
	
	/**
	 * Creates the strategies data.
	 *
	 * @return the list
	 */
	Map<Integer,Object> createStrategiesData();
	
	/**
	 * Creates the perpetual events.
	 *
	 * @param modelname the modelname
	 * @return the list
	 */
	//List<PerpetualEventData> createPerpetualEvents();
	/**
	 * Creates the compare event dataes.
	 *
	 * @param modelname the modelname
	 * @return the map
	 */
	// Map<String,Map<String,List<EventData>>> createCompareEventDataes(String modelname);
	 Map<String,Map<String,List<List<String>>>> createCompareEventDataes(String modelname) throws Exception;
	 
	 List<Date> convertDatesToReportDateFormat(List<String> xmlDates);


}
