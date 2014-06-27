package com.frontpaw.forecast.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.frontpaw.forecast.util.User;

public class DocumentParser {
  public static User getUserDetails(String xmlString) throws Exception{
	User user = new User();
	String password = "";
    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    InputSource is = new InputSource();
    is.setCharacterStream(new StringReader(xmlString));

    Document doc = db.parse(is);
    NodeList nodes = doc.getElementsByTagName("User");
    Element element = (Element) nodes.item(0);
    NodeList passwordnode = element.getElementsByTagName("Password");
    Element line = (Element) passwordnode.item(0);
	user.setPassword(getCharacterDataFromElement(line));
	
	NodeList firstnamenode = element.getElementsByTagName("FirstName");
    Element line1 = (Element) firstnamenode.item(0);
	user.setFirstName(getCharacterDataFromElement(line1));

	NodeList lastnamenode = element.getElementsByTagName("LastName");
    Element line2 = (Element) lastnamenode.item(0);
	user.setLastName(getCharacterDataFromElement(line2));

	/*NodeList description = element.getElementsByTagName("Description");
    Element line3 = (Element) description.item(0);
	user.setDescription(getCharacterDataFromElement(line3));*/
	
	NodeList userId = element.getElementsByTagName("UserID");
    Element line4 = (Element) userId.item(0);
	user.setEmail(getCharacterDataFromElement(line4));
	

	
	return user;

    /*for (int i = 0; i < nodes.getLength(); i++) {
      Element element = (Element) nodes.item(i);

      NodeList password = element.getElementsByTagName("Password");
      Element line = (Element) password.item(0);
      System.out.println("password: " + getCharacterDataFromElement(line));

    }*/

  }

  public static String getCharacterDataFromElement(Element e) {
    Node child = e.getFirstChild();
    if (child instanceof CharacterData) {
      CharacterData cd = (CharacterData) child;
      return cd.getData();
    }
    return "";
  }
}
