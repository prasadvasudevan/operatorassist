package com.frontpaw.forecast.model;

import java.util.List;

import com.frontpaw.forecast.schema.Measure;

public class Measures {

	public List<Measure> measures;
	
	/**
	 * @return the measures
	 */
	public List<Measure> getMeasures() {
		return measures;
	}

	/**
	 * @param measures the measures to set
	 */
	public void setMeasures(List<Measure> measures) {
		this.measures = measures;
	}

	public Measures(List<Measure> measuresArg) {
		
		this.measures=measuresArg;
	}

}
