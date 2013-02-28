package com.example.menuexample;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Route implements Serializable{

	int _id;
	String _routeName;
	int _wheelchair;
	int _pushchair;
	
	public Route(String name, int wchair, int pchair){
		this._routeName = name;
		this._wheelchair = wchair;
		this._pushchair = pchair;
	}
	
	public Route(){
		
	}
	
	public void setName(String name){
		this._routeName = name;
	}
	
	public String getName(){
		return this._routeName;
	}
	
	public void setWheelchair(int wchair){
		this._wheelchair = wchair;
	}
	
	public int getWheelchair(){
		return this._wheelchair;
	}
	
	public void setPushchair(int pchair){
		this._pushchair = pchair;
	}
	
	public int getPushchair(){
		return this._pushchair;
	}
	
	public void setID(int id){
		this._id = id;
	}
	
	public int getID(){
		return this._id;
	}
	
}
