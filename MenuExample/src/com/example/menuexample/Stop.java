package com.example.menuexample; 

import java.io.Serializable;
 
@SuppressWarnings("serial")
public class Stop implements Serializable{
	 
	int _id;
    String _name;
    double _lat;
    double _lng;
 

    public Stop(){
 
    }

    public Stop(String name, double lat, double lng){
        this._name = name;
        this._lat = lat;
        this._lng = lng;
    }


    public String getName(){
        return this._name;
    }
 
    public void setName(String name){
        this._name = name;
    }
 
    public Double getLat(){
        return this._lat;
    }
    
    public Double getLng(){
        return this._lng;
    }
 
    public void setLat(Double lat){
        this._lat = lat;
    }
    
    public void setLng(Double lng){
        this._lng = lng;
    }
    
    public int getID(){
        return this._id;
    }
 
    public void setID(int id){
        this._id = id;
    }
}