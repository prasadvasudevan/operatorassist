package com.frontpaw.forecast.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
* This is a utility Class for for sending mail with and without attachement.
* @author frontpaw
* @version 1.0
*/


public class MailSender {
	
	private static final Log log = LogFactory.getLog("MailSender");
    private String SMTP_AUTH_USER = "";
    private String SMTP_AUTH_PWD  = "";

    
    
	/**
	 * This method sends mail with out attachment to the specified destination.
	 * @param properties holds mail server configuration details.
	 * @param yourName name of the receiver
	 * @param from sender address.
	 * @param to receiver mail address.
	 * @param subject of mail
	 * @param body of mail
	 */	
    
    public void sendMail(Properties properties,String yourName,String from,String to,String subject,String body ){
    	try {
    		Address[] replyAddress = new Address[1];
    		replyAddress[0] = new InternetAddress(to); 
    		SMTP_AUTH_USER = properties.getProperty("username");
    		SMTP_AUTH_PWD = properties.getProperty("password");
    		Authenticator auth = new SMTPAuthenticator();
    		Session mailSession = Session.getDefaultInstance(properties, auth);
    		mailSession.setDebug(true);
    		//OutputStream output = new OutputSream();  
    		//mailSession.setDebugOut(new PrintStream(new FileOutputStream("F://mailerror.txt")));
    		Transport transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
            message.setReplyTo(replyAddress);
            System.out.println("from " + from);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.addRecipient(Message.RecipientType.CC,new InternetAddress("sureshmay31@gmail.com"));
            message.addRecipient(Message.RecipientType.CC,new InternetAddress("prasad.vasudevan@gmail.com"));
            message.setSubject(subject);
            message.setText("Hi Admin,\n Name:" + yourName +"," + "\n" +body);
	        transport.connect();
            transport.send(message);
	        transport.close();		
    	}catch(Exception e){
    		e.printStackTrace();
    		log.error("sendMail() - MailSender" + e);
    	}

    	
    }
    
    
	/**
	 * This method sends mail with attachment to the specified destination.
	 * @param properties holds mail server configuration details.
	 * @param filePath path of file to be attached
	 * @param fielName name of the file to be attached 
	 * @param firstName name of the receiver
	 * @param lastName name of the receiver
	 * @param emailAddress receiver mail address.
	 */    
	
	public void sendMailwithAttachement(Properties properties,String filepath,String filename,String emailAddress,String firstName,String LastName) 
	{

		try {
			SMTP_AUTH_USER = properties.getProperty("username");
			SMTP_AUTH_PWD = properties.getProperty("password");
			Address[] replyAddress = new Address[1];
			replyAddress[0] = new InternetAddress("info@frontpaw.com"); 
			
			Authenticator auth = new SMTPAuthenticator();
			Session mailSession = Session.getDefaultInstance(properties, auth);
			Transport transport = mailSession.getTransport();
	        MimeMessage message = new MimeMessage(mailSession);
	        message.setSubject("Your Financial Forecasting Report");
	       // message.setFrom(new InternetAddress(properties.getProperty("username")));
	        message.setFrom(new InternetAddress("info@frontpaw.com"));
	        message.setReplyTo(replyAddress);
	        message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailAddress));
	        message.addRecipient(Message.RecipientType.CC, new InternetAddress("sureshmay31@gmail.com"));
            message.addRecipient(Message.RecipientType.CC,new InternetAddress("prasad.vasudevan@gmail.com"));
	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText("Hi " + firstName+" "+ LastName + ",\n \nWe have attached the Personal Economics Modeling report you requested. The report includes the data that you provided along with the underlying assumptions for the scenarios shown in the graph. We can provide assistance to personalize the model to your unique circumstances. We invite you to contact us for a free initial consultation by replying to this email with a preferred contact number.\n\nSincerely, \n\nThe FrontPaw Customer Support Team");
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);
	        messageBodyPart = new MimeBodyPart();
	        DataSource source = new FileDataSource(filepath+filename);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(filename);
	        multipart.addBodyPart(messageBodyPart);
	        message.setContent(multipart);
	        transport.connect();
	        transport.send(message);
	        transport.close();		
		}catch(Exception e){
			//e.printStackTrace();
			log.error("sendMail() - MailSender" + e);

		}
		
	}
	
	
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
           String username = SMTP_AUTH_USER;
           String password = SMTP_AUTH_PWD;
           
           return new PasswordAuthentication(username, password);
        }
    }
	

}
