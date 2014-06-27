/**
 * 
 */
package com.frontpaw.forecast.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
import org.apache.commons.codec.binary.Base64;

//import org.apache.commons.codec.binary.StringUtils;

import java.io.BufferedReader;

import com.frontpaw.forecast.util.DocumentParser;
import com.frontpaw.forecast.util.User;


/**
* This is a RESTServiceClient Class  
* @author frontpaw
* @version 1.0
*/


public class RestService {

	public String getReferenceXml(){
		return "";
	}

	
	/**
	 * This method returns the RESTClient response as String value 
	 * @param euxml contains the updated string xml value of EconomicUnit's XMLdocument.
	 * @param modelxml contains the updated string xml value of Model or DebtModel or DebtCostModel XMLDocument.
	 * @return response generated string response from RESTClient 
	 */	
	

	
	
	/*public String RegisterUser(String firstName,String lastName,String password,String company,String email)throws Exception{
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
		/*CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT,AuthScope.ANY_REALM, "basic")
,new UsernamePasswordCredentials("fpmainwebapp.admin", "zW3fQ39aN8"));
		//HttpClient client = new DefaultHttpClient();
		//client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		//CredentialsProvider provider = client.getCredentialsProvider();
		//client.setCredentialsProvider(credsProvider);
		//client.getState().setCredentials(AuthScope.ANY, credentials);
        DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
    //    client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT,AuthScope.ANY_REALM), 
      //              new UsernamePasswordCredentials("fpmainwebapp.admin", "zW3fQ39aN8"));		
		
		//HttpPut post = new HttpPut("http://pmxrestenv-ppmjmkhrhc.elasticbeanstalk.com/api/leads/"+email);
		HttpPost post = new HttpPost("http://pmxrestenv-dev.elasticbeanstalk.com/api/users/");
		String encoding = new String(
		org.apache.commons.codec.binary.Base64.encodeBase64   
		(org.apache.commons.codec.binary.StringUtils.getBytesUtf8("test:test"))
		);
		
		
		post.setHeader("Authorization", "Basic " + encoding);

//		post.addHeader("Authorization", (String)authorization.get("Authorization"));
		post.addHeader("date", (String)authorization.get("date"));
		post.addHeader("UserID", "fpmainwebapp.admin");
		post.addHeader("Password", "zW3fQ39aN8");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("firstName" ,firstName));
	    nameValuePairs.add(new BasicNameValuePair("lastName" ,lastName));
	    nameValuePairs.add(new BasicNameValuePair("email" ,email));
	    nameValuePairs.add(new BasicNameValuePair("password" ,password));
	    nameValuePairs.add(new BasicNameValuePair("userId" ,email));
	    nameValuePairs.add(new BasicNameValuePair("groups" ,"basic"));
		
	    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    String response = "";
	    HttpResponse httpResp = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResp.getEntity().getContent()));
		String line = "";
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
			if (line.startsWith("Auth=")) {
				String key = line.substring(5);
          // Do something with the key
			}

		}

		
		
	    response = new Integer(httpResp.getStatusLine().getStatusCode()).toString();
	    System.out.println("status code" + response);
		return response;

		
	}*/
	
	
	
		public String RegisterUser(String firstName,String lastName,String password,String company,String email,String userId)throws Exception{
		String response = "";
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
        DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpPost post = new HttpPost("http://pmxrestenv-dev.elasticbeanstalk.com/api/users/");
		post.addHeader("Authorization", (String)authorization.get("Authorization"));
		post.addHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
		post.addHeader("date", (String)authorization.get("date"));
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("firstName" ,firstName));
	    nameValuePairs.add(new BasicNameValuePair("lastName" ,lastName));
	    nameValuePairs.add(new BasicNameValuePair("email" ,email));
	    nameValuePairs.add(new BasicNameValuePair("password" ,password));
	    nameValuePairs.add(new BasicNameValuePair("userId" ,userId));
	    nameValuePairs.add(new BasicNameValuePair("groups" ,"basic"));
	    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    HttpResponse httpResp = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResp.getEntity().getContent()));
		String line = "";
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
			if (line.startsWith("Auth=")) {
				String key = line.substring(5);
          // Do something with the key
			}

		}
	    response = new Integer(httpResp.getStatusLine().getStatusCode()).toString();
	    System.out.println("status code" + response);
		return response;

		
	}
		
		
		public String UpdateUser(String userId,String password)throws Exception{
		System.out.println("user id for updating" + password);
		String response = "";
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
        DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		String uri = "http://pmxrestenv-dev.elasticbeanstalk.com/api/users/"+userId;
		System.out.println("request url" + uri );
		HttpPut post = new HttpPut(uri);
		post.addHeader("Content-Type", "application/x-www-form-urlencoded" );

		//HttpPost post = new HttpPost("http://pmxrestenv-dev.elasticbeanstalk.com/api/users/");
		post.addHeader("Authorization", (String)authorization.get("Authorization"));
		post.addHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
		post.addHeader("date", (String)authorization.get("date"));
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("password" ,password));
	  //  nameValuePairs.add(new BasicNameValuePair("userId" ,userId));
	  //  nameValuePairs.add(new BasicNameValuePair("groups" ,"basic"));
	    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    HttpResponse httpResp = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResp.getEntity().getContent()));
		String line = "";
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
			if (line.startsWith("Auth=")) {
				String key = line.substring(5);
          // Do something with the key
			}

		}
	    response = new Integer(httpResp.getStatusLine().getStatusCode()).toString();
	    System.out.println("response status code for updating user 	" +  userId + response);
		return response;

		
	}
	
	
	/*	public String RegisterUser(String firstName,String lastName,String password,String company,String email)throws Exception{
		String response = "";
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
        DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpPost post = new HttpPost("http://pmxrestenv-dev.elasticbeanstalk.com/api/users/");
		post.addHeader("Authorization", (String)authorization.get("Authorization"));
		post.addHeader("x-fpws-authorization", (String)authorization.get("userAndPassword"));
		post.addHeader("password", "zW3fQ39aN8");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("firstName" ,firstName));
	    nameValuePairs.add(new BasicNameValuePair("lastName" ,lastName));
	    nameValuePairs.add(new BasicNameValuePair("email" ,email));
	    nameValuePairs.add(new BasicNameValuePair("password" ,password));
	    nameValuePairs.add(new BasicNameValuePair("userId" ,email));
	    nameValuePairs.add(new BasicNameValuePair("groups" ,"basic"));
	    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    HttpResponse httpResp = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResp.getEntity().getContent()));
		String line = "";
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
			if (line.startsWith("Auth=")) {
				String key = line.substring(5);
          // Do something with the key
			}

		}
	    response = new Integer(httpResp.getStatusLine().getStatusCode()).toString();
	    System.out.println("status code" + response);
		return response;

		
	}
	
	
	
		public String RegisterUser(String firstName,String lastName,String password,String company,String email)throws Exception{
		String response = "";
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
        DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpPost post = new HttpPost("http://pmxrestenv-dev.elasticbeanstalk.com/api/users/");
		post.addHeader("Authorization", (String)authorization.get("Authorization"));
		post.addHeader("UserID", "fpmainwebapp.admin");
		post.addHeader("Password", "zW3fQ39aN8");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("firstName" ,firstName));
	    nameValuePairs.add(new BasicNameValuePair("lastName" ,lastName));
	    nameValuePairs.add(new BasicNameValuePair("email" ,email));
	    nameValuePairs.add(new BasicNameValuePair("password" ,password));
	    nameValuePairs.add(new BasicNameValuePair("userId" ,email));
	    nameValuePairs.add(new BasicNameValuePair("groups" ,"basic"));
		
	    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    HttpResponse httpResp = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResp.getEntity().getContent()));
		String line = "";
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
			if (line.startsWith("Auth=")) {
				String key = line.substring(5);
          // Do something with the key
			}

		}
	    response = new Integer(httpResp.getStatusLine().getStatusCode()).toString();
	    System.out.println("status code" + response);
		return response;

		
	}*/
	
	public User checkUser(String email,String password)throws Exception{
		User user = null;
		Hashtable authorization = new FPAuthorizationService().getAuthorization();
		HttpClient httpclient = new DefaultHttpClient();
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpGet httpGet = new HttpGet("http://pmxrestenv-dev.elasticbeanstalk.com/api/users/"+email);
		httpGet.addHeader("Authorization", (String)authorization.get("Authorization"));
		httpGet.addHeader("date", (String)authorization.get("date"));
		httpGet.addHeader("x-fpws-authorization", (String)authorization.get("x-fpws-authorization"));
        HttpResponse httpResp = httpclient.execute(httpGet);
		boolean check = false;
     //   HttpEntity entity = response.getEntity();
		String response = new Integer(httpResp.getStatusLine().getStatusCode()).toString();
		if(response.equals("200")){
		      BufferedReader rd = new BufferedReader(new InputStreamReader(httpResp.getEntity().getContent()));
			  String xmlString = "";
			  String line = "";
			  while ((line = rd.readLine()) != null) {
					xmlString += line;
			  }
			  System.out.println(xmlString);
			  user = DocumentParser.getUserDetails(xmlString);
			  if(user.getPassword().equals(password)){
				user.setSearchMessage("ValidUser");
			  }else if(response.equals("400")){
				user.setSearchMessage("UserNotfound");
			  }else if(response.equals("500")){
				user.setSearchMessage("Server Error");
			  }
			
		}else {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(httpResp.getEntity().getContent()));
			  String xmlString = "";
			  String line = "";
			  while ((line = rd.readLine()) != null) {
					xmlString += line;
			  }
			  System.out.println("login response for user" + email +" "+  xmlString);
		}
		//System.out.println("checkUser    " + checkUser);
		
		return user;

		
	}
	
	
	
	/**
	 * This method returns the ipaddress of the request being made 
	 * @param request .
	 * @return ipAddress  
	 */	
	
	private String getIPAddress(HttpServletRequest request){
		
		request.getHeader("VIA");
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
		      ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
		
		
	}
	
	
	

}

