//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.09 at 12:38:16 PM IST 
//


package com.frontpaw.forecast.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}MeasureType"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}Account"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}EbbName"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}EbbUserName"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}EconTag"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}Values"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "measureType",
    "account",
    "ebbName",
    "ebbUserName",
    "econTag",
    "values",
    "name"
})
@XmlRootElement(name = "Measure")
public class Measure {

    @XmlElement(name = "MeasureType", required = true)
    protected String measureType;
    @XmlElement(name = "Account", required = true)
    protected String account;
    @XmlElement(name = "EbbName", required = true)
    protected String ebbName;
    @XmlElement(name = "EbbUserName", required = true)
    protected String ebbUserName;
    @XmlElement(name = "EconTag", required = true)
    protected String econTag;
    @XmlElement(name = "Values", required = true)
    protected Values values;
    @XmlElement(name = "Name", required = false)
    protected String name;

    /* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return name.equals(obj.toString());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
     * Gets the value of the measureType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasureType() {
        return measureType;
    }

    /**
     * Sets the value of the measureType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasureType(String value) {
        this.measureType = value;
    }

    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccount(String value) {
        this.account = value;
    }

    /**
     * Gets the value of the ebbName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEbbName() {
        return ebbName;
    }

    /**
     * Sets the value of the ebbName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEbbName(String value) {
        this.ebbName = value;
    }

    /**
     * Gets the value of the ebbUserName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEbbUserName() {
        return ebbUserName;
    }

    /**
     * Sets the value of the ebbUserName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEbbUserName(String value) {
        this.ebbUserName = value;
    }

    /**
     * Gets the value of the econTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEconTag() {
        return econTag;
    }

    /**
     * Sets the value of the econTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEconTag(String value) {
        this.econTag = value;
    }

    /**
     * Gets the value of the values property.
     * 
     * @return
     *     possible object is
     *     {@link Values }
     *     
     */
    public Values getValues() {
        return values;
    }

    /**
     * Sets the value of the values property.
     * 
     * @param value
     *     allowed object is
     *     {@link Values }
     *     
     */
    public void setValues(Values value) {
        this.values = value;
    }
    
    /**
   	 * @return the name
   	 */
   	public String getName() {
   		return name;
   	}

   	/**
   	 * @param name the name to set
   	 */
   	public void setName(String name) {
   		this.name = name;
   	}


}
