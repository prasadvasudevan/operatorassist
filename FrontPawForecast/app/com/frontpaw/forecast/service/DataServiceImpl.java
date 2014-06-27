/**
 * 
 */
package com.frontpaw.forecast.service;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import play.libs.Json;

import com.avaje.ebeaninternal.api.BindParams.Param;
import com.frontpaw.forecast.dao.RepositoryDaoI;
import com.frontpaw.forecast.dao.RepositoryDaoImpl;
import com.frontpaw.forecast.model.BaseLineSnapShotTreeBean;
import com.frontpaw.forecast.model.Chart;
import com.frontpaw.forecast.model.DateValueMap;
import com.frontpaw.forecast.model.EventData;
import com.frontpaw.forecast.model.Measures;
import com.frontpaw.forecast.model.MenuChart;
import com.frontpaw.forecast.model.PerpetualEventData;
import com.frontpaw.forecast.schema.Dates;
import com.frontpaw.forecast.schema.Ebb;
import com.frontpaw.forecast.schema.EconMetrics;
import com.frontpaw.forecast.schema.EconTagNode;
import com.frontpaw.forecast.schema.EconTagNodes;
import com.frontpaw.forecast.schema.EconTagNodes1;
import com.frontpaw.forecast.schema.EconTagTree;
import com.frontpaw.forecast.schema.EconomicUnit;
import com.frontpaw.forecast.schema.Event;
import com.frontpaw.forecast.schema.EventMetrics;
import com.frontpaw.forecast.schema.Events;
import com.frontpaw.forecast.schema.Measure;
import com.frontpaw.forecast.schema.Measuree;
import com.frontpaw.forecast.schema.Metrics;
import com.frontpaw.forecast.schema.Model;
import com.frontpaw.forecast.schema.ModelData;
import com.frontpaw.forecast.schema.Models;
import com.frontpaw.forecast.schema.Params;
import com.frontpaw.forecast.schema.Statement;
import com.frontpaw.forecast.schema.Values;

// TODO: Auto-generated Javadoc
/**
 * The Class DataServiceImpl.
 *
 * @author Administrator
 */
public class DataServiceImpl implements DataServiceI {

	
	/* (non-Javadoc)
	 * @see com.frontpaw.forecast.service.DataServiceI#loginService(java.lang.String, java.lang.String)
	 */
	public boolean loginService(String userName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.frontpaw.forecast.service.DataServiceI#getReport(java.util.Map)
	 * 
	 * Gets the report from DAO by iterating through all four model XML files
	 */
	@Override
	public Map getReport(Map<String, Integer> filterParameters,String email,InputStream input,ArrayList<String> forecastName) {
		// TODO Auto-generated method stub
		
		Map<Integer,List<Object>> displayPlots=new HashMap<Integer, List<Object>>();
		
		try {
			int size = 0;
			String foreCastParam="";
			Set<String> forCastParams = filterParameters.keySet();
			Iterator foreCastParamIt = forCastParams.iterator();
			while(foreCastParamIt.hasNext()){
				foreCastParam = foreCastParamIt.next().toString();
			}
			if(filterParameters.get(foreCastParam)!=null)	
				size =  filterParameters.get(foreCastParam);
			else
				foreCastParam="sfp,sfp,sfp,sfp";
		
			String []forecaseParams=foreCastParam.split(",");
		
			//System.out.println("size on date range in DS: "+size);
			//System.out.println("foreCastParam on date range in DS: "+foreCastParam);
		
		/*int noPlots=3;
		int noPlots1=4;*/
	
				List<List<Statement>> allStatements=new ArrayList<List<Statement>>();			
				RepositoryDaoI dao = new RepositoryDaoImpl();
				allStatements  = dao.readDefaultXml("test",email,input,forecastName);
				
				int plots=0;
				for(List<Statement> ls:allStatements)
				{	List<Object> displayChartData=new ArrayList<Object>();	
					List<Measures> measuresData = new ArrayList<Measures>();
					List<Chart> chartData = new ArrayList<Chart>();
					MenuChart menuChart = new MenuChart();						
					Chart buffer = null;
					Dates dates = dao.getdates();	
					String modelName=dao.getmodelname().get(plots);
					System.out.println("modelname " + modelName);

					for(Statement s:ls){
					List<Measure> measure = new ArrayList<Measure>();
					for(Measure m:s.getMeasure()){
						Chart totalIncome = null;
						Chart account = null;
						measure.add(m);
						if(!m.getMeasureType().equalsIgnoreCase(("Ratio"))){						
							
							//System.out.println("********Start IF Fore cast param MeasureType***********: "+forecaseParams[0]);
							if(foreCastParam!=null && m.getAccount().equalsIgnoreCase(forecaseParams[1]) && m.getMeasureType().equalsIgnoreCase(forecaseParams[0]) || foreCastParam!=null && m.getAccount().equalsIgnoreCase(forecaseParams[3]) && m.getMeasureType().equalsIgnoreCase(forecaseParams[2])){
								//System.out.println("********In IF Fore cast param MeasureType***********: "+forecaseParams[0]);
								//System.out.println("Account Name:"+m.getAccount());
								DateValueMap datevalue=new DateValueMap();								
								if(m.getAccount().equalsIgnoreCase(forecaseParams[3])){
									buffer = new Chart(modelName, m.getAccount(),m.getMeasureType(), m.getValues(), dates,size,datevalue);
									//System.out.println("BufferName: "+buffer.getName());
								}
								else{
									account = new Chart(modelName, m.getAccount(),m.getMeasureType(), m.getValues(), dates,size,datevalue);										
									//System.out.println("Name: "+Json.toJson(account));
								}
								
							}
						}else if(m.getMeasureType().equalsIgnoreCase(("Ratio"))){
							
							if(foreCastParam!=null && m.getName().equalsIgnoreCase(forecaseParams[1]) && m.getMeasureType().equalsIgnoreCase(forecaseParams[0]) || foreCastParam!=null && m.getName().equalsIgnoreCase(forecaseParams[3]) && m.getMeasureType().equalsIgnoreCase(forecaseParams[2])){
								//System.out.println("********In IF Fore cast param MeasureType***********: "+forecaseParams[0]);
								//System.out.println("Account Name:"+m.getAccount());
								DateValueMap datevalue=new DateValueMap();								
								if(m.getName().equalsIgnoreCase(forecaseParams[0])||m.getName().equalsIgnoreCase(forecaseParams[1])||m.getName().equalsIgnoreCase(forecaseParams[3]) || m.getName().equalsIgnoreCase(forecaseParams[2])){
									buffer = new Chart(modelName, m.getName(),m.getMeasureType(), m.getValues(), dates,size,datevalue);
									//System.out.println("BufferName: "+buffer.getName());
								}
								else{
									account = new Chart(modelName, m.getName(),m.getMeasureType(), m.getValues(), dates,size,datevalue);										
									//System.out.println("Name: "+account.getName());
								}
							}
							else if(m.getName().equalsIgnoreCase("Net Income")||m.getName().equalsIgnoreCase("Net Worth")){
									Values values=m.getValues();
									List<String> ValueList=values.getValue();
									DateValueMap datevalue=new DateValueMap();					
									totalIncome = new Chart(modelName, m.getName(),m.getMeasureType(), m.getValues(), dates,size,datevalue);									
						}
						}
						if(foreCastParam!=null && foreCastParam.equalsIgnoreCase("sfp,sfp,sfp,sfp") && totalIncome!=null){
							//System.out.println("not forecast");
							//System.out.println("json conversion not forecast"  + Json.toJson(totalIncome));

							chartData.add(totalIncome);
						}else if(account!=null){
							//System.out.println("foreast parameters");
							chartData.add(account);	
							//System.out.println("json conversion foreast parameters"  + Json.toJson(account));

						}
							
					}
					
					measuresData.add(new Measures(measure));
					
				}
		
				//System.out.println("chartData size: "+chartData.size());
				if(buffer!=null){
					chartData.add(buffer);
					//System.out.println("**************Buffer Index**************: "+chartData.indexOf(buffer));//Always buffer obj should be last
				}
				menuChart.setCharts(chartData);
				//System.out.println("chartData size after buffer: "+chartData.size());
				menuChart.setMenuMeasures(measuresData);
		
				displayChartData.add(menuChart);
				
				
				
				List<Date> dateList = dao.convertDatesToReportDateFormat(dao.getdates().getDate());
				List<EventData> eventdataList = (List<EventData>)dao.createEventsData().get(plots);
				List<EventData> eventdatadateList = new ArrayList<EventData>();
		        NumberFormat formatter = new DecimalFormat("############");
		        formatter.setGroupingUsed(true);
		        formatter.setMaximumFractionDigits(0);
		        formatter.setMinimumFractionDigits(0);

				
				for(Date date : dateList){
					String addval = "";
					String addval1 = "";
					
					List<Date> ndate = new ArrayList<Date>();
					EventData dateEventData = new EventData();
					Date dateval = null;
					boolean check1 = false, check2 = false;
					if(eventdataList!=null){

					for(EventData eventData : eventdataList){
						if(eventData.getEventDate().get(0).toString().equals(date.toString())){
							dateval = eventData.getEventDate().get(0);
							if(eventData.getEventType() !=null && eventData.getEventType().equals("EbbUserSetParamEvent")){
								check1 = true;
								System.out.println("value in " + eventData.getEbbUserSetParamName()+ " " + eventData.getEventName() + " " + eventData.getName());
								dateEventData.setEventType(eventData.getEventType());
								;
								//addval = addval + "<br><span style='color:#000;font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;'/>"+eventData.getUserName()+"</span><br>" +eventData.getEbbName()+"<br>"+eventData.getEbbUserName()+"<br><span style='color: navy; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>"+eventData.getEbbUserSetParamName()+"</span><br>"+eventData.getEventName()+" : "+eventData.getEventValue()+"<br>";
								addval = addval + "<br><span style='color:#000;font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;'/>"+eventData.getUserName()+"</span><br>"+eventData.getEbbUserSetParamName()+"</span><br>"+eventData.getEventName()+" :  $"+Math.round(formatter.parse(eventData.getEventValue()).doubleValue()) +"<br>";
							//	System.out.println("report param 1 " + addval);

							}else if(eventData.getEventType() !=null && eventData.getEventType().equals("GeneralEvent")){
								check2 = true;
								dateEventData.setEventType(eventData.getEventType());
								addval1 = addval1 + "<br><span style='color:#000;font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;'/>"+eventData.getUserName()+"</span><br>";

							//	addval1 = addval1 + "<br><span style='color:#000;font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;'/>"+eventData.getUserName()+"</span><br>"+eventData.getEventName();
							//	System.out.println("report param 2" + addval1);
								dateEventData.setParams(eventData.getParams());
							

							}
						}
					//	System.out.println(dateval + addval);
						
					}
					}
					if(check1){
						//System.out.println("date val" + dateval);
						//System.out.println("concat" + addval);

						ndate.add(dateval);
						dateEventData.setEventDate(ndate);
						dateEventData.setUserName(addval);
						eventdatadateList.add(dateEventData);
					}	
					if(check2){
						//System.out.println("date val" + dateval);
						//System.out.println("concat" + addval);

						ndate.add(dateval);
						dateEventData.setEventDate(ndate);
						dateEventData.setUserName(addval);
						eventdatadateList.add(dateEventData);
					}	
					
				}
				
				
				//System.out.println("evendatadateList " + eventdatadateList.size());
				
				
				//displayChartData.add(dao.createEventsData().get(plots));
			  	displayChartData.add(eventdatadateList);

				displayChartData.add(dao.createStrategiesData().get(plots));
				List<Object> dateObj = new ArrayList<Object>();
				dateObj.add(new Integer(dates.getDate().size()));
				System.out.println("displaychartDate size " + dates.getDate().size());
				displayPlots.put(plots,displayChartData);
				displayPlots.put(new Integer(150), dateObj);
				//displayPlots.put(,dates.getDate().size());
				++plots;
				/*System.out.println("noPlots"+noPlots);
				displayChart.put(noPlots,dao.createPerpetualEvents());
				
				String value=null;
				if(noPlots1==4)
				{
				value="baseline";
				}else if(noPlots1==6)
				{
					value="whatif1";
				}else if(noPlots1==8)
				{
					value="whatif2";
				}else if(noPlots1==10)
				{
					value="whatif3";
				}
				
				displayChart.put(noPlots1, dao.createCompareEventDataes(value));
				noPlots=noPlots+2; 
				noPlots1=noPlots1+2;*/
				
		}	
				
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return displayPlots;
	}
	
	
	/* (non-Javadoc)
	 * @see com.frontpaw.forecast.service.DataServiceI#getReport(java.util.Map)
	 * 
	 * Gets the report from DAO by iterating through all four model XML files
	 */
	@Override
	public Map getReloadReport(Map<String, Integer> filterParameters,String email,InputStream input,ArrayList<String> forecastName) {
		// TODO Auto-generated method stub
		
		Map<Integer,List<Object>> displayPlots=new HashMap<Integer, List<Object>>();
		
		try {
			int size = 0;
			String foreCastParam="";
			Set<String> forCastParams = filterParameters.keySet();
			Iterator foreCastParamIt = forCastParams.iterator();
			while(foreCastParamIt.hasNext()){
				foreCastParam = foreCastParamIt.next().toString();
			}
			if(filterParameters.get(foreCastParam)!=null)	
				size =  filterParameters.get(foreCastParam);
			else
				foreCastParam="sfp,sfp,sfp,sfp";
		
			String []forecaseParams=foreCastParam.split(",");
		
			//System.out.println("size on date range in DS: "+size);
			//System.out.println("foreCastParam on date range in DS: "+foreCastParam);
		
		/*int noPlots=3;
		int noPlots1=4;*/
	
				List<List<Statement>> allStatements=new ArrayList<List<Statement>>();			
				RepositoryDaoI dao = new RepositoryDaoImpl();
				allStatements  = dao.readDefaultXml("test",email,input,forecastName);
				
				int plots=0;
				for(List<Statement> ls:allStatements)
				{	List<Object> displayChartData=new ArrayList<Object>();	
					List<Measures> measuresData = new ArrayList<Measures>();
					List<Chart> chartData = new ArrayList<Chart>();
					MenuChart menuChart = new MenuChart();						
				//	Chart buffer = null;
					Dates dates = dao.getdates();					
					String modelName=dao.getmodelname().get(plots);
					//System.out.println("modelname " + modelName);
					int test =0;

					for(Statement s:ls){
					List<Measure> measure = new ArrayList<Measure>();
					for(Measure m:s.getMeasure()){
						Chart totalIncome = null;
						Chart account = null;
						Chart buffer = null;
						
						measure.add(m);
						if(!m.getMeasureType().equalsIgnoreCase(("Ratio"))){						
							
							//System.out.println("********Start IF Fore cast param MeasureType***********: "+forecaseParams[0]);
							if(foreCastParam!=null && m.getAccount().equalsIgnoreCase(forecaseParams[1]) && m.getMeasureType().equalsIgnoreCase(forecaseParams[0]) || foreCastParam!=null && m.getAccount().equalsIgnoreCase(forecaseParams[3]) && m.getMeasureType().equalsIgnoreCase(forecaseParams[2])){
						//		System.out.println("********In IF Ratio Fore cast param MeasureType***********: " + m.getAccount() +"......"+ forecaseParams[1] + ".."+forecaseParams[0]);
						//		System.out.println("********In IF Ratio Fore cast param MeasureType***********: " + m.getAccount() +"......"+ forecaseParams[3] + ".."+forecaseParams[2]);
								
								//System.out.println("Account Name:"+m.getAccount());
								DateValueMap datevalue=new DateValueMap();								
								if(m.getAccount().equalsIgnoreCase(forecaseParams[3])||m.getAccount().equalsIgnoreCase(forecaseParams[2])||m.getAccount().equalsIgnoreCase(forecaseParams[1]) || m.getAccount().equalsIgnoreCase(forecaseParams[0])){
									//System.out.println("model name " + modelName);
									System.out.println("inside if");
									buffer = new Chart(modelName, m.getAccount(),m.getMeasureType(), m.getValues(), dates,size,datevalue);
									//System.out.println("BufferName: "+buffer.getName());
								}
								else{
									System.out.println("inside else");

									account = new Chart(modelName, m.getAccount(),m.getMeasureType(), m.getValues(), dates,size,datevalue);	
									
									
									//System.out.println("Name: "+account.getName());
								//	System.out.println("model name " + modelName);
									
								}
								
							}
						}else if(m.getMeasureType().equalsIgnoreCase(("Ratio"))){
							
							
							if(foreCastParam!=null && m.getName().equalsIgnoreCase(forecaseParams[1]) && m.getMeasureType().equalsIgnoreCase(forecaseParams[0]) || foreCastParam!=null && m.getName().equalsIgnoreCase(forecaseParams[3]) && m.getMeasureType().equalsIgnoreCase(forecaseParams[2])){
								DateValueMap datevalue=new DateValueMap();			
						//		System.out.println(m.getName() + "==" + forecaseParams[3] + " " + m.getName().length() + "== " + forecaseParams[3].length() + "  " + test);
								if(m.getName().trim().equalsIgnoreCase(forecaseParams[3])){
						//			System.out.println("Account Name if:"+m.getName());

									buffer = new Chart(modelName, m.getName(),m.getMeasureType(), m.getValues(), dates,size,datevalue);
									//System.out.println("BufferName: "+buffer.getName());
								}
								else{
						//			System.out.println("Account Name else:"+m.getName());
									account = new Chart(modelName, m.getName(),m.getMeasureType(), m.getValues(), dates,size,datevalue);									

									//account = new Chart(modelName, m.getName(),m.getMeasureType(), m.getValues(), dates,size,datevalue);										
									//System.out.println("Name: "+account.getName());
								}
							}

							else if(m.getName().equalsIgnoreCase("Total Income")||m.getName().equalsIgnoreCase("Total Expense")){
									DateValueMap datevalue=new DateValueMap();					
									totalIncome = new Chart(modelName, m.getName(),m.getMeasureType(), m.getValues(), dates,size,datevalue);									
							}
						}
						if(foreCastParam!=null && foreCastParam.equalsIgnoreCase("sfp,sfp,sfp,sfp") && totalIncome!=null){
							//System.out.println("not forecast");
							chartData.add(totalIncome);
							//System.out.println("json conversion 123"  + Json.toJson(account));

						}else if(account!=null){
							//System.out.println("forecast parameters");
							if(test<=0){
								chartData.add(account);	
						//		System.out.println("json conversion 456 "  + test+"  " + Json.toJson(account));
							}
							test++;
							//account = null;

						}else if(buffer!=null){
							chartData.add(buffer);

						}
						//test++;	
					}
					
					measuresData.add(new Measures(measure));
					
				}
		
				//System.out.println("chartData size: "+chartData.size());
				/*if(buffer!=null){
					chartData.add(buffer);
				//	System.out.println("json conversion "  + Json.toJson(buffer));

					//System.out.println("**************Buffer Index**************: "+chartData.indexOf(buffer));//Always buffer obj should be last
				}*/
				menuChart.setCharts(chartData);
				//System.out.println("chartData size after buffer: "+chartData.size());
				menuChart.setMenuMeasures(measuresData);
				displayChartData.add(menuChart);
				List<Date> dateList = dao.convertDatesToReportDateFormat(dao.getdates().getDate());
				List<EventData> eventdataList = (List<EventData>)dao.createEventsData().get(plots);
				List<EventData> eventdatadateList = new ArrayList<EventData>();
				
		        NumberFormat formatter = new DecimalFormat("############");
		        formatter.setGroupingUsed(true);
		        formatter.setMaximumFractionDigits(0);
		        formatter.setMinimumFractionDigits(0);
				
				for(Date date : dateList){
					String addval = "";
					String addval1 = "";
					
					List<Date> ndate = new ArrayList<Date>();
					EventData dateEventData = new EventData();
					Date dateval = null;
					boolean check1 = false, check2 = false;
					if(eventdataList!=null){

					for(EventData eventData : eventdataList){
						if(eventData.getEventDate().get(0).toString().equals(date.toString())){
							dateval = eventData.getEventDate().get(0);
							if(eventData.getEventType() !=null && eventData.getEventType().equals("EbbUserSetParamEvent")){
								check1 = true;

								dateEventData.setEventType(eventData.getEventType());
								//addval = addval + "<span style='color:#000;font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;'/>"+eventData.getUserName()+"</span><br>" +eventData.getEbbName()+"<br>"+eventData.getEbbUserName()+"<br><span style='color: navy; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>"+eventData.getEbbUserSetParamName()+"</span><br>"+eventData.getEventName()+"<br>";
							//	addval = addval + "<span style='color:#000;font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;'/>"+eventData.getUserName()+"<br><span style='color: navy; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>"+eventData.getEbbUserSetParamName()+"</span><br>"+"$"+formatter.format(eventData.getEventName())+"<br>";
								//addval = addval + "<br><span style='color:#000;font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;'/>"+eventData.getUserName()+"<br><span style='color: navy; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>"+eventData.getEbbUserSetParamName()+"</span><br>"+eventData.getEventName()+" :  $"+formatter.format(eventData.getEventValue()) +"<br>";
								addval = addval + "<br><span style='color:#000;font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;'/>"+eventData.getUserName()+"<br><span style='color: navy; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>"+eventData.getEbbUserSetParamName()+"</span><br>"+eventData.getEventName()+" :  $"+Math.round(formatter.parse(eventData.getEventValue()).doubleValue()) +"<br>";

								
							}else if(eventData.getEventType() !=null && eventData.getEventType().equals("GeneralEvent")){
								check2 = true;
								dateEventData.setEventType(eventData.getEventType());
								addval1 = addval1 + "<span style='color:#000;font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:bold;'/>"+eventData.getUserName()+"</span><br>"+eventData.getEventName();
								dateEventData.setParams(eventData.getParams());
							

							}
						}
					//	System.out.println(dateval + addval);
						
					}
					}
					if(check1){
						//System.out.println("date val" + dateval);
						//System.out.println("concat" + addval);

						ndate.add(dateval);
						dateEventData.setEventDate(ndate);
						dateEventData.setUserName(addval);
						eventdatadateList.add(dateEventData);
					}	
					if(check2){
						//System.out.println("date val" + dateval);
						//System.out.println("concat" + addval);

						ndate.add(dateval);
						dateEventData.setEventDate(ndate);
						dateEventData.setUserName(addval);
						eventdatadateList.add(dateEventData);
					}	
					
				}
				
				
				//System.out.println("evendatadateList " + eventdatadateList.size());
				
				
				//displayChartData.add(dao.createEventsData().get(plots));
			  	displayChartData.add(eventdatadateList);

				displayChartData.add(dao.createStrategiesData().get(plots));
				List<Object> dateObj = new ArrayList<Object>();
				dateObj.add(new Integer(dates.getDate().size()));
				System.out.println("display chart date "+ dates.getDate().size());
				displayPlots.put(plots,displayChartData);
				displayPlots.put(new Integer(150), dateObj);
				++plots;
				/*System.out.println("noPlots"+noPlots);
				displayChart.put(noPlots,dao.createPerpetualEvents());
				
				String value=null;
				if(noPlots1==4)
				{
				value="baseline";
				}else if(noPlots1==6)
				{
					value="whatif1";
				}else if(noPlots1==8)
				{
					value="whatif2";
				}else if(noPlots1==10)
				{
					value="whatif3";
				}
				
				displayChart.put(noPlots1, dao.createCompareEventDataes(value));
				noPlots=noPlots+2; 
				noPlots1=noPlots1+2;*/
				
		}	
				
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return displayPlots;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public List<DateValueMap> getDateVales() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see com.frontpaw.forecast.service.DataServiceI#setComparisionDisplay()
	 */
	public  Map<Integer,Object> setComparisionDisplay() throws Exception{
		
		Map<Integer, Object> displayChart=new HashMap<Integer, Object>();
		Map<String,Object> allMap = new LinkedHashMap<String, Object>();
		RepositoryDaoI dao = new RepositoryDaoImpl();
		//dao.readDefaultXml("Read from WS");
		//dao.getdates();
		int noPlots=3;
		int noPlots1=4;

		
		Models models = dao.getForcastModels();

		//models.
		//System.out.println("forecaste name " + models.getModel().get(0).getName());
	//	System.out.println("models................"+models);
		List<Model> modelList = models.getModel();
	//	System.out.println("models................"+modelList.size());

		List<String> modelNames = new ArrayList<String>();
		Set<String> listOfvalues = new LinkedHashSet<String>();
		listOfvalues.add("Prepetual");
		modelNames.add("Empty");
		
		allMap.put("firstSection", modelNames);
		for(Model model : modelList){
			Map<String, Map<String,List<List<String>>>> events =  dao.createCompareEventDataes(model.getName());
			/*if(events!=null){
				
			}*/
			allMap.put(model.getName()+"%%compareDates",events);
		//	System.out.println("model Name ........." + model.getName());
			List<List<String>> perpEvents = dao.createPerpetualEvents(model.getName());
			
			allMap.put(model.getName()+"%%perpetual",perpEvents);
			Map<String,String> keyMeasures = dao.getMeasuresData(model.getName());
			allMap.put(model.getName()+"%%keyMeasures", keyMeasures);
			modelNames.add(model.getName());
			noPlots=noPlots+2; 
			noPlots1=noPlots1+2;
			
		}
		Map<String,Integer> datesLenghtmap =  dao.getDatesLengthMap();
		List<String> forecastdates = dao.getdates().getDate();
		//System.out.println("forecastDates" + forecastdates);
		displayChart.put(50, allMap);
		displayChart.put(51, datesLenghtmap);
		displayChart.put(52, dao.getPerpetualHeight());
		displayChart.put(53, dao.getKeyMeasures());
		displayChart.put(54, forecastdates);
		
		//System.out.println(displayChart);
		return displayChart;
	}
	
	
	/* (non-Javadoc)
	 * @see com.frontpaw.forecast.service.DataServiceI#getAccountMap(java.util.List)
	 * 
	 * Return the Map with key as Measure Type and values as Accounts in that Measure 
	 */
	public Map<String, List<String>> getAccountMap(List<Measures> measures)
	{
		Map<String, List<String>> accountMap = null;
		SortedSet<String> measureType = new TreeSet<String>();
		for(Measures sm:measures){
			for(Measure m:sm.getMeasures()){
				measureType.add(m.getMeasureType());
			}			
		}
		//System.out.println("measureType Set ### "+measureType.size());
		accountMap = new LinkedHashMap<String, List<String>>();
//		List<String> accountName= new ArrayList<String>();
		for(Measures sm:measures){
			for(Measure m:sm.getMeasures()){
				//List<String> accountName= new ArrayList<String>();
				if(measureType.contains(m.getMeasureType()) && !accountMap.containsKey(m.getMeasureType())){
					List<String> aName= new ArrayList<String>();
					if(m.getAccount()!=null)
						aName.add(m.getAccount());
					else
						aName.add(m.getName());
					accountMap.put(m.getMeasureType(), aName);
				}else if(measureType.contains(m.getMeasureType()) && accountMap.containsKey(m.getMeasureType())){
					List<String> aNames=accountMap.get(m.getMeasureType());
					if(m.getAccount()!=null)
						aNames.add(m.getAccount());
					else
						aNames.add(m.getName());
					//System.out.println("a "+aNames.size());
					accountMap.put(m.getMeasureType(),aNames);
				}
				
			}			
		}
		//System.out.println("accountMap size:" +accountMap.size());
		//System.out.println("accountMap Expense element:" +accountMap.get("Expense"));
		return  accountMap;
	}
/*	public static void main(String[] agrs){
		
		DataServiceImpl imp = new DataServiceImpl();
		Map<Integer, MenuChart> chart = imp.getReport(null);
		Map<String, List<String>> accountMap =imp.getAccountMap(chart.get(0).getMenuMeasures());
	}*/
	
	
	private void findEbbOrEvent(Measuree measure, BaseLineSnapShotTreeBean measureNode,List<Ebb> ebbs){
		boolean isEbbEvent = false;
		String ebbName = measure.getEbbName();
		if(ebbName!=null){
			isEbbEvent = true;
		}
		if(isEbbEvent){
			Ebb ebb = new Ebb();
			ebb.setName(ebbName);
			int index = ebbs.indexOf(ebb);
			if(index!=-1){
			ebb = ebbs.get(index);
			measureNode.setParams(ebb.getParams().getParam());
			}
		}
		
		
		
		
	}
	
	private void findMeasureOrEconTagNode(EconTagNodes nodes,BaseLineSnapShotTreeBean parentNode,List<Ebb> ebbs){
		
		parentNode.setName(nodes.getName());
		parentNode.setValue(nodes.getValue());
		boolean isMeasure  = false;
		
		List<Measuree> measures = nodes.getMeasuree();
		
		if(measures!=null && !measures.isEmpty()){
			
			isMeasure = true;
		}
		
		if(isMeasure){
			List<BaseLineSnapShotTreeBean> measureBeans = new ArrayList<BaseLineSnapShotTreeBean>();
			for(Measuree measure:measures){
			BaseLineSnapShotTreeBean topNodes = new BaseLineSnapShotTreeBean();
			topNodes.setName(measure.getAccount());
			topNodes.setValue(measure.getValue());
			topNodes.setType("Measure");
			findEbbOrEvent(measure,topNodes,ebbs);
			
			measureBeans.add(topNodes);
			}
			parentNode.setMeasures(measureBeans);
		}else{
			
			List<EconTagNodes> childNodes = nodes.getEconTagNodes();
			if(childNodes!=null){
				for(int cot = 0 ; cot < childNodes.size(); cot++)
				{
					BaseLineSnapShotTreeBean econNodes = new BaseLineSnapShotTreeBean();
					econNodes.setType("EconTagNode");
					findMeasureOrEconTagNode(nodes.getEconTagNodes().get(cot), econNodes, ebbs) ;
					parentNode.getChildBeans().add(econNodes);
				}
			}
			
		}
		
		
	}
	
	public String 	setBaselineSnapShotData(){
		return "";
	}

public LinkedHashMap<String,Object> setBaselineSnapShotMapData(String email)
{
	LinkedHashMap<String,Object> baseLineData = new LinkedHashMap<String,Object>();
	String modelString=null;
try
{
	RepositoryDaoI dao = new RepositoryDaoImpl();
	Metrics metrics=dao.getMetrics();
	EconomicUnit economicunit=dao.getEconomicUnit();
	List<Ebb> ebb=economicunit.getEbbs().getEbb();
	List<String> modelNameList = new ArrayList<String>();
	
	List <ModelData> modeldata=metrics.getModelData(); 
	Map<String,String> modelMap=new HashMap<String,String>();
	List<BaseLineSnapShotTreeBean> roots = new ArrayList<BaseLineSnapShotTreeBean>();
	if(modeldata.size() >0)
	{
	for(int i=0;i<modeldata.size();i++)
	{
		
		BaseLineSnapShotTreeBean newBaseLineBean = new BaseLineSnapShotTreeBean();
		newBaseLineBean.setName(modeldata.get(i).getModelName());
		modelNameList.add(modeldata.get(i).getModelName());
		newBaseLineBean.setType("Model"); 
		roots.add(newBaseLineBean);
		List<BaseLineSnapShotTreeBean> childBeans = new ArrayList<BaseLineSnapShotTreeBean>();
		newBaseLineBean.setChildBeans(childBeans);
	    //System.out.println("modelname"+modeldata.get(i).getModelName());
		EconMetrics econmetrics=modeldata.get(i).getEconMetrics();
		EventMetrics eventmetrics=modeldata.get(i).getEventMetrics();
//		List<Event> event=eventmetrics.getEvent();
		EconTagTree tree=econmetrics.getEconTagTree();
		List <EconTagNode> econnode=tree.getEconTagNode();
		Map<String,String> Child1list=new HashMap<String,String>();
		
		for(int j=0;j<econnode.size();j++)//iterating econ node from econ tree Start
		{
			
			BaseLineSnapShotTreeBean topNodes = new BaseLineSnapShotTreeBean();
			topNodes.setName(econnode.get(j).getName());
			topNodes.setValue(econnode.get(j).getValue());
			topNodes.setType("Top");
			List<BaseLineSnapShotTreeBean> topChildBeans = new ArrayList<BaseLineSnapShotTreeBean>();
			topNodes.setChildBeans(topChildBeans);
			childBeans.add(topNodes);
			//System.out.println("Child1"+econnode.get(j).getName());
			List <EconTagNodes> nodes=econnode.get(j).getEconTagNodes();
			
			
			
			Map<String,String> Child2map=new HashMap<String,String>();  
			for(int k=0;k<nodes.size();k++)//iterating Child nodes of econ node 
			{
				BaseLineSnapShotTreeBean topChildNodes = new BaseLineSnapShotTreeBean();
				findMeasureOrEconTagNode(nodes.get(k),topChildNodes,ebb);
				topChildBeans.add(topChildNodes);
			//	System.out.println("Child2"+nodes.get(k).getName());
//				//	System.out.println("inside baseline2"+nodes.get(k).getValue());
//				
//				
//				List<Measuree> measuree=nodes.get(k).getMeasuree();
//				List<EconTagNodes1> econtagnodes1=nodes.get(k).getEconTagNodes1();
//				Map<String,String> Measureelist=new HashMap<String,String>();
//				for(int l=0;l<measuree.size();l++)//Start of Child 2 measure informantion
//				{
//					//System.out.println("measure values"+measuree.get(l).getAccount());
//				//	System.out.println("measure values1"+measuree.get(l).getEbbName());
//				//	System.out.println("measure values2"+measuree.get(l).getEbbUserName());
//					if(measuree.get(l).getAccount()!=null)
//					{
//						if(measuree.get(l).getEbbName()!=null && measuree.get(l).getEbbUserName()!=null)
//						{
//					//	System.out.println("ebb vales check"+measuree.get(l).getValue());
//						List<String> paramsList=new ArrayList<String>();
//						for(int m=0;m<ebb.size();m++) //ebb checking for the mesures start
//						{
//							
//							if(ebb.get(m).getName().equalsIgnoreCase(measuree.get(l).getEbbName())&& ebb.get(m).getUserName().equalsIgnoreCase(measuree.get(l).getEbbUserName()))
//									{
//								//System.out.println("Matching");
//								Params params=ebb.get(m).getParams();
//								List<com.frontpaw.forecast.schema.Param> param=params.getParam();
//								for(int n=0;n<param.size();n++)//gettting the param values for the ebbs start
//								{
//									paramsList.add(param.get(n).getName()+"*"+param.get(n).getValue());
//									//System.out.println("param names"+n+param.get(n).getName());
//									
//								}//gettting the param values for the ebbs start end
//								
//								
//								}
//							
//							
//						}//ebb checking for the mesures start
//						String paramString=Json.stringify(Json.toJson(paramsList));
//						
//						Measureelist.put(measuree.get(l).getEbbName(), paramString);
//						}else{
//					//		System.out.println("Account"+measuree.get(l).getAccount());
//						//	System.out.println("Account value"+measuree.get(l).getValue());
//							Measureelist.put(measuree.get(l).getAccount(),measuree.get(l).getValue());
//						}//end of the ebbname and ebb username
//					}//end of the account
//					
//					
//				}//end of Child 2 measure informantion
//				String MeasureString=Json.stringify(Json.toJson(Measureelist));
//				//System.out.println("Maps1"+MeasureString);
//				Child2map.put(nodes.get(k).getName()+"*"+nodes.get(k).getValue()+"*M", MeasureString);
//				Map<String,String> econtagnodes1map=new HashMap<String,String>();
//				for(int o=0;o<econtagnodes1.size();o++)
//				{//start of the child node3 iteration
//					
//					List<Measuree> measuree1=econtagnodes1.get(o).getMeasuree();
//					Map<String,String> Measuree1list=new HashMap<String,String>();
//					for(int p=0;p<measuree1.size();p++)//Start of Child 2 measure informantion
//					{
//						//System.out.println("measure values"+measuree1.get(p).getAccount());
//						//System.out.println("measure values1"+measuree1.get(p).getEbbName());
//						//System.out.println("measure values2"+measuree1.get(p).getEbbUserName());
//						if(measuree1.get(p).getAccount()!=null)
//						{
//							if(measuree1.get(p).getEbbName()!=null && measuree1.get(p).getEbbUserName()!=null)
//							{
//							//System.out.println("ebb vales check"+measuree1.get(p).getValue());
//							List<String> params1List=new ArrayList<String>();
//							for(int q=0;q<ebb.size();q++) //ebb checking for the mesures start
//							{
//								
//								if(ebb.get(q).getName().equalsIgnoreCase(measuree1.get(p).getEbbName())&& ebb.get(q).getUserName().equalsIgnoreCase(measuree1.get(p).getEbbUserName()))
//										{
//									//System.out.println("Matching");
//									Params params1=ebb.get(q).getParams();
//									List<com.frontpaw.forecast.schema.Param> param1=params1.getParam();
//									for(int r=0;r<param1.size();r++)//gettting the param values for the ebbs start
//									{
//								//		System.out.println("param names"+r+param1.get(r).getName());
//										params1List.add(param1.get(r).getName()+"*"+param1.get(r).getValue());
//									}//gettting the param values for the ebbs start end
//									
//									
//									}
//								
//								
//							}//ebb checking for the mesures start
//							String param1String=Json.stringify(Json.toJson(params1List));
//							//System.out.println("maps value"+param1String);
//							Measuree1list.put(measuree1.get(p).getEbbName(), param1String);
//							}else{
//								//System.out.println("Account1"+measuree1.get(p).getAccount());
//								//System.out.println("Account value1"+measuree1.get(p).getValue());
//								Measuree1list.put(measuree1.get(p).getAccount(),measuree1.get(p).getValue());	
//							}//end of the ebbname and ebb username
//						}//end of the account
//						
//						
//					}//end of Child 3 measure informantion
//					
//					
//				String measure1String=Json.stringify(Json.toJson(Measuree1list));	
//				econtagnodes1map.put(econtagnodes1.get(o).getName()+"*"+econtagnodes1.get(o).getValue(), measure1String);
//				}//end of the child node3 iteration
//				String econtagnodes1String=Json.stringify(Json.toJson(econtagnodes1map));
//				Child2map.put(nodes.get(k).getName()+"*"+nodes.get(k).getValue()+"*CM", econtagnodes1String);
//				
//			}//end of the econnode Child2 -----------iterating Child nodes of econ node
//			String Childnode2String=Json.stringify(Json.toJson(Child2map));
//			Child1list.put(econnode.get(j).getName()+"*"+econnode.get(j).getValue(), Childnode2String);
//		}////iterating econ node from econ tree Start-------end of the econnode Child1
//		String Childnode1String=Json.stringify(Json.toJson(Child1list));
//		//System.out.println("maps value"+Childnode1String);
//		modelMap.put(modeldata.get(i).getModelName(),Childnode1String);
//	}//end of the Model data
//	 modelString=Json.stringify(Json.toJson(modelMap));
			}
		}
	}
	}
	//System.out.println("baseline ="+roots);
	modelString=Json.stringify(Json.toJson(roots));
	baseLineData.put("modelnameList",modelNameList);
	baseLineData.put("baseLine",modelString);
	
}catch(Exception e)
{
	System.out.println("excepton");
	e.printStackTrace();
}
return baseLineData;
}

public static void main(String[] args){
	
	DataServiceImpl imple = new DataServiceImpl();
	imple.setBaselineSnapShotData();
	
}

}
