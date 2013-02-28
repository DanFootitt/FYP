package com.example.menuexample;

import java.io.File;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class BusStopActivity extends Activity {


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bus_stop, menu);
		return true;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_stop);

		DatabaseHandler db = new DatabaseHandler(this);

		/*
		db.addStop(new Stop("Burton Street", "UNKNOWN"));
		db.addStop(new Stop("Maid Marian Way", "UNKNOWN"));
		db.addStop(new Stop("Train Station", "UNKNOWN"));
		db.addStop(new Stop("Trent Bridge", "UNKNOWN"));
		db.addStop(new Stop("Wilford Green", "UNKNOWN"));
		db.addStop(new Stop("Fabis Drive", "UNKNOWN"));
		db.addStop(new Stop("NTU Clifton Campus", "UNKNOWN"));
		
		db.addRoute(new Route("Unilink 4",1,1));
		db.addRoute(new Route("N4",1,1));
		
		
		Calendar c  = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 20);
		c.set(Calendar.MINUTE, 00);
		db.addJourney(new Journey(1, "Thursday", 1, 1, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 04);
		db.addJourney(new Journey(1, "Thursday", 1, 2, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 06);
		db.addJourney(new Journey(1, "Thursday", 1, 3, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 10);
		db.addJourney(new Journey(1, "Thursday", 1, 4, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 15);
		db.addJourney(new Journey(1, "Thursday", 1, 5, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 20);
		db.addJourney(new Journey(1, "Thursday", 1, 6, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 26);
		db.addJourney(new Journey(1, "Thursday", 1, 7, c.getTimeInMillis())); 
		
		c.set(Calendar.HOUR_OF_DAY, 20);
		c.set(Calendar.MINUTE, 15);
		db.addJourney(new Journey(1, "Thursday", 2, 1, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 19);
		db.addJourney(new Journey(1, "Thursday", 2, 2, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 21);
		db.addJourney(new Journey(1, "Thursday", 2, 3, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 25);
		db.addJourney(new Journey(1, "Thursday", 2, 4, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 30);
		db.addJourney(new Journey(1, "Thursday", 2, 5, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 35);
		db.addJourney(new Journey(1, "Thursday", 2, 6, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 41);
		db.addJourney(new Journey(1, "Thursday", 2, 7, c.getTimeInMillis())); 
		
		c.set(Calendar.HOUR_OF_DAY, 21);
		c.set(Calendar.MINUTE, 00);
		db.addJourney(new Journey(1, "Thursday", 3, 1, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 04);
		db.addJourney(new Journey(1, "Thursday", 3, 2, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 06);
		db.addJourney(new Journey(1, "Thursday", 3, 3, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 10);
		db.addJourney(new Journey(1, "Thursday", 3, 4, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 15);
		db.addJourney(new Journey(1, "Thursday", 3, 5, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 20);
		db.addJourney(new Journey(1, "Thursday", 3, 6, c.getTimeInMillis()));
		c.set(Calendar.MINUTE, 26);
		db.addJourney(new Journey(1, "Thursday", 3, 7, c.getTimeInMillis())); 
		*/
		
		List<Stop> stopList = db.getAllStops();
		List<Route> routeList = db.getAllRoutes();
		List<Journey> journeyList = db.getAllJourneys();
		
		addItemsOnSpinner((Spinner) findViewById(R.id.Spinner1), stopList);
		addItemsOnSpinner((Spinner) findViewById(R.id.Spinner2), stopList);
		addSpinnerLister(db);
			
			
		for (Stop  s : stopList){
			String stopInfo = "Stop id : " + Integer.toString(s.getID()) + " Stop Name : " + s.getName() + "GPS : " + s.getLocation();
			Log.d("Stop", stopInfo);
		}
		
		for (Route  r : routeList){
			String routeinfo = "Route id : " + Integer.toString(r.getID()) + " Stop Name : " + r.getName();
			Log.d("Route", routeinfo);
		}
		
		for (Journey  j : journeyList){
			String jinfo = "Route id : " + Integer.toString(j.getRouteID()) + " Day : " + j.getDay() + " Run number : " + Integer.toString(j.getRun()) + 
					" Stop : " + Integer.toString(j.getStop()) + " Time : " + calToString(j.getTime());
			Log.d("Route", jinfo);
		}
		

		addButtonListener((Button)findViewById(R.id.stopSearchButton), new Intent(getApplicationContext(), ResultsActivity.class), db, stopList);
			

	}

	public String calToString(long time) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(time);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String formattedTime = df.format(cal1.getTime());

		return formattedTime;
	}

	public void addItemsOnSpinner(Spinner spinner, List<Stop> stopList) {

		
		List<String> stops = new ArrayList<String>();
		
		for(Stop s : stopList){
			stops.add(s.getName());
		}
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stops);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

	}

	public void addSpinnerLister(final DatabaseHandler db) {
		final Spinner spinner = (Spinner) findViewById(R.id.Spinner1);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int i = spinner.getSelectedItemPosition();
				Calendar cal = Calendar.getInstance();
				//String info = "The next bus to arrive at " + spinner.getItemAtPosition(i).toString() + " will be at : " + calToString(db.getNextBusTime(cal.getTimeInMillis(), spinner.getItemAtPosition(i).toString()));
				TextView textview = (TextView) findViewById(R.id.textView1);
				//textview.setText(info);
				
				//String info2 = "You Have " + calToString(db.getNextBus(cal.getTimeInMillis(), spinner.getItemAtPosition(i).toString()));
			    TextView textview2 = (TextView) findViewById(R.id.textView2);
				//textview2.setText(info2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	}
	
	public void addButtonListener(Button button , final Intent intent, final DatabaseHandler db, final List<Stop> stops)
	{
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Calendar c1 = Calendar.getInstance();
								
				List<JourneyResult> jresult= db.getJourneyResults(getBusStopId(getSelectedSpinnerItem(R.id.Spinner1),stops),getBusStopId(getSelectedSpinnerItem(R.id.Spinner2),stops),"Thursday", c1.getTimeInMillis());
				
				intent.putExtra("searchResultOne", jresult.get(0));
				intent.putExtra("searchResultTwo", jresult.get(1));
				intent.putExtra("searchResultThree", jresult.get(2));
				startActivity(intent);
			}
		});
	}
	
	String getSelectedSpinnerItem(int id){
		Spinner spinner = (Spinner) findViewById(id);
		int i = spinner.getSelectedItemPosition();
		return spinner.getItemAtPosition(i).toString();
	}
	
	int getBusStopId(String stop, List<Stop> stops)
	{
		for(Stop s : stops)
		{
			if (s.getName().toLowerCase().contains(stop.toLowerCase())){
				return s.getID();
			}
			
		}
		
		return 0;
	}
	
	

}
