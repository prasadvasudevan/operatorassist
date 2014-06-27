//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.09 at 12:38:16 PM IST 
//


package com.frontpaw.forecast.schema;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}EbbUserSetParamEvent" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}GeneralEvent"/>
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
    "ebbUserSetParamEvent",
    "generalEvent"
})
@XmlRootElement(name = "Events")
public class Events {

    @XmlElement(name = "EbbUserSetParamEvent", required = true)
    protected List<EbbUserSetParamEvent> ebbUserSetParamEvent;
    @XmlElement(name = "GeneralEvent", required = true)
    protected GeneralEvent generalEvent;

    /**
     * Gets the value of the ebbUserSetParamEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ebbUserSetParamEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEbbUserSetParamEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EbbUserSetParamEvent }
     * 
     * 
     */
    public List<EbbUserSetParamEvent> getEbbUserSetParamEvent() {
        if (ebbUserSetParamEvent == null) {
            ebbUserSetParamEvent = new ArrayList<EbbUserSetParamEvent>();
        }
        return this.ebbUserSetParamEvent;
    }

    /**
     * Gets the value of the generalEvent property.
     * 
     * @return
     *     possible object is
     *     {@link GeneralEvent }
     *     
     */
    public GeneralEvent getGeneralEvent() {
        return generalEvent;
    }

    /**
     * Sets the value of the generalEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneralEvent }
     *     
     */
    public void setGeneralEvent(GeneralEvent value) {
        this.generalEvent = value;
    }

}
