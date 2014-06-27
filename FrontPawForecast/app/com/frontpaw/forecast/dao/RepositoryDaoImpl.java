/**
 * 
 */
package com.frontpaw.forecast.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.frontpaw.forecast.common.util.CommonUtils;
import com.frontpaw.forecast.model.EventData;
import com.frontpaw.forecast.model.PerpetualEventData;
import com.frontpaw.forecast.model.StrategyData;
import com.frontpaw.forecast.schema.Dates;
import com.frontpaw.forecast.schema.EbbUserSetParamEvent;
import com.frontpaw.forecast.schema.EconomicUnit;
import com.frontpaw.forecast.schema.Event;
import com.frontpaw.forecast.schema.Events;
import com.frontpaw.forecast.schema.Forecast;
import com.frontpaw.forecast.schema.GeneralEvent;
import com.frontpaw.forecast.schema.GeneralStrategy;
import com.frontpaw.forecast.schema.Measure;
import com.frontpaw.forecast.schema.Metrics;
import com.frontpaw.forecast.schema.Model;
import com.frontpaw.forecast.schema.ModelData;
import com.frontpaw.forecast.schema.Models;
import com.frontpaw.forecast.schema.Param;
import com.frontpaw.forecast.schema.Params;
import com.frontpaw.forecast.schema.Periods;
import com.frontpaw.forecast.schema.Statement;
import com.frontpaw.forecast.schema.Strategies;
import com.frontpaw.forecast.schema.Strategy;
import com.frontpaw.forecast.schema.Values;
import com.frontpaw.forecast.service.ForecastRestService;
import com.frontpaw.forecast.util.ApplicationConstants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


// TODO: Auto-generated Javadoc
/**
 * The Class RepositoryDaoImpl.
 *
 * @author Administrator
 */
public class RepositoryDaoImpl implements RepositoryDaoI {
	
	/** The forecast. */
	private static Forecast forecast =  new Forecast();
	
	private static Map<String, Date> datesMap = new LinkedHashMap<String, Date>();
	
	private static void decompressGzipFile(String gzipFile, String newFile) {
		try{
			
			FileInputStream fis = new FileInputStream(gzipFile);
			GZIPInputStream gis = new GZIPInputStream(fis);
			FileOutputStream fos = new FileOutputStream(newFile);
			byte[] buffer = new byte[1024];
			int len;
			while((len = gis.read(buffer)) != -1){
				fos.write(buffer, 0, len);
			}
			fos.close();
			gis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * 
	 * Returns the list of Statements from each model XML
	 * @see com.frontpaw.forecast.dao.RepositoryDaoI#readDefaultXml(java.lang.String)
	 */
	@Override
	public List<List<Statement>> readDefaultXml(String fileName,String email,InputStream stream,ArrayList<String> forecastName) {
		//ClassLoader.getSystemClassLoader();
		// TODO Auto-generated method stub
		List<Statement> statements=new ArrayList<Statement>();
		List<List<Statement>> allStatements=new ArrayList<List<Statement>>();
		     
			try {
				 /* ForecastRestService FCservice = new ForecastRestService();
				  String forecastResp=FCservice.getUserForecast(email);
				  InputStream stream = new ByteArrayInputStream(forecastResp.getBytes("UTF-8"));*/

				/*String gzipFile = "E:/play/project/27-06-2013/FrontPawForecast/newforecast14102013.xml.gz";
		
				String newFile = "E:/play/project/27-06-2013/FrontPawForecast/newforecast14102013gz1.xml";
				decompressGzipFile(gzipFile,newFile);*/
				
				//InputStream stream =  play.Play.application().resourceAsStream("newforecast14102013gz1.xml");
				//InputStream stream = ClassLoader.getSystemResourceAsStream("E:/play/project/27-06-2013/FrontPawForecast/newforecast14102013gz1.xml");
				//InputStream stream = new FileInputStream(newFile);
				
				
				
			    if(stream==null){
			    	System.out.println("File Not read!!!");
			    }
			     JAXBContext jaxbContext;
			     /*ObjectNode result = Json.newObject();
			     Financials financials=new Financials();*/
			     jaxbContext = JAXBContext.newInstance(com.frontpaw.forecast.schema.Forecast.class);
			     Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			     forecast = (Forecast) jaxbUnmarshaller.unmarshal(stream);
			    // session.put("forecastname", forecast.getDescription());
			     forecastName.add(forecast.getDescription());
			     List<ModelData> modelData=forecast.getMetrics().getModelData();
			    // System.out.println("*************modelData************: "+modelData);
			     for(ModelData md:modelData){
			    	 statements=md.getEconMetrics().getFinancials().getStatement();
			    	 allStatements.add(statements);
			     }
				getdates();
				
				
		} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		/*catch (java.io.UnsupportedEncodingException eus) {
				// TODO Auto-generated catch block
				eus.printStackTrace();
		}*/
		return allStatements;
	}

	/* (non-Javadoc)
	 * @see com.frontpaw.forecast.dao.RepositoryDaoI#getdates()
	 * 
	 * Returns the list of dates in each model XML
	 */
	@Override
	public Dates getdates() {
		Dates dates=null;
		dates=forecast.getDates();
		try {
			datesMap = CommonUtils.convertedDateMap(dates);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dates;
	}
	
	
	Set<String> keyMesures = new LinkedHashSet<String>();
	
	/* (non-Javadoc)
	 * @see com.frontpaw.forecast.dao.RepositoryDaoI#getMeasuresData(java.lang.String)
	 */
	/*public Map<String,String> getMeasuresData(String modelName)
	{
		
		Map<String,String> keyMeasuresData = new LinkedHashMap<String, String>();
		ModelData model =  getMetricModel(modelName);
		
		List<Statement> statesments = model.getEconMetrics().getFinancials().getStatement();
		if (statesments != null) {
			for (Statement statement : statesments) {
				List<Measure> mes = statement.getMeasure();
				if (mes != null) {
					String[] keys = ApplicationConstants.COMPARSION_KEY_MEASURES
							.split(",");
					for (String key : keys) {
						Measure measure = new Measure();
						measure.setName(key);
						int index = mes.indexOf(measure);
						if (index != -1) {
							keyMesures.add(key);
							Values values = mes.get(index).getValues();
							List<String> vals = values.getValue();
							int valIndex = vals.size();
							keyMeasuresData.put(key, vals.get(valIndex - 1));

						}
					}
				}

			}
		}
		
		return keyMeasuresData;
		
	}*/
	
	public Map<String,String> getMeasuresData(String modelName)
	{
		
		Map<String,String> keyMeasuresData = new LinkedHashMap<String, String>();
		ModelData model =  getMetricModel(modelName);
		
		List<Statement> statesments = model.getEconMetrics().getFinancials().getStatement();
		if (statesments != null) {
			for (Statement statement : statesments) {
				List<Measure> mes = statement.getMeasure();
				if (mes != null) {
					String[] keys = ApplicationConstants.COMPARSION_KEY_MEASURES.split(",");
					for (String key : keys) {
						Measure measure = new Measure();
							measure.setName(key);
							int index = mes.indexOf(measure);
							if (index != -1) {
								keyMesures.add(key);
								Values values = mes.get(index).getValues();
								List<String> vals = values.getValue();
								int valIndex = vals.size();
								keyMeasuresData.put(key, vals.get(valIndex - 1));
							}
					}
					for(Measure measure1 : mes){
							//System.out.println("ebbName " + measure1.getEbbName());
							if(measure1.getEbbName()!=null && measure1.getEbbName().trim().equals("Cash")){
								keyMesures.add("Cash");
								//System.out.println("ebbName inside if " + measure1.getEbbName());
								Values values1  = measure1.getValues();
								List<String> vals1 = values1.getValue();
								//System.out.println("ebbName vals size " + vals1.size());
								int valIndex1 = vals1.size();
								keyMeasuresData.put("Cash", vals1.get(valIndex1 - 1));
								//System.out.println("ebbName vals cash value " + keyMeasuresData.get("Cash"));
							}
							if(measure1.getEbbName()!=null && measure1.getEbbName().trim().equals("Net Worth")){
								keyMesures.add("Cash");
								//System.out.println("ebbName inside if " + measure1.getEbbName());
								Values values1  = measure1.getValues();
								List<String> vals1 = values1.getValue();
								//System.out.println("ebbName vals size " + vals1.size());
								int valIndex1 = vals1.size();
								keyMeasuresData.put("Cash", vals1.get(valIndex1 - 1));
								//System.out.println("ebbName vals cash value " + keyMeasuresData.get("Net Worth"));
							}							

						
					}
					

					
				}

			}
		}
		//System.out.println("ebbName vals cash value " + keyMeasuresData.get("Cash"));

		return keyMeasuresData;
		
	}
	
	
	
	
	
	
	public Set<String> getKeyMeasures(){
		
		return keyMesures;
	}
	
	


	
	
	/**
	 * Gets the event metric events.
	 *
	 * @return the event metric events
	 */
	public List<List<Event>> getEventMetricEvents(){
		
		List<List<Event>> allEvents=new ArrayList<List<Event>>();
		List<ModelData> modelData=forecast.getMetrics().getModelData();
	     for(ModelData md:modelData){
			 if(md.getEventMetrics()!=null){
				List<Event> events=md.getEventMetrics().getEvent();
				allEvents.add(events);
			 }
	     }
		return allEvents;
		
		
	}
	
	public Models getForcastModels(){
		return forecast.getModels();
		
	}
public EconomicUnit getEconomicUnit()
{
	return forecast.getEconomicUnit();
}
	public Metrics getMetrics()
	{
		return forecast.getMetrics();
	}
	
	/**
	 * Gets the event metric events.
	 *
	 * @return the event metric events
	 */
	/**
	 * @param modelName
	 * @return
	 */
	public List<Event> getEventMetricEvents (String modelName){
		List<Event> eventMetricEvent = new ArrayList<Event>();
		ModelData modelNameModel = new ModelData();
		modelNameModel.setModelName(modelName);
		List<ModelData> modelDatas= forecast.getMetrics().getModelData() ;
		int modelIndex = modelDatas.indexOf(modelNameModel);
		if(modelIndex>-1 && modelDatas.get(modelIndex).getEventMetrics()!=null && modelDatas.get(modelIndex).getEventMetrics().getEvent()!=null)
		eventMetricEvent.addAll(modelDatas.get(modelIndex).getEventMetrics().getEvent());
		return eventMetricEvent;
		
		
	}
	
	
	/**
	 * Gets the model events.
	 *
	 * @return the model events
	 */
	public Events getModelEvents(String modelName){
		Events events = new Events();
			events = getModel(modelName).getEvents();
			//events.
		return events;
	}
	
	
	
	/**
	 * @return
	 */
	public List<Model> getModels(){
		return forecast.getModels().getModel();
	}
	/**
	 * @return
	 */
	public Model getModel(String modelName){
		Model modelNameModel = new Model();
		
		modelNameModel.setName(modelName);
		List<Model> modelList = forecast.getModels().getModel();
		int modelIndex = modelList.indexOf(modelNameModel);
		if(modelIndex > -1){
			modelNameModel = modelList.get(modelIndex);
		}
		return modelNameModel;
	}
	
	/**
	 * @return
	 */
	public ModelData getMetricModel(String modelName){
		ModelData modelNameModel = new ModelData();
		modelNameModel.setModelName(modelName);
		List<ModelData> modelDatas= forecast.getMetrics().getModelData() ;
		int modelIndex = modelDatas.indexOf(modelNameModel);
		if(modelIndex>-1)
			modelNameModel = modelDatas.get(modelIndex);
		
		return modelNameModel;
	}
	
	/**
	 * @return
	 */
	public List<ModelData> getMetricModels(){
		return forecast.getMetrics().getModelData();
	}
	
	/**
	 * Gets the model events.
	 *
	 * @return the model events
	 */
	public List<Events> getModelEvents(){
		
		List<Events> allEvents=new ArrayList<Events>();
		List<Model> models=forecast.getModels().getModel();
	     for(Model md:models){
	    	 Events events=md.getEvents();
	    	 allEvents.add(events);
	     }
		return allEvents;
	}
	
	
	/**
	 * Creates the events data.
	 *
	 * @return the list
	 */
	public Map<Integer,Object> createDateEventsData(){
		Map<Integer,Object> alleventData=new HashMap<Integer, Object>();
		List<List<Event>> allEventMetrics = getEventMetricEvents();
		List<Events> allModelEvents = getModelEvents();
		int eindex=0;
		getdates();
		for(List<Event> events:allEventMetrics){	
			List<EventData> eventData = new ArrayList<EventData>();
			for(Event event:events){
				String eventType = event.getEventType();
				//event.getDates().
				System.out.println("Event data1 .........."+event.getDates().getDate());	
				createEventDataList(eventData, allModelEvents.get(eindex), event, eventType);
			}
		alleventData.put(eindex,eventData);
		++eindex;
		}
		return alleventData;
		
	}
	
	
	
	
	
	
	/**
	 * Creates the events data.
	 *
	 * @return the list
	 */
	public Map<Integer,Object> createEventsData(){
		Map<Integer,Object> alleventData=new HashMap<Integer, Object>();
		List<List<Event>> allEventMetrics = getEventMetricEvents();
		List<Events> allModelEvents = getModelEvents();
		int eindex=0;
		for(List<Event> events:allEventMetrics){	
			List<EventData> eventData = new ArrayList<EventData>();
			for(Event event:events){
				String eventType = event.getEventType();
				//event.
				createEventDataList(eventData, allModelEvents.get(eindex), event, eventType);
			}
		alleventData.put(eindex,eventData);
		++eindex;
		}
		return alleventData;
		
	}
	
	
	
	
	
	
	/**
	 * Creates the events data.
	 *
	 * @return the list
	 */
	public List<EventData> createEventsData(String modelName){
		
		List<EventData> data = new ArrayList<EventData>();
		List<Event> events = getEventMetricEvents(modelName);
		Events even = getModelEvents(modelName);
		for(Event event:events){
			String eventType = event.getEventType();
			System.out.println("Event data1"+eventType);	
			createEventDataList(data, even, event, eventType);
			//createEventDataList(data,event.getUserName(), event.getEventType(), event.getDescription(), event.getDates().getDate());//Modified as part of new XML - 07/24
		}
		return data;
	}

	/**
	 * General event needs to verified as param value is harded code as one parameter
	 * @param data
	 * @param even
	 * @param event
	 * @param eventType
	 */
	
	
	private void createEventDataList(List<EventData> eventData, Events even,Event event, String eventType) {
		if(eventType!=null && eventType.equals("EbbUserSetParamEvent") || eventType.equals("EbbEvent")){
		List<EbbUserSetParamEvent> userEvents = even.getEbbUserSetParamEvent();
		 for(EbbUserSetParamEvent usparamEvent:userEvents){
			 if(usparamEvent!=null && usparamEvent.getUserName().equals(event.getUserName())){
				 EventData eveData = new EventData();
				 eveData.setEbbUserName(usparamEvent.getEbbUserName());
				 //System.out.println("event.getDates().getDate()" + event.getDates().getDate());
				 eveData.setEventDate(convertDatesToReportDateFormat(event.getDates().getDate()));
				 eveData.setEventType(eventType);
				 //System.out.println(usparamEvent.getEbbUserName() + "========="+usparamEvent.getEbbName());
				 eveData.setEbbName(usparamEvent.getEbbName());
				 eveData.setEventValue(usparamEvent.getValue());
				 eveData.setUserName(usparamEvent.getUserName());
				 eveData.setEbbUserSetParamName(usparamEvent.getEbbUserSetParamName());
				 eveData.setEventName(usparamEvent.getName());
				 eventData.add(eveData);
			 }
		 }
		}else{
			GeneralEvent userEvents = even.getGeneralEvent();
				 if(userEvents!=null && userEvents.getUserName().equals(event.getUserName())){
					 EventData eveData = new EventData();
					 eveData.setEbbUserName(userEvents.getUserName());
					 eveData.setEventDate(convertDatesToReportDateFormat(event.getDates().getDate()));
					 eveData.setEventType(eventType);
					 eveData.setEventName(userEvents.getUserName());
					 eveData.setParams(userEvents.getParams());
					 //userEvents.getParams().getParam();
					 eveData.setEventName(userEvents.getName());
					 eveData.setUserName(userEvents.getUserName());
					// System.out.println("*************Params Size********: "+userEvents.getParams().toString());
					 eventData.add(eveData);
				 }
		}
	}
	
	/***********Methods for strategic events******************************/
	
	/**
	 * Gets the event metric events.
	 *
	 * @return the event metric events
	 */
	
	public List<List<Strategy>> getStrategiesMetricsEvents(){
		
		List<List<Strategy>> allStrategies=new ArrayList<List<Strategy>>();
		List<ModelData> modelData=forecast.getMetrics().getModelData();
	     for(ModelData md:modelData){
			 if(md.getStrategyMetrics()!=null){
				List<Strategy> strategies=md.getStrategyMetrics().getStrategy();
				allStrategies.add(strategies);
			}	
	     }
		return allStrategies;
		
	}
	
	/**
	 * Gets the event metric events.
	 *
	 * @return the event metric events
	 */
	public List<Strategy> getStrategiesMetricsEvents(String modelName){
		
		
		return getMetricModel(modelName).getStrategyMetrics().getStrategy();
		
		
	}
	
	/**
	 * Gets the model events.
	 *
	 * @return the model events
	 */
	public List<Strategies> getModelStrategies(){
		
		List<Strategies> allStrategies=new ArrayList<Strategies>();
		List<Model> models=forecast.getModels().getModel();
	     for(Model md:models){
	    	 Strategies strategies=md.getStrategies();
	    	 allStrategies.add(strategies);
	     }
		return allStrategies;
	}
	
	/**
	 * Gets the model events.
	 *
	 * @return the model events
	 */
	public Strategies getModelStrategies(String modelName){
		//System.out.println("modelStrategies ..........." + modelName);
		
		return getModel(modelName).getStrategies();
	}
	
	
	/**
	 * Creates the events data.
	 *
	 * @return the list
	 */
	public Map<Integer,Object> createStrategiesData(){
		
		Map<Integer,Object> allstrategyData=new HashMap<Integer, Object>();
		List<List<Strategy>> allStrategies = getStrategiesMetricsEvents();
		List<Strategies> allModelStrategies = getModelStrategies();
		int sindex=0;
		for(List<Strategy> strategies:allStrategies){
			List<StrategyData> strategyData = new ArrayList<StrategyData>();
			for(Strategy strategy:strategies){
				String strategyName = strategy.getUserName();			
				createStrategyEventDataList(strategyData, allModelStrategies.get(sindex), strategy, strategyName);
			}
			allstrategyData.put(sindex,strategyData);
			++sindex;
		}
		return allstrategyData;
	}

	/**
	 * General event needs to verified as param value is harded code as one parameter
	 * @param data
	 * @param even
	 * @param event
	 * @param eventType
	 */
	private void createStrategyEventDataList(List<StrategyData> data, Strategies modelStrat,
			Strategy strategy, String strategyName) {
		
		List<GeneralStrategy> genStrats = modelStrat.getGeneralStrategy();
		 for(GeneralStrategy genStrat:genStrats){
			 if(genStrat.getUserName().equalsIgnoreCase(strategyName)){
			 if(genStrat.getStrategyTiming().getOneTime()==null){
				 StrategyData strategyData = new StrategyData();
				 strategyData.setUserName(genStrat.getUserName());
				 strategyData.setName(genStrat.getName());
				 strategyData.setStrategyDate(convertDatesToReportDateFormat(strategy.getDates().getDate()));
				 strategyData.setParams(genStrat.getParams());				 
				 data.add(strategyData);
			 }
		 }
		}
	}
	
	
	public List<Date> convertDatesToReportDateFormat(List<String> xmlDates){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		TimeZone zone =  TimeZone.getTimeZone("UTC");
		format.setTimeZone(zone);
		List<Date> dates = new ArrayList<Date>();
		for(String date:xmlDates){
			dates.add(datesMap.get(date));
		}
		return dates;
		
		
	}
	
	public List<String> getmodelname() {
		// TODO Auto-generated method stub
		List<String> modelNames=new ArrayList<String>();
		List<Model> models=forecast.getModels().getModel();
	     for(Model md:models){
	    	 String modelName=md.getName();
	    	 modelNames.add(modelName);
	     }
		
		return modelNames;
	}
	
	public List<StrategyData> createStrategiesDataNonPerpetual(String modelName){
		
		List<StrategyData> data = new ArrayList<StrategyData>();
		List<Strategy> strategies = getStrategiesMetricsEvents(modelName);
		Strategies modelStrat = getModelStrategies(modelName);
		for(Strategy strategy:strategies){
			String strategyName = strategy.getUserName();
			
			createStrategyEventDataListNonPertual(data, modelStrat, strategy, strategyName);
		}
		return data;
	}
	
	/**
	 * Non perptual events data
	 * @param data
	 * @param modelStrat
	 * @param strategy
	 * @param strategyName
	 */
	private void createStrategyEventDataListNonPertual(List<StrategyData> data, Strategies modelStrat,
			Strategy strategy, String strategyName) {
		
		List<GeneralStrategy> genStrats = modelStrat.getGeneralStrategy();
		 for(GeneralStrategy genStrat:genStrats){
			 if(genStrat.getUserName().equalsIgnoreCase(strategyName)){
			 if(genStrat.getStrategyTiming().getOneTime()!=null){
				 StrategyData strategyData = new StrategyData();
				 strategyData.setUserName(genStrat.getUserName());
				 strategyData.setStrategyName(genStrat.getName());
				 strategyData.setStrategyDate(convertDatesToReportDateFormat(strategy.getDates().getDate()));
				 strategyData.setParams(genStrat.getParams());
				 
				 data.add(strategyData);
			 }
		 }
		}
	}
	
	

	int perpetualHeightCount = 0;
	
	/******************************End of Strategic events methods*****************************/
	/*public List<PerpetualEventData> createPerpetualEvents(String modelName){
		int tempCount = 0 ;
		boolean first = false;
		if(tempCount == perpetualHeightCount){
			first = true;
		}
		
		List<PerpetualEventData> pEventdata = new ArrayList<PerpetualEventData>();
		Events modelEvents = getModelEvents(modelName);
		List<EbbUserSetParamEvent> userEvents = modelEvents.getEbbUserSetParamEvent();
		GeneralEvent generalEvents = modelEvents.getGeneralEvent();
		Strategies modelStrat = getModelStrategies(modelName);
		List<GeneralStrategy> genStrats = modelStrat.getGeneralStrategy();
		for(EbbUserSetParamEvent ebbUserEvent:userEvents){
			if(ebbUserEvent.getEventTiming().getOneTime()==null){
			PerpetualEventData perpetualData = new PerpetualEventData();
			perpetualData.setName(ebbUserEvent.getName());
			perpetualData.setUserName(ebbUserEvent.getUserName());
			perpetualData.setEbbUserParamName(ebbUserEvent.getEbbUserSetParamName());
			perpetualData.setEbbUserParamValue(ebbUserEvent.getValue());
			perpetualData.setEventTiming(ebbUserEvent.getEventTiming());
			pEventdata.add(perpetualData);
			
			}		
			 
		}
		
		if(generalEvents !=null && generalEvents.getEventTiming().getOneTime()==null){
			PerpetualEventData perpetualData = new PerpetualEventData();
			perpetualData.setName(generalEvents.getName());
			perpetualData.setUserName(generalEvents.getUserName());
			perpetualData.setParams(generalEvents.getParams());
			perpetualData.setEventTiming(generalEvents.getEventTiming());
			pEventdata.add(perpetualData);
			if(perpetualData.getParams()!=null && perpetualData.getParams().getParam()!=null)
				tempCount = tempCount+perpetualData.getParams().getParam().size();
		}
			
		for(GeneralStrategy genStrat:genStrats){
			if(genStrat.getStrategyTiming().getOneTime()==null){
			PerpetualEventData perpetualData = new PerpetualEventData();
			perpetualData.setName(genStrat.getName());
			perpetualData.setUserName(genStrat.getUserName());
			perpetualData.setParams(genStrat.getParams());
			perpetualData.setStrategyTiming(genStrat.getStrategyTiming());
			pEventdata.add(perpetualData);
			if(perpetualData.getParams()!=null && perpetualData.getParams().getParam()!=null)
				tempCount = tempCount+perpetualData.getParams().getParam().size();
			}		
			 
		}
		tempCount = tempCount+pEventdata.size();
		
		if(first){
			perpetualHeightCount = tempCount;
		}else{
			if(tempCount > perpetualHeightCount){
				perpetualHeightCount = tempCount;
			}
		}
		return pEventdata;
	}*/
	
	
	/*public List<List<String>> createPerpetualEvents(String modelName){
	int tempCount = 0 ;
	boolean first = false;
	List<List<String>> perpetualEventData = new ArrayList<List<String>>();
	
	List<PerpetualEventData> pEventdata = new ArrayList<PerpetualEventData>();
	Events modelEvents = getModelEvents(modelName);
	List<EbbUserSetParamEvent> userEvents = modelEvents.getEbbUserSetParamEvent();
	//System.out.println("userEvents data " + userEvents.size());
	GeneralEvent generalEvents = modelEvents.getGeneralEvent();
	Strategies modelStrat = getModelStrategies(modelName);
	List<GeneralStrategy> genStrats = modelStrat.getGeneralStrategy();
	for(EbbUserSetParamEvent ebbUserEvent:userEvents){
		//System.out.println("inside for ............" + ebbUserEvent.getEventTiming().getOneTime().getFixedPeriod());
		if(ebbUserEvent.getEventTiming().getOneTime().getFixedPeriod()==null  ){
			//System.out.println("inside for if ............" + ebbUserEvent.getEventTiming().getOneTime().getFixedPeriod());
		List<String> perpetualRowvalues = new ArrayList<String>();	
		perpetualRowvalues.add(ebbUserEvent.getName());
		perpetualRowvalues.add(ebbUserEvent.getUserName());
		perpetualRowvalues.add(ebbUserEvent.getEbbUserSetParamName() + ":" + ebbUserEvent.getValue());
		//perpetualRowvalues.setEventTiming(ebbUserEvent.getEventTiming());
		//System.out.println("perpetual Events " + ebbUserEvent.getName() + " " + ebbUserEvent.getUserName());
		perpetualEventData.add(perpetualRowvalues);
		}		
		 
	}
	
	if(generalEvents !=null && generalEvents.getEventTiming().getOneTime()==null){
		List<String> perpetualRowvalues = new ArrayList<String>();	
		perpetualRowvalues.add(generalEvents.getName());
		perpetualRowvalues.add(generalEvents.getUserName());
		Params pmsList = generalEvents.getParams();
		List<Param> pmList = pmsList.getParam();
		for(Param param : pmList )
			perpetualRowvalues.add(param.getName() + " : " + param.getValue());
		//perpetualData.setEventTiming(generalEvents.getEventTiming());
			//System.out.println("perpetual Events " + generalEvents.getName() + " " + generalEvents.getUserName());
				
			perpetualEventData.add(perpetualRowvalues);
	}
		
	for(GeneralStrategy genStrat:genStrats){
		if(genStrat.getStrategyTiming().getOneTime()==null){
			List<String> perpetualRowvalues = new ArrayList<String>();	
			perpetualRowvalues.add(genStrat.getName());
			perpetualRowvalues.add(genStrat.getUserName());
			Params pmsList = genStrat.getParams();
			List<Param> pmList = pmsList.getParam();
			for(Param param : pmList )
				perpetualRowvalues.add(param.getName() + " : " + param.getValue());
		}	
		 
	}
	
	return perpetualEventData;
	}*/
	
	
	
	public List<List<String>> createPerpetualEvents(String modelName){
		//System.out.println("createPerpetualEvents.................... ");
		Dates forecastTotalDates = getdates();
		Periods forecastTotalPeriods = forecast.getPeriods();
		List<List<String>> perpetualEventData = new ArrayList<List<String>>();
		List<Event> eventMetricsList = getEventMetricEvents(modelName);
		List<Strategy> strategyMetricsList = getStrategiesMetricsEvents(modelName);
		//System.out.println("eventMetricsList size " + eventMetricsList.size());
		Events modelEvents = getModelEvents(modelName);
		
		List<EbbUserSetParamEvent> userEvents = modelEvents.getEbbUserSetParamEvent();
		GeneralEvent generalEvents = modelEvents.getGeneralEvent();
		Strategies modelStrat = getModelStrategies(modelName);
		List<GeneralStrategy> genStrats = modelStrat.getGeneralStrategy();

		for(Event event : eventMetricsList){
			Periods periods = event.getPeriods();
			Dates dates = event.getDates();

			if((periods!=null && periods.getPeriod().size()==forecastTotalPeriods.getPeriod().size()) ||(dates!=null && dates.getDate().size()==forecastTotalDates.getDate().size())){
				for(EbbUserSetParamEvent ebbUserEvent:userEvents){
					if(ebbUserEvent.getUserName().equalsIgnoreCase(event.getUserName())){
						List<String> perpetualRowvalues = new ArrayList<String>();	
						if(!ebbUserEvent.getName().isEmpty() && ebbUserEvent.getName().length()>0)
						perpetualRowvalues.add(ebbUserEvent.getName());
						else
						perpetualRowvalues.add(ebbUserEvent.getUserName());
						//perpetualRowvalues.add(ebbUserEvent.getEbbUserSetParamName());
						perpetualRowvalues.add(ebbUserEvent.getEbbUserSetParamName() + ":" + ebbUserEvent.getValue());
						perpetualEventData.add(perpetualRowvalues);
						break;
					}
				}
				if(generalEvents !=null && generalEvents.getUserName().equalsIgnoreCase(event.getUserName())){
					List<String> perpetualRowvalues = new ArrayList<String>();
					if(!generalEvents.getName().isEmpty() && generalEvents.getName().length()>0)
					perpetualRowvalues.add(generalEvents.getName());
					else
					perpetualRowvalues.add(generalEvents.getUserName());

					Params pmsList = generalEvents.getParams();
					List<Param> pmList = pmsList.getParam();
					for(Param param : pmList )
						perpetualRowvalues.add(param.getName() + " : " + param.getValue());
						perpetualEventData.add(perpetualRowvalues);

				}

			}
			
		}
		for(Strategy strategy : strategyMetricsList){
			Periods periods = strategy.getPeriods();
			Dates dates = strategy.getDates();
			for(GeneralStrategy genStrat:genStrats){
				if(genStrat.getUserName().equalsIgnoreCase(strategy.getUserName())){
					List<String> perpetualRowvalues = new ArrayList<String>();	
					if(!genStrat.getName().isEmpty() && genStrat.getName().length()>0)
						perpetualRowvalues.add(genStrat.getName());
					else 
						perpetualRowvalues.add(genStrat.getUserName());
					
					Params pmsList = genStrat.getParams();
					List<Param> pmList = pmsList.getParam();
					for(Param param : pmList )
						perpetualRowvalues.add(param.getName() + " : " + param.getValue());
					perpetualEventData.add(perpetualRowvalues);
					break;	
				}	
			 
			}				
			
		}	
		
		return perpetualEventData;
	}
	
	
	
	private Map<String,Integer> datesMapsLength = new LinkedHashMap<String, Integer>();
	/**
	 * Creates the compare event dataes.
	 *
	 * @param modelname the modelname
	 * @return the map
	 */
	
	/*public Map<String,Map<String,List<EventData>>> createCompareEventDataes(String modelname){
		List<EventData> evntData  =  createEventsData(modelname);
		List<StrategyData> stragNonData = createStrategiesDataNonPerpetual(modelname);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		TimeZone zone =  TimeZone.getTimeZone("UTC");
		format.setTimeZone(zone);
		
		Dates dates = getdates();
		Map<String,Map<String,List<EventData>>> compareEventsDataMap= new LinkedHashMap<String, Map<String,List<EventData>>>();
		Map<String,List<EventData>> datesMap = new LinkedHashMap<String, List<EventData>>();
		int count = 0;
		for(String date:dates.getDate()){
			count = 0;
			List<EventData> neededEventData= new ArrayList<EventData>();
			for(EventData evenData:evntData){
				//System.out.println("date="+evenData.getEventDate().get(0));
				if(date.equalsIgnoreCase(format.format(evenData.getEventDate().get(0)))){
					if(evenData.getParams()!=null && evenData.getParams().getParam()!=null){
						count = count + evenData.getParams().getParam().size();
					}
					if(evenData.getEventType()!=null && evenData.getEventType().equals("EbbUserSetParamEvent"))
						count = count+4;
					else{
						count = count+1;
					}
					
				neededEventData.add(evenData);
				}
			}
			for(StrategyData statData:stragNonData){
				if(date.equals(format.format(statData.getStrategyDate().get(0)))){
					EventData evenData= new EventData();
					evenData.setName(statData.getStrategyName());
					evenData.setUserName(statData.getUserName());
					evenData.setEventDate(statData.getStrategyDate());
					evenData.setParams(statData.getParams());
					evenData.setEventValue(statData.getStrategyValue());
					
					if(evenData.getParams()!=null && evenData.getParams().getParam()!=null){
					count = count + evenData.getParams().getParam().size();
					
					}
				neededEventData.add(evenData);
				}
			}
			
			datesMap.put(date, neededEventData);
			count = count+neededEventData.size();
			//System.out.println("date = "+date+" modelname ="+modelname+" count="+count);
			if(!datesMapsLength.containsKey(date)){
				
			datesMapsLength.put(date, count);
			}
			else
				if(datesMapsLength.get(date)<count){
					datesMapsLength.put(date, count);	
				}
				
			
		}
		compareEventsDataMap.put(modelname, datesMap);
		
		
		
		
		
	return compareEventsDataMap;	
	}*/
	
	
	
	
	/*public Map<String,Map<String,List<List<String>>>> createCompareEventDataes(String modelname){
	List<EventData> evntData  =  createEventsData(modelname);
	List<StrategyData> stragNonData = createStrategiesDataNonPerpetual(modelname);
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
	TimeZone zone =  TimeZone.getTimeZone("UTC");
	format.setTimeZone(zone);
	
	Dates dates = getdates();
	Map<String,Map<String,List<List<String>>>> compareEventsDataMap= new LinkedHashMap<String, Map<String,List<List<String>>>>();
	Map<String,List<List<String>>> datesMap = new LinkedHashMap<String, List<List<String>>>();
	int count = 0;
	for(String date:dates.getDate()){
		boolean check = false;
		List<List<String>> neededEventData= new ArrayList<List<String>>();
		for(EventData evenData:evntData){
			if(date.equalsIgnoreCase(format.format(evenData.getEventDate().get(0)))){
				//System.out.println("date="+format.format(evenData.getEventDate().get(0)));
				check = true;
				List<String> eventList = new ArrayList<String>();
				//System.out.println(evenData.getEbbUserName() + "==="+ evenData.getName());
				eventList.add(evenData.getEbbUserName());
				eventList.add(evenData.getName());
				Params params = evenData.getParams();
				if(params!=null){
					List<Param> paramList = params.getParam();
					for(Param param : paramList){
						eventList.add(param.getName()+ " : " +param.getValue());
						//System.out.println("param name value " + param.getName() + "==="+ param.getValue());

					}
				}
				neededEventData.add(eventList);
				//datesMap.put(date, neededEventData);

			}
		}
		for(StrategyData statData:stragNonData){
			if(date.equals(format.format(statData.getStrategyDate().get(0)))){
				check = true;
				//System.out.println("getStrategyDatedate="+format.format(statData.getStrategyDate().get(0)));
				
				List<String> eventList = new ArrayList<String>();
				eventList.add(statData.getStrategyName());
				eventList.add(statData.getUserName());
				Params params = statData.getParams();
				if(params!=null){
					List<Param> paramList = params.getParam();
					for(Param param : paramList){
						eventList.add(param.getName()+ " : " +param.getValue());
					}
				}
				neededEventData.add(eventList);
			}
		}
		if(check){
			//System.out.println("dates i n check " + date);
			datesMap.put(date, neededEventData);
		}
		//count = count+neededEventData.size();
		//System.out.println("date = "+date+" modelname ="+modelname+" count="+count);
			
		
	}
	compareEventsDataMap.put(modelname, datesMap);
	
	
	
	
	
return compareEventsDataMap;	
}*/
	
	
	
	public Map<String,Map<String,List<List<String>>>> createCompareEventDataes(String modelname) throws Exception{
		List<EventData> evntData  =  createEventsData(modelname);
		System.out.println("evntData  size" + evntData.size());
		List<StrategyData> stragNonData = createStrategiesDataNonPerpetual(modelname);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		TimeZone zone =  TimeZone.getTimeZone("UTC");
		format.setTimeZone(zone);
        NumberFormat formatter = new DecimalFormat("############");
        formatter.setGroupingUsed(true);
        formatter.setMaximumFractionDigits(0);
        formatter.setMinimumFractionDigits(0);	
		
		Dates dates = getdates();
		Map<String,Map<String,List<List<String>>>> compareEventsDataMap= new LinkedHashMap<String, Map<String,List<List<String>>>>();
		Map<String,List<List<String>>> datesMap = new LinkedHashMap<String, List<List<String>>>();
		int count = 0;
		for(String date:dates.getDate()){
			//System.out.println("date inside =" + modelname + " " +date);

			boolean check = false;
			List<List<String>> neededEventData = new ArrayList<List<String>>();
			for(EventData evenData:evntData){
				System.out.println("date="+format.format(evenData.getEventDate().get(0)));

				if(date.equalsIgnoreCase(format.format(evenData.getEventDate().get(0)))){
				//System.out.println("date="+format.format(evenData.getEventDate().get(0)));
					check = true;
					List<String> eventList = new ArrayList<String>();
					//System.out.println(evenData.getEbbUserName() + "==="+ evenData.getName());
					//if(evenData.getEbbUserName().isEmpty() && evenData.getEbbUserName().length()>0)
					if(!evenData.getUserName().isEmpty() && evenData.getUserName().length()>0)
					eventList.add(evenData.getUserName());
					else 
					eventList.add(evenData.getEbbName());
					eventList.add(evenData.getEbbUserSetParamName());
					if(evenData.getEbbUserSetParamName()!=null || evenData.getEventValue()!=null){
						if(evenData.getEventName().endsWith("Percent")){
							eventList.add(evenData.getEventName()+ " : " + Math.round(formatter.parse(evenData.getEventValue()).doubleValue()));
						}else {
							eventList.add(evenData.getEventName()+ " : $" + Math.round(formatter.parse(evenData.getEventValue()).doubleValue()));
						}
						
					}	
					neededEventData.add(eventList);
					//datesMap.put(date, neededEventData);

			}
		}
		for(StrategyData statData:stragNonData){
			if(date.equals(format.format(statData.getStrategyDate().get(0)))){
				check = true;
				//System.out.println("getStrategyDatedate="+format.format(statData.getStrategyDate().get(0)));
				
				List<String> eventList = new ArrayList<String>();
				if(statData.getUserName().isEmpty() && statData.getUserName().length()>0)
					eventList.add(statData.getUserName());
				else
					eventList.add(statData.getName());
				Params params = statData.getParams();
				if(params!=null){
					List<Param> paramList = params.getParam();
					for(Param param : paramList){
						if(param.getName()!=null && param.getValue()!=null){
							eventList.add(param.getName()+ " : " +param.getValue());
						}	
					}
				}
				neededEventData.add(eventList);
			}
		}
		if(check){
				/*if(neededEventData.size()>0){
					for(int a=0;a<neededEventData.size();a++){
						//System.out.println("neededEventdata with not null values" +date);
						List<String> list = neededEventData.get(0);
						if(list!=null && list.size()>0){
							System.out.println("neededEventData list size " + date + modelname);
							
						}else {
							System.out.println("neededEvent Data with null values " + date + modelname);
						}
					}	
				}else{
					//System.out.println("neededEventdata with null values" +date);
				}*/
					
				datesMap.put(date, neededEventData);
				
		}
			
		
	}
	compareEventsDataMap.put(modelname, datesMap);
	
		return compareEventsDataMap;	
	}
	
	
	@Override
	public Map<String, Integer> getDatesLengthMap() {
		// TODO Auto-generated method stub
		return datesMapsLength;
	}
	
	@Override
	public int getPerpetualHeight() {
		// TODO Auto-generated method stub
		return perpetualHeightCount;
	}
	

	
}
