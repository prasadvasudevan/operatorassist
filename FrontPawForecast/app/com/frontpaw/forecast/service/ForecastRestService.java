package com.frontpaw.forecast.service;



import play.mvc.*;

import play.libs.F.*;


//import static org.fest.assertions.Assertions.*;

//import static org.fluentlenium.core.filter.FilterConstructor.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.io.StringReader;


import com.frontpaw.forecast.service.FPAuthorizationService;
import com.frontpaw.forecast.util.User;
//import com.frontpaw.forecast.util.XMLDocumentHelper;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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


import java.io.File;


public class ForecastRestService {
	XPathExpression expr = null;
	DocumentBuilder builder;
	Document doc = null;


    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */   
    
	public String getUserForecast(String email){
    	String forecastXMLString = "";
			try {
			//String email = session("email");
			System.out.println("email................................................  " + email);
			//String URL = "http://pmxrestenv-dev.elasticbeanstalk.com/api/users/acplsuresh@gmail.com/forecasts/latest";
			String URL = "http://pmxrestenv-dev.elasticbeanstalk.com/api/users/"+email+"/forecasts/latest";
			System.out.println("url................................................  " + URL);

			String forecastURI = "http://pmxrestenv-dev.elasticbeanstalk.com/api/forecasts/";
			Hashtable authorization = new FPAuthorizationService().getAuthorization();
			HttpClient httpclient = new DefaultHttpClient();
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpGet httpGet = new HttpGet(URL);
			httpGet.addHeader("Authorization", (String)authorization.get("Authorization"));
			httpGet.addHeader("date", (String)authorization.get("date"));
			httpGet.addHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
			HttpResponse httpResp = httpclient.execute(httpGet);
			boolean check = false;
			//   HttpEntity entity = response.getEntity();
			String response = new Integer(httpResp.getStatusLine().getStatusCode()).toString();
			System.out.println("get user forecast test" + response);
			if(response.equals("200")){
		      BufferedReader rd = new BufferedReader(new InputStreamReader(httpResp.getEntity().getContent()));
			  String xmlString = "";
			  String line = "";
			  while ((line = rd.readLine()) != null) {
					xmlString += line;
			  }
			  
			  
			//  System.out.println(xmlString);
			  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			  factory.setNamespaceAware(true);
			  builder = factory.newDocumentBuilder();
			  InputSource is = new InputSource(new StringReader(xmlString));
			  doc = builder.parse(is);
			  XPathFactory xFactory = XPathFactory.newInstance();
			  XPath xpath = xFactory.newXPath();
			  expr = xpath.compile("//Forecast/ForecastID/text()");
			  Object result = expr.evaluate(doc, XPathConstants.NODESET);
			  NodeList nodes = (NodeList) result;		
			  System.out.println("nodes length " + nodes.getLength());	
			  String forecastid = "";
			  for(int i=0;i<nodes.getLength();i++){
				forecastid = nodes.item(i).getNodeValue();
				System.out.println("forecast id " + forecastid);
				
			  }
			  HttpGet httpGet1 = new HttpGet(forecastURI+forecastid);
			  httpGet1.addHeader("Authorization", (String)authorization.get("Authorization"));
			  httpGet1.addHeader("date", (String)authorization.get("date"));
			  httpGet1.addHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
			  HttpResponse httpResp1 = httpclient.execute(httpGet1);
			  String forecastresponse = new Integer(httpResp1.getStatusLine().getStatusCode()).toString();
			  System.out.println("get user forecast test" + forecastresponse);
			  if(forecastresponse.equals("200")){
				BufferedReader rd1 = new BufferedReader(new InputStreamReader(httpResp1.getEntity().getContent()));
				
				String line1 = "";
				while ((line1 = rd1.readLine()) != null) {
					forecastXMLString += line1;
				}
				//System.out.println("forecastXML String " + forecastXMLString);
			  }
			  
			  /*user = DocumentParser.getUserPassword(xmlString);
			  if(user.getPassword().equals(password)){
				user.setSearchMessage("ValidUser");
			  }else if(response.equals("400")){
				user.setSearchMessage("UserNotfound");
			  }else if(response.equals("500")){
				user.setSearchMessage("Server Error");
			  }*/
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		//System.out.
			return forecastXMLString;
	
	}
	
  



public String getForeCastId(String email){
	  String forecastid = "";
		try {
		//String email = session("email");
		System.out.println("email................................................  " + email);
		//String URL = "http://pmxrestenv-dev.elasticbeanstalk.com/api/users/acplsuresh@gmail.com/forecasts/latest";
		String URL = "http://pmxrestenv-dev.elasticbeanstalk.com/api/users/"+email+"/forecasts/latest";
		System.out.println("url................................................  " + URL);

		String forecastURI = "http://pmxrestenv-dev.elasticbeanstalk.com/api/forecasts/";
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
		HttpClient httpclient = new DefaultHttpClient();
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpGet httpGet = new HttpGet(URL);
		httpGet.addHeader("Authorization", (String)authorization.get("Authorization"));
		httpGet.addHeader("date", (String)authorization.get("date"));
		httpGet.addHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
		HttpResponse httpResp = httpclient.execute(httpGet);
		boolean check = false;
		//   HttpEntity entity = response.getEntity();
		String response = new Integer(httpResp.getStatusLine().getStatusCode()).toString();
		System.out.println("get user forecast test" + response);
		if(response.equals("200")){
	      BufferedReader rd = new BufferedReader(new InputStreamReader(httpResp.getEntity().getContent()));
		  String xmlString = "";
		  String line = "";
		  while ((line = rd.readLine()) != null) {
				xmlString += line;
		  }
		  System.out.println(xmlString);
		  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		  factory.setNamespaceAware(true);
		  builder = factory.newDocumentBuilder();
		  InputSource is = new InputSource(new StringReader(xmlString));
		  doc = builder.parse(is);
		  XPathFactory xFactory = XPathFactory.newInstance();
		  XPath xpath = xFactory.newXPath();
		  expr = xpath.compile("//Forecast/ForecastID/text()");
		  Object result = expr.evaluate(doc, XPathConstants.NODESET);
		  NodeList nodes = (NodeList) result;		
		  System.out.println("nodes length " + nodes.getLength());	
		  for(int i=0;i<nodes.getLength();i++){
			forecastid = nodes.item(i).getNodeValue();
			System.out.println("forecast id " + forecastid);
			
		  }

		  
		  /*user = DocumentParser.getUserPassword(xmlString);
		  if(user.getPassword().equals(password)){
			user.setSearchMessage("ValidUser");
		  }else if(response.equals("400")){
			user.setSearchMessage("UserNotfound");
		  }else if(response.equals("500")){
			user.setSearchMessage("Server Error");
		  }*/
		
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	//System.out.
		return forecastid;

}


}

