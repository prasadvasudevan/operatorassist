package com.frontpaw.forecast.util;



import play.mvc.*;

import play.libs.F.*;


//import static org.fest.assertions.Assertions.*;

//import static org.fluentlenium.core.filter.FilterConstructor.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Iterator;

import java.util.List;
import java.io.StringReader;


import com.frontpaw.forecast.service.FPAuthorizationService;
import com.frontpaw.forecast.util.User;
//import com.frontpaw.forecast.util.XMLDocumentHelper;

import javax.servlet.http.HttpServletRequest;

//import org.apache.http.entity.mime.HttpMultipartMode;
/*import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.entity.mime.content.FileBody;*/

import org.apache.http.impl.client.DefaultHttpClient;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


import java.io.File;

public class ComparisonDisplayData {
	Document newdoc = null;
	DocumentBuilder builder;
	private static final String DTM_MANAGER_PROP_NAME = "com.sun.org.apache.xml.internal.dtm.DTMManager";  
    private static final String DTM_MANAGER_CLASS_NAME =   "com.sun.org.apache.xml.internal.dtm.ref.DTMManagerDefault";  
    private static boolean speedUpDTMManagerSearch;  
	
	
	public ComparisonDisplayData(){

	}

	
  
    static {  
        try {  
            speedUpDTMManagerSearch = !isDTMManagerDetermined();  
        } catch (Exception e) {  
            speedUpDTMManagerSearch = false;  
        }  
    }   
  
    private static boolean isDTMManagerDetermined() throws Exception  
    {  
        return (System.getProperty(DTM_MANAGER_PROP_NAME) != null);  
    }  

	
	

 
	
	
	
	public LinkedHashMap<String,Object> getComparisonData(String forecastXMLString){
			LinkedHashMap<String,Object> allMap = new LinkedHashMap<String,Object>();
			LinkedHashMap<String,List<String>> firstSectionData = new LinkedHashMap<String,List<String>>();

			try {

			boolean check = false;
			//   HttpEntity entity = response.getEntity();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			builder = factory.newDocumentBuilder();
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xpath = xFactory.newXPath();


			InputSource is1 = new InputSource(new StringReader(forecastXMLString));
			newdoc = builder.parse(is1);
				
			String[] firstsection = {"Cum Net Income","Net Income","Net Worth","Cash"};
				
			NodeList modelList = getNodeList(newdoc,xpath,"//Models/Model/Name/text()");
			NodeList modelIdList = getNodeList(newdoc,xpath,"//Models/Model/ModelID/text()");
			
			NodeList periodsList = getNodeList(newdoc,xpath,"//Forecast/Periods/Period/text()");
			NodeList datesList = getNodeList(newdoc,xpath,"//Forecast/Dates/Date/text()");
			
			String[] modelname = new String[modelList.getLength()];
			String[] modelId = new String[modelIdList.getLength()];

			for(int a=0;a<modelList.getLength();a++){
				modelname[a] = modelList.item(a).getNodeValue();
				modelId[a] = modelIdList.item(a).getNodeValue();
				
				System.out.println(modelname[a]);
			}
			allMap.put("modelskeys",modelname);
			allMap.put("firstSectionkeys",firstsection);
			for(int b=0;b<firstsection.length;b++){
					ArrayList<String> firstSectionList = new ArrayList<String>();
					for(int a=0;a<modelname.length;a++){
							String assign = "";
							if(!firstsection[b].equals("Cash")){
							 assign = "//Metrics/Model[ModelName='"+modelname[a]+"']/EconMetrics/Financials/Statement/Measure[Name='"+firstsection[b]+"']/Values/Value/text()";
							//System.out.println(assign);
							}else
							 assign = "//Metrics/Model[ModelName='"+modelname[a]+"']/EconMetrics/Financials/Statement/Measure[EbbName='"+firstsection[b]+"']/Values/Value/text()";	
							String val = getLastValue(getNodeList(newdoc,xpath,assign));
							firstSectionList.add(val);
							System.out.println(firstsection[b] + val );
					}
					firstSectionData.put(firstsection[b],firstSectionList);
					
			}
			allMap.put("firstSectionData",firstSectionData);
	
				
			LinkedHashMap<String,List> modelPerPertualMap = new LinkedHashMap<String,List>();

			for(int d=0;d<modelname.length;d++){
					// strategy perpetual list..
					String expr1 = "//Model[ModelName='"+modelname[d]+"']/StrategyMetrics/Strategy[UserName=//Models/Model[Name='"+modelname[d]+"']/Strategies/GeneralStrategy[StrategyTiming/Perpetual]/UserName]/UserName/text()";
					//System.out.println(expr);
					NodeList nodes = getNodeList(newdoc,xpath,expr1);
					List<List> perpertualList = new ArrayList<List>();
					addPerpetualList(nodes,"//Models/Model[Name='"+modelname[d]+"']/Strategies/GeneralStrategy[UserName='",perpertualList,newdoc,xpath);
					//events perpetualList
					String expr2 = "//Model[ModelName='"+modelname[d]+"']/EventMetrics/Event[UserName=//Models/Model[Name='"+modelname[d]+"']/Events/EbbUserSetParamEvent[EventTiming/Perpetual]/UserName]/UserName/text()";
					NodeList eventnodes = getNodeList(newdoc,xpath,expr2);
					addPerpetualList(eventnodes,"//Models/Model[Name='"+modelname[d]+"']/Events/EbbUserSetParamEvent[UserName='",perpertualList,newdoc,xpath);
					// general events in event metrics perpetual List
					String expr3 = "//Model[ModelName='"+modelname[d]+"']/EventMetrics/Event[UserName=//Models/Model[Name='"+modelname[d]+"']/Events/GeneralEvent[EventTiming/Perpetual]/UserName]/UserName/text()";
					NodeList generaleventnodes = getNodeList(newdoc,xpath,expr3);
					addPerpetualList(generaleventnodes,"//Models/Model[Name='"+modelname[d]+"']/Events/GeneralEvent[UserName='",perpertualList,newdoc,xpath);			
					
					modelPerPertualMap.put(modelname[d],perpertualList);
			}		
				
				
						
			allMap.put("perpertual",modelPerPertualMap);
				
				

				
			LinkedHashMap<String,List<List<List<String>>>> periodMap = new LinkedHashMap<String,List<List<List<String>>>>();
				

			for(int a=0;a<periodsList.getLength();a++){
					String dateval = getValueFromNode(datesList.item(a));
					String periodval = getValueFromNode(periodsList.item(a));
					ArrayList models_periodList = new ArrayList();
					boolean checkavail = false;
					for(int d=0;d<modelname.length;d++){
							ArrayList periodList = new ArrayList();
							String expr1 = "/Forecast/Metrics/Model/StrategyMetrics/Strategy[../../ModelID='"+modelId[d]+"' and UserName=//GeneralStrategy[StrategyTiming/OneTime]/UserName and Dates/Date='"+dateval+"']/UserName/text()";
							//System.out.println("expr1 " + expr1);
							NodeList strategynodes = getNodeList(newdoc,xpath,expr1);
							if(strategynodes!=null && strategynodes.getLength()>0){
								checkavail = true;
								addPeriodList(strategynodes,"//Models/Model[Name='"+modelname[d]+"']/Strategies/GeneralStrategy[UserName='",periodList,newdoc,xpath,dateval);
							}	
							String expr2 = "/Forecast/Metrics/Model/EventMetrics/Event[../../ModelID='"+modelId[d]+"' and UserName=//Events/EbbUserSetParamEvent/UserName and Dates/Date='"+dateval+"']/UserName/text()";
							NodeList ebbUserNodes = getNodeList(newdoc,xpath,expr2);
							if(ebbUserNodes!=null && ebbUserNodes.getLength()>0){
								checkavail = true;
								System.out.println("test" + modelname[d]);
								addPeriodList1(ebbUserNodes,"//Models/Model[Name='"+modelname[d]+"']/Events/EbbUserSetParamEvent[UserName='",periodList,newdoc,xpath,dateval);
							}	
						
							String expr3 = "/Forecast/Metrics/Model/EventMetrics/Event[../../ModelID='"+modelId[d]+"' and UserName=//Events/GeneralEvent/UserName and Dates/Date='"+dateval+"']/UserName/text()";
							NodeList generalEventNodes = getNodeList(newdoc,xpath,expr3);
							if(generalEventNodes!=null && generalEventNodes.getLength()>0){
								checkavail = true;
								addPeriodList1(generalEventNodes,"//Models/Model[Name='"+modelname[d]+"']/Events/GeneralEvent[UserName='",periodList,newdoc,xpath,dateval);
							}
							if(checkavail){
								models_periodList.add(periodList);
								periodMap.put(dateval,models_periodList);

							}

					}		
				
			}		
			allMap.put("periodValues",periodMap);
				

				
				
				
				
				
		//}	
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return allMap;
		//System.out.
	
	}
	
	
	private void addPeriodList1(NodeList userNameNodes,String paramExp,List periodList,Document newdoc,XPath xpath,String date)throws Exception{
			if(userNameNodes!=null && userNameNodes.getLength()>0){
					for(int a=0;a<userNameNodes.getLength();a++){
							ArrayList<String> perpertualvalueList = new ArrayList<String>();
							String value = userNameNodes.item(a).getNodeValue();
							perpertualvalueList.add(value);
							System.out.println("value " + value);
							String nameVal = getValue(getNodeList(newdoc,xpath,paramExp+value+"']/Name/text()"));
							perpertualvalueList.add(nameVal);							
							String exp1 = paramExp+value+"']/EbbUserSetParamName/text()";
							String exp2 = paramExp+value+"']/Value/text()";
							NodeList paramNameList = getNodeList(newdoc,xpath,exp1);
							NodeList paramValueList = getNodeList(newdoc,xpath,exp2);
							addParamToList(paramNameList,paramValueList,perpertualvalueList);
							periodList.add(perpertualvalueList);
							
					}
			}
	
	}	
	
	
	
		private void addPeriodList(NodeList userNameNodes,String paramExp,List periodList,Document newdoc,XPath xpath,String date)throws Exception{
			if(userNameNodes!=null && userNameNodes.getLength()>0){
					for(int a=0;a<userNameNodes.getLength();a++){
							ArrayList<String> perpertualvalueList = new ArrayList<String>();
							String value = userNameNodes.item(a).getNodeValue();
							perpertualvalueList.add(value);
							System.out.println("value " + value);
							String nameVal = getValue(getNodeList(newdoc,xpath,paramExp+value+"']/Name/text()"));
							perpertualvalueList.add(nameVal);
							String exp1 = paramExp+value+"']/Params/Param/Name/text()";
							String exp2 = paramExp+value+"']/Params/Param/Value/text()";
							NodeList paramNameList = getNodeList(newdoc,xpath,exp1);
							NodeList paramValueList = getNodeList(newdoc,xpath,exp2);
							addParamToList(paramNameList,paramValueList,perpertualvalueList);
							periodList.add(perpertualvalueList);
					}
			}
	
	}
	
	
	private void addPerpetualList(NodeList userNameNodes,String paramExp,List<List> perpertualList,Document newdoc,XPath xpath) throws Exception{
			if(userNameNodes!=null){
					for(int a=0;a<userNameNodes.getLength();a++){
							ArrayList perpertualvalueList = new ArrayList();
							String value = userNameNodes.item(a).getNodeValue();
							perpertualvalueList.add(value);
							//System.out.println("value " + value);
							String exp1 = paramExp+value+"']/Params/Param/Name/text()";
							String exp2 = paramExp+value+"']/Params/Param/Value/text()";
							NodeList paramNameList = getNodeList(newdoc,xpath,exp1);
							NodeList paramValueList = getNodeList(newdoc,xpath,exp2);
							addParamToList(paramNameList,paramValueList,perpertualvalueList);
							perpertualList.add(perpertualvalueList);

							
					}
			}
	
	
	}
	
	
	private void addParamToList1(NodeList paramNameList,NodeList paramValueList,List<String> perpertualvalueList){
			for(int c=0;null!=paramNameList && c<paramNameList.getLength();c++){
					String concatval = "";
					boolean con = false;
					Node paramNameNode = paramNameList.item(c);
					Node paramValueNode = paramValueList.item(c);
					if(paramNameNode!=null && paramNameNode.getNodeValue()!=null){
							concatval += paramNameNode.getNodeValue();
								con = true;
								//perpertualvalueList.add(paramNameList.item(c).getNodeValue() + " : " + paramValueList.item(c).getNodeValue());
					}
					if(paramValueNode!=null && paramValueNode.getNodeValue()!=null){
							if(con)
								concatval+=" : "+ paramValueNode.getNodeValue();
							else {
								concatval+=paramValueNode.getNodeValue();
							}	
					}
					if(con)
						perpertualvalueList.add(concatval);
			}
	
	}
	
	
	private void addParamToList(NodeList paramNameList,NodeList paramValueList,ArrayList<String> perpertualvalueList){
			for(int c=0;null!=paramNameList && c<paramNameList.getLength();c++){
					String concatval = "";
					boolean con = false;
					Node paramNameNode = paramNameList.item(c);
					Node paramValueNode = paramValueList.item(c);
					if(paramNameNode!=null && paramNameNode.getNodeValue()!=null){
							concatval += paramNameNode.getNodeValue();
								con = true;
								//perpertualvalueList.add(paramNameList.item(c).getNodeValue() + " : " + paramValueList.item(c).getNodeValue());
					}
					if(paramValueNode!=null && paramValueNode.getNodeValue()!=null){
							if(con)
								concatval+=" : "+ paramValueNode.getNodeValue();
							else {
								concatval+=paramValueNode.getNodeValue();
							}	
					}
					if(con)
						perpertualvalueList.add(concatval);
			}
	
	}
	
	
	
	
	/*private static synchronized Object evaluateXPath(Node node,String xPath,XPath xPathProcessor,QName returnType)  throws XPathExpressionException  
    {  
        if (speedUpDTMManagerSearch) {  
            synchronized (XPathProcessorUtility.class) {  
                boolean setDTMManager = !isDTMManagerDetermined();  
                if (setDTMManager) {  
                    System.setProperty(DTM_MANAGER_PROP_NAME, DTM_MANAGER_CLASS_NAME);  
                }  
                try {  
                    return xPathProcessor.evaluate(xPath, node, returnType);  
                } finally {  
                    if (setDTMManager) {  
                        System.clearProperty(DTM_MANAGER_PROP_NAME);  
                    }  
                }  
            }  
        } else {  
            return xPathProcessor.evaluate(xPath, node, returnType);  
        }  
    }*/      
	
	private static synchronized Object evaluateXPath(Document newdoc,XPath xpath,String expressionvalue)  throws Exception  
    {  
		XPathExpression expression =null;
	
        if (speedUpDTMManagerSearch) {  
            synchronized (ComparisonDisplayData.class) {  
                boolean setDTMManager = !isDTMManagerDetermined();  
				
                if (setDTMManager) {  
                    System.setProperty(DTM_MANAGER_PROP_NAME, DTM_MANAGER_CLASS_NAME);  
                }  
                try {  
					expression = xpath.compile(expressionvalue);
                    return expression.evaluate(newdoc,XPathConstants.NODESET);  
                } finally {  
                    if (setDTMManager) {  
                        System.clearProperty(DTM_MANAGER_PROP_NAME);  
                    }  
                }  
            }  
        } else {  
					System.out.println("testing in else");
					expression = xpath.compile(expressionvalue);
                    return expression.evaluate(newdoc,XPathConstants.NODESET);  
        }  
    }      
	
	
	
	private NodeList getNodeList(Document newdoc,XPath xpath,String expressionvalue) throws Exception{
			XPathExpression expression =null;
			//expression = xpath.compile(expressionvalue);
			//Object result = expression.evaluate(newdoc, XPathConstants.NODESET);
			Object result = evaluateXPath(newdoc,xpath,expressionvalue);
			NodeList list = (NodeList) result;
			return list;
	}

	
	
	
	private String getValue(NodeList nodelist){
			String nodeValue ="";
			if(nodelist!=null){
				Node node = nodelist.item(0);
				if(node!=null){
					nodeValue = node.getNodeValue();
				}
			}
			return nodeValue;
	}
	
	private String getLastValue(NodeList nodelist){
			String nodeValue ="";
			if(nodelist!=null){
				Node node = nodelist.item(nodelist.getLength()-1);
				if(node!=null){
					nodeValue = node.getNodeValue();
				}
			}
			return nodeValue;
	}
	
	
	
	private String getValueFromNode(Node _node){
			String nodeValue ="";
			if(_node!=null){
					nodeValue = _node.getNodeValue();
			}
			return nodeValue;
	}
	
	private NodeList getNodeList(Document newdoc,XPath xpath,String expression,String firstValue) throws Exception{
			NodeList list = null;
			return list;
	}

	private NodeList getNodeList(Document newdoc,XPath xpath,String expression,String firstValue,String secondValue ) throws Exception{
			NodeList list = null;
			return list;
	}
	
  

}

