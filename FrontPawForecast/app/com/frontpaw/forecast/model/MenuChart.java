/**
 * 
 */
package com.frontpaw.forecast.model;

import java.util.List;

/**
 * @author Project
 *
 */
public class MenuChart {

	/**
	 * 
	 */
	public MenuChart() {
		// TODO Auto-generated constructor stub
	}
	
	private List<Chart> charts ;
	
	private List<Measures> menuMeasures;

	public List<Chart> getCharts() {
		return charts;
	}

	public void setCharts(List<Chart> charts) {
		this.charts = charts;
	}

	public List<Measures> getMenuMeasures() {
		return menuMeasures;
	}

	public void setMenuMeasures(List<Measures> menuMeasures) {
		this.menuMeasures = menuMeasures;
	}

}
