package com.example.menuexample;

public class Journey {

	int _routeID;
	String _day;
	int _run;
	int _stopID;
	long _time;
	
	public Journey(){}
	
	public Journey(int routeID, String day, int run, int stopID, long time){
		
		this._routeID = routeID;
		this._day = day;
		this._run = run;
		this._stopID = stopID;
		this._time = time;
		
	}
	
	public void setRouteID(int routeID){
		this._routeID = routeID;		
	}
	
	public int getRouteID(){
		return this._routeID;
	}
	
	public void setDay(String day){
		this._day = day;		
	}
	
	public String getDay(){
		return this._day;
	}
	
	public void setRun(int run){
		this._run = run;		
	}
	
	public int getRun(){
		return this._run;
	}
	
	public void setStop(int stop){
		this._stopID = stop;		
	}
	
	public int getStop(){
		return this._stopID;
	}
	
	public void setTime(long time){
		this._time = time;		
	}
	
	public long getTime(){
		return this._time;
	}
	
	
}
