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
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}Name"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}UserName"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}EbbName"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}EbbUserName"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}EbbCommonTag"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}EbbUserSetParamName"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}Value"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}EventTiming"/>
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
    "name",
    "userName",
    "ebbName",
    "ebbUserName",
    "ebbCommonTag",
    "ebbUserSetParamName",
    "value",
    "eventTiming"
})
@XmlRootElement(name = "EbbUserSetParamEvent")
public class EbbUserSetParamEvent {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "UserName", required = true)
    protected String userName;
    @XmlElement(name = "EbbName", required = true)
    protected String ebbName;
    @XmlElement(name = "EbbUserName", required = true)
    protected String ebbUserName;
    @XmlElement(name = "EbbCommonTag", required = true)
    protected String ebbCommonTag;
    @XmlElement(name = "EbbUserSetParamName", required = true)
    protected String ebbUserSetParamName;
    @XmlElement(name = "Value", required = true)
    protected String value;
    @XmlElement(name = "EventTiming", required = true)
    protected EventTiming eventTiming;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
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
     * Gets the value of the ebbCommonTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEbbCommonTag() {
        return ebbCommonTag;
    }

    /**
     * Sets the value of the ebbCommonTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEbbCommonTag(String value) {
        this.ebbCommonTag = value;
    }

    /**
     * Gets the value of the ebbUserSetParamName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEbbUserSetParamName() {
        return ebbUserSetParamName;
    }

    /**
     * Sets the value of the ebbUserSetParamName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEbbUserSetParamName(String value) {
        this.ebbUserSetParamName = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the eventTiming property.
     * 
     * @return
     *     possible object is
     *     {@link EventTiming }
     *     
     */
    public EventTiming getEventTiming() {
        return eventTiming;
    }

    /**
     * Sets the value of the eventTiming property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventTiming }
     *     
     */
    public void setEventTiming(EventTiming value) {
        this.eventTiming = value;
    }

}
