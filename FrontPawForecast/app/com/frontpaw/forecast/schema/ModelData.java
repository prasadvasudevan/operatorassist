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
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}ModelName"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}ModelID"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}EconMetrics"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}AssumptionMetrics"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}EventMetrics"/>
 *         &lt;element ref="{http://frontpaw.com/2002/10/generatedschema}StrategyMetrics"/>
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
    "modelName",
    "modelID",
    "econMetrics",
    "assumptionMetrics",
    "eventMetrics",
    "strategyMetrics"
})
@XmlRootElement(name = "ModelData")
public class ModelData {

    @XmlElement(name = "ModelName", required = true)
    protected String modelName;
    @XmlElement(name = "ModelID", required = true)
    protected String modelID;
    @XmlElement(name = "EconMetrics", required = true)
    protected EconMetrics econMetrics;
    @XmlElement(name = "AssumptionMetrics", required = true)
    protected AssumptionMetrics assumptionMetrics;
    @XmlElement(name = "EventMetrics", required = true)
    protected EventMetrics eventMetrics;
    @XmlElement(name = "StrategyMetrics", required = true)
    protected StrategyMetrics strategyMetrics;

    /**
     * Gets the value of the modelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelName(String value) {
        this.modelName = value;
    }

    /**
     * Gets the value of the modelID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelID() {
        return modelID;
    }

    /**
     * Sets the value of the modelID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelID(String value) {
        this.modelID = value;
    }

    /**
     * Gets the value of the econMetrics property.
     * 
     * @return
     *     possible object is
     *     {@link EconMetrics }
     *     
     */
    public EconMetrics getEconMetrics() {
        return econMetrics;
    }

    /**
     * Sets the value of the econMetrics property.
     * 
     * @param value
     *     allowed object is
     *     {@link EconMetrics }
     *     
     */
    public void setEconMetrics(EconMetrics value) {
        this.econMetrics = value;
    }

    /**
     * Gets the value of the assumptionMetrics property.
     * 
     * @return
     *     possible object is
     *     {@link AssumptionMetrics }
     *     
     */
    public AssumptionMetrics getAssumptionMetrics() {
        return assumptionMetrics;
    }

    /**
     * Sets the value of the assumptionMetrics property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssumptionMetrics }
     *     
     */
    public void setAssumptionMetrics(AssumptionMetrics value) {
        this.assumptionMetrics = value;
    }

    /**
     * Gets the value of the eventMetrics property.
     * 
     * @return
     *     possible object is
     *     {@link EventMetrics }
     *     
     */
    public EventMetrics getEventMetrics() {
        return eventMetrics;
    }

    /**
     * Sets the value of the eventMetrics property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventMetrics }
     *     
     */
    public void setEventMetrics(EventMetrics value) {
        this.eventMetrics = value;
    }

    /**
     * Gets the value of the strategyMetrics property.
     * 
     * @return
     *     possible object is
     *     {@link StrategyMetrics }
     *     
     */
    public StrategyMetrics getStrategyMetrics() {
        return strategyMetrics;
    }

    /**
     * Sets the value of the strategyMetrics property.
     * 
     * @param value
     *     allowed object is
     *     {@link StrategyMetrics }
     *     
     */
    public void setStrategyMetrics(StrategyMetrics value) {
        this.strategyMetrics = value;
    }
    
    /* (non-Javadoc)
   	 * @see java.lang.Object#toString()
   	 */
   	@Override
   	public String toString() {
   		// TODO Auto-generated method stub
   		return modelName;
   	}
   	
   	 /* (non-Javadoc)
   		 * @see java.lang.Object#equals(java.lang.Object)
   		 */
   		@Override
   		public boolean equals(Object obj) {
   			// TODO Auto-generated method stub
   			return modelName.equals(obj.toString());
   		}


}