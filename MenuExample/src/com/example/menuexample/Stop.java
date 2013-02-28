package com.example.menuexample; 

import java.io.Serializable;
 
@SuppressWarnings("serial")
public class Stop implements Serializable{
	 
	int _id;
    String _name;
    String _gpsLoc;
 

    public Stop(){
 
    }

    public Stop(String name, String gps){
        this._name = name;
        this._gpsLoc = gps;
    }


    public String getName(){
        return this._name;
    }
 
    public void setName(String name){
        this._name = name;
    }
 
    public String getLocation(){
        return this._gpsLoc;
    }
 
    public void setLocation(String location){
        this._gpsLoc = location;
    }
    
    public int getID(){
        return this._id;
    }
 
    public void setID(int id){
        this._id = id;
    }
}