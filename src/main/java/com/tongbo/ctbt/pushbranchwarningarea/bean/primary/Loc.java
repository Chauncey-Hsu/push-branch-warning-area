package com.tongbo.ctbt.pushbranchwarningarea.bean.primary;

public class Loc {

	private String type;
	private double [] coordinates;
	
	
	public Loc(double[] coordinates) {
		super();
		this.coordinates = coordinates;
		this.type="Point";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	} 
	
	
	
}
