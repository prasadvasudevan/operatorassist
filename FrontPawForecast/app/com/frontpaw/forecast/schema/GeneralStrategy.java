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
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}Params"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}StrategyTiming"/>
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
    "params",
    "strategyTiming"
})
@XmlRootElement(name = "GeneralStrategy")
public class GeneralStrategy {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "UserName", required = true)
    protected String userName;
    @XmlElement(name = "Params", required = true)
    protected Params params;
    @XmlElement(name = "StrategyTiming", required = true)
    protected StrategyTiming strategyTiming;

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
     * Gets the value of the params property.
     * 
     * @return
     *     possible object is
     *     {@link Params }
     *     
     */
    public Params getParams() {
        return params;
    }

    /**
     * Sets the value of the params property.
     * 
     * @param value
     *     allowed object is
     *     {@link Params }
     *     
     */
    public void setParams(Params value) {
        this.params = value;
    }

    /**
     * Gets the value of the strategyTiming property.
     * 
     * @return
     *     possible object is
     *     {@link StrategyTiming }
     *     
     */
    public StrategyTiming getStrategyTiming() {
        return strategyTiming;
    }

    /**
     * Sets the value of the strategyTiming property.
     * 
     * @param value
     *     allowed object is
     *     {@link StrategyTiming }
     *     
     */
    public void setStrategyTiming(StrategyTiming value) {
        this.strategyTiming = value;
    }

}
