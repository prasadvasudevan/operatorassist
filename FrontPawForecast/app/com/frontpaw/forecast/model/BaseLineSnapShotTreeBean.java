/**
 * 
 */
package com.frontpaw.forecast.model;

import java.util.ArrayList;
import java.util.List;

import com.frontpaw.forecast.schema.Param;

/**
 * @author Project
 *
 */
public class BaseLineSnapShotTreeBean {
	
	
	private String name;
	
	private String type;
	
	private String value;
	
	private List<BaseLineSnapShotTreeBean> childBeans = new ArrayList<BaseLineSnapShotTreeBean>();
	
	private List<BaseLineSnapShotTreeBean> measures;
	
	private List<Param> params;
	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseLineSnapShotTreeBean [name=" + name + ", type=" + type
				+ ", value=" + value + ", childBeans=" + childBeans
				+ ", measures=" + measures + ", params=" + params + "]";
	}

	/**
	 * @return the measures
	 */
	public List<BaseLineSnapShotTreeBean> getMeasures() {
		return measures;
	}

	/**
	 * @param measures the measures to set
	 */
	public void setMeasures(List<BaseLineSnapShotTreeBean> measures) {
		this.measures = measures;
	}

	/**
	 * @return the params
	 */
	public List<Param> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(List<Param> params) {
		this.params = params;
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

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the childBeans
	 */
	public List<BaseLineSnapShotTreeBean> getChildBeans() {
		return childBeans;
	}

	/**
	 * @param childBeans the childBeans to set
	 */
	public void setChildBeans(List<BaseLineSnapShotTreeBean> childBeans) {
		this.childBeans = childBeans;
	}
	
	

}
