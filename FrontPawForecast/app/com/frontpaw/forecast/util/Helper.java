package com.frontpaw.forecast.util;

import java.util.Properties;

public class Helper {
	
	
	public String addMinus(String value){
		if(value.substring(0, 1).equals("-")){
			value = new Integer(value.substring(0, value.length()-1)).intValue()*(-1) + "%";
			//value = value.substring(1, value.length());
			System.out.println("value in if =" + value);
			//value = value.substring(1);
		}else {
			value = new Integer(value.substring(0, value.length()-1)).intValue()*(-1) + "%";
			System.out.println("value in else =" + value);

		}

		return value;
	}
	
	
	public Properties getMailProperties(){
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", "email-smtp.us-east-1.amazonaws.com");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.auth", "true");
	  	//	props.put("mail.smtp.ssl.enable", true);
		props.put("mail.smtp.port", "587");
		props.put("mail.debug", "true");
		props.put("username", "AKIAJYOVKO2QCMUQHFIQ");
		props.put("password", "Aq9n4WiuRzfiAb01Nd2+eMEHjOmY14LEBHojM6tMXLuU");
		return props;
		
	}
	

}
