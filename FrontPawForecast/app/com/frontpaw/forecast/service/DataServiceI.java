/**
 * 
 */
package com.frontpaw.forecast.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import com.frontpaw.forecast.model.Chart;
import com.frontpaw.forecast.model.DateValueMap;
import com.frontpaw.forecast.model.Measures;
import com.frontpaw.forecast.model.MenuChart;

// TODO: Auto-generated Javadoc
/**
 * The Interface DataServiceI.
 *
 * @author Administrator
 */
public interface DataServiceI {
	
	
	/**
	 * Login service.
	 *
	 * @param userName the user name
	 * @param password the password
	 * @return true, if successful
	 */
	boolean	loginService(String userName,String password );
	
	/**
	 * Gets the report.
	 *
	 * @param filterParameters the filter parameters
	 * @return the report
	 */
	Map getReport(Map<String,Integer> filterParameters,String email,InputStream input,ArrayList<String> forecastName);
	
	Map getReloadReport(Map<String, Integer> filterParameters,String email,InputStream input,ArrayList<String> forecastName);  
	
	List<DateValueMap> getDateVales();
	
	Map<Integer,Object> setComparisionDisplay() throws Exception;

	Map<String, List<String>> getAccountMap(List<Measures> measures);
  
	String setBaselineSnapShotData();
	
	LinkedHashMap<String,Object> setBaselineSnapShotMapData(String email);

	
}
