/**
 * 
 */
package com.frontpaw.forecast.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import com.frontpaw.forecast.schema.Dates;

/**
 * @author Project
 *
 */
public class CommonUtils {

	/**
	 * 
	 */
	public CommonUtils() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static  Map<String, Date> convertedDateMap(Dates dates) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		TimeZone zone =  TimeZone.getTimeZone("UTC");
		format.setTimeZone(zone);
		Map<String, Date> datesMap = new LinkedHashMap<String, Date>();
		for (String value : dates.getDate()) {

			datesMap.put(value, format.parse(value));
		}
		
		return datesMap;
		
	}


}
