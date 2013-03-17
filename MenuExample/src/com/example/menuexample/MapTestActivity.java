package com.example.menuexample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MapTestActivity extends FragmentActivity implements LocationListener{

	private GoogleMap mMap;
	LocationManager locMan;
	String provider;
	Location loc;
	Marker myMarker;
	List<Stop> stopList;
	List<Marker> markerList;
	DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_test);
		
		locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
		db = new DatabaseHandler(this);
		db.createDatabase();
		stopList = db.getAllStops();
		markerList = new ArrayList<Marker>();
		Criteria criteria = new Criteria();
		provider = locMan.getBestProvider(criteria, false);
		loc = locMan.getLastKnownLocation(provider);
		setUpMapIfNeeded();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
		locMan.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locMan.removeUpdates(this);
	}
	
	private void setUpMapIfNeeded() {

		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		
		//Display Users current Position
		double lat = loc.getLatitude();
		double lng = loc.getLongitude();
		LatLng ll = new LatLng(lat,lng);
		myMarker = mMap.addMarker(new MarkerOptions()
		.position(ll)
		.title("test")
		.snippet("what?")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_default)));
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ll, 13);
	    mMap.animateCamera(cameraUpdate);
	    
	    
	    //Display Stops for route	    
	    if (stopList != null)
	    {
	    	for(Stop s : stopList)
	    	{
	    		Marker m = mMap.addMarker(new MarkerOptions()
	    		.position(new LatLng(s.getLat(),s.getLng()))
	    		.title(s.getName())
	    		.snippet("Next Departure : " + calToString(db.getNextBusFrom(s.getID())))
	    		.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_small)));
	    		markerList.add(m);

	    	}
	    	
	    }	    
 
	}

	@Override
	
	public void onLocationChanged(Location location) {
		myMarker.remove();
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		LatLng ll = new LatLng(lat,lng);
		myMarker = mMap.addMarker(new MarkerOptions()
		.position(ll)
		.title("test")
		.snippet("what?")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_default)));
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ll, 15);
	    mMap.animateCamera(cameraUpdate);
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {

		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}
	
	public String calToString(long time) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(time);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String formattedTime = df.format(cal1.getTime());

		return formattedTime;
	}
}
