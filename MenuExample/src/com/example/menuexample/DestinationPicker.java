package com.example.menuexample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class DestinationPicker extends Activity implements LocationListener {

	LocationManager locMan;
	String provider;
	Location loc;
	List<Stop> stopList;
	TreeMap<Float, Integer> closestStops;
	TreeMap<String, JourneyResult> viableJourneys;
	List<JourneyResult> jrList;
	int speed = 50;
	DatabaseHandler db;
	int hour = 3600000;
	int twomiles = 3218;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destination_picker);

		closestStops = new TreeMap<Float, Integer>();
		viableJourneys = new TreeMap<String, JourneyResult>();
		jrList = new ArrayList<JourneyResult>();

		// Database
		db = new DatabaseHandler(this);
		db.createDatabase();
		stopList = db.getAllStops();
		addStringsToSpinner((Spinner) findViewById(R.id.dpSpinner), stopList);

		// Location
		locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locMan.getBestProvider(criteria, false);
		loc = locMan.getLastKnownLocation(provider);

		getClosestBusStops(loc);

		
		printJourneys(viableJourneys);
		
		addButtonListener((Button)findViewById(R.id.dpSearchButton), new Intent(getApplicationContext(), ResultsActivity.class));
	}

	public void addButtonListener(Button button , final Intent intent)
	{
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				getDepartures(closestStops);
				
				for(JourneyResult jr : jrList)
				{
					viableJourneys.put(calToString(jr.arrivalTime) + " " + jr.departStop, jr);
				}
				List <JourneyResult> jr = getListofDepartures(viableJourneys);
				
				intent.putExtra("isWalking", true);					
				intent.putExtra("searchResultOne", jr.get(0));
				intent.putExtra("searchResultTwo", jr.get(1));
				intent.putExtra("searchResultThree", jr.get(2));
				startActivity(intent);
			}
		});
	}
	
	
	public List<JourneyResult> getListofDepartures(Map m)
	{
		List<JourneyResult> jr = new ArrayList<JourneyResult>();
		
		for(int i = 0; i < 3; i++)
		{
			JourneyResult j = (JourneyResult) m.values().toArray()[i];
			jr.add(j);
		}
		
		return jr;
	}
	
	public void getDepartures(Map stops) {
			
		Iterator it = stops.entrySet().iterator();
		Calendar cal = Calendar.getInstance();

		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if ((Float)pairs.getKey() < twomiles){
				db.getJourney(
						(Integer)pairs.getValue(), 
						getBusStopId(getSelectedSpinnerItem(R.id.dpSpinner), stopList),
						(Float)pairs.getKey(), 
						jrList);
			}
			it.remove();
		}

	}

	String getSelectedSpinnerItem(int id) {
		Spinner spinner = (Spinner) findViewById(id);
		int i = spinner.getSelectedItemPosition();
		return spinner.getItemAtPosition(i).toString();
	}

	
	
	int getBusStopId(String stop, List<Stop> stops) {
		for (Stop s : stops) {
			
			if (s.getName().toLowerCase().contains(stop.toLowerCase())) {
				return s.getID();
			}

		}
		return 0;
	}

	public void printClosestStops(Map stops) {
		Iterator it = stops.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			Log.d("closest", pairs.getKey().toString() + " , "
					+ pairs.getValue().toString());
			it.remove();
		}
	}
	
	public void printJourneys(Map j) {
		Iterator it = j.entrySet().iterator();
		int count = 0;
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			JourneyResult jr = (JourneyResult)pairs.getValue();
			
			
			Calendar c = Calendar.getInstance();
			c.set(1, 1, 0);
			int time = (int) (getDistanceFromStop(jr.departStop) / 50);
			c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + time );
			long t =  c.getTimeInMillis();
			
			Log.d("results", calToString(jr.arrivalTime) + " , " + jr.departStop + " , "
					+ calToString((jr.departTime - t) - hour) + " " + jr.walkingTime);
			it.remove();
		}
	}

	public void getClosestBusStops(Location loc) {
		Location stopLoc = new Location(provider);
		HashMap<Float, Integer> stops = new HashMap<Float, Integer>();

		for (Stop s : stopList) {
			stopLoc.setLatitude(s.getLat());
			stopLoc.setLongitude(s.getLng());
			float dist = stopLoc.distanceTo(loc);
			stops.put(dist, s.getID());
		}

		closestStops = new TreeMap<Float, Integer>(stops);
	}

	Float getDistanceFromStop(String stopName)
	{
		Location stopLoc = new Location(provider);
		Stop s = db.getStop(stopName);
		stopLoc.setLatitude(s.getLat());
		stopLoc.setLongitude(s.getLng());
		
		return stopLoc.distanceTo(loc);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.destination_picker, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		locMan.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locMan.removeUpdates(this);
	}

	public void addStringsToSpinner(Spinner spinner, List<Stop> stopList) {

		List<String> stops = new ArrayList<String>();

		for (Stop s : stopList) {
			stops.add(s.getName());
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, stops);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

	}

	public String calToString(long time) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(time);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String formattedTime = df.format(cal1.getTime());

		return formattedTime;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
