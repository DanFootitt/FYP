package com.example.menuexample;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JourneyResult implements Serializable{
	
	String routeName;
	int run;
	String arrivalStop;
	long arrivalTime;
	String departStop;
	long departTime;
	String day;
	int walkingTime;
	double destLat;
	double destLong;
		
	
	public JourneyResult(){
		
		
	}
	
	public void setRouteName(String route)
	{
		this.routeName = route;
	}
	
	public void setRun(int run)
	{
		this.run = run;		
	}
	
	public void setDay(String run)
	{
		this.day = run;		
	}
	
	public void setArrivalStop(String arr)
	{
		this.arrivalStop = arr;		
	}
	
	public void setArrivalTime(long arrt)
	{
		this.arrivalTime = arrt;
	}
	
	public void setDepartStop(String dep)
	{
		this.departStop = dep;
	}
	
	public void setDepartTime(int dept)
	{
		this.departTime = dept;
	}
	
	public void setWalkingTime(int wt)
	{
		this.walkingTime = wt;
	}
	
	public void setLng(double lng)
	{
		this.destLong = lng;
	}
	
	public void setLat(double lat)
	{
		this.destLat  = lat;
	}
	
	

}
