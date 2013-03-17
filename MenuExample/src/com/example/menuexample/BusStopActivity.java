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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.TimePickerDialog;

public class BusStopActivity extends Activity {

	static final int TIMEPICKER_ID = 0;
	private static int hour = 0;
	private static int min = 0;

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
		//DatabaseFiller.fillDB(db);
		db.createDatabase();

		List<Stop> stopList = db.getAllStops();
		List<Route> routeList = db.getAllRoutes();
		List<Journey> journeyList = db.getAllJourneys();

		addStringsToSpinner((Spinner) findViewById(R.id.Spinner1), stopList);
		addStringsToSpinner((Spinner) findViewById(R.id.Spinner2), stopList);
		addDaysToSpinner((Spinner) findViewById(R.id.daySpinner));

		addButtonListener((Button) findViewById(R.id.stopSearchButton),
				new Intent(getApplicationContext(), ResultsActivity.class), db,
				stopList);
		
		EditText editText = (EditText)findViewById(R.id.editText1);
		editText.setFocusable(false);
		
		Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		min = c.get(Calendar.MINUTE);
		
		if (min < 10)editText.setText(hour + ":0" + min);
		else editText.setText(hour + ":" + min);

	}

	@SuppressLint("ValidFragment")
	public class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
			
		}
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			hour = view.getCurrentHour();
			min = view.getCurrentMinute();
			EditText editText = (EditText)findViewById(R.id.editText1);
			if  (min < 10) editText.setText(hour + ":0" + min);
			else editText.setText(hour + ":" + min);
			
		}
	}
	
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	}

	public String calToString(long time) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(time);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String formattedTime = df.format(cal1.getTime());

		return formattedTime;
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

	public void addDaysToSpinner(Spinner spinner) {

		List<String> days = new ArrayList<String>();

		days.add("Monday");
		days.add("Tuesday");
		days.add("Wednesday");
		days.add("Thursday");
		days.add("Friday");
		days.add("Saturday");
		days.add("Sunday");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, days);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

	}

	public void addIntsToSpinner(Spinner spinner, int start, int end, int step) {

		List<Integer> ints = new ArrayList<Integer>();

		for (int i = start; i < (end + step); i += step) {
			ints.add(i);
		}

		ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, ints);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

	}

	public void addSpinnerLister(final DatabaseHandler db) {
		final Spinner spinner = (Spinner) findViewById(R.id.Spinner1);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int i = spinner.getSelectedItemPosition();
				Calendar cal = Calendar.getInstance();

				TextView textview = (TextView) findViewById(R.id.textView1);
				TextView textview2 = (TextView) findViewById(R.id.textView2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	public void addButtonListener(Button button, final Intent intent,
			final DatabaseHandler db, final List<Stop> stops) {
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (getSelectedSpinnerItem(R.id.Spinner1) == getSelectedSpinnerItem(R.id.Spinner2))
				{
					Toast.makeText(getBaseContext(), "Origin and Destination cannot be the same", Toast.LENGTH_SHORT).show();
				} else {
					Calendar c1 = Calendar.getInstance();
	
					List<JourneyResult> jresult = db.getJourneyResults(
							getBusStopId(getSelectedSpinnerItem(R.id.Spinner1),
									stops),
							getBusStopId(getSelectedSpinnerItem(R.id.Spinner2),
									stops),
							getSelectedSpinnerItem(R.id.daySpinner),
							getTimeFromInts(hour,min,getSelectedSpinnerItem(R.id.daySpinner)));
	
					intent.putExtra("searchResultOne", jresult.get(0));
					intent.putExtra("searchResultTwo", jresult.get(1));
					intent.putExtra("searchResultThree", jresult.get(2));
					startActivity(intent);
				}
			}
		});
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

	long getTimeFromPicker(int id) {
		TimePicker tp = (TimePicker) findViewById(id);

		Calendar c = Calendar.getInstance();
		c.set(1, 1, 0);
		c.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
		c.set(Calendar.MINUTE, tp.getCurrentMinute());

		return c.getTimeInMillis();
	}

	long getTimeFromInts(int hour, int min, String day) {

		Calendar c = Calendar.getInstance();
		c.set(1, 1, 0);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, min);

		return c.getTimeInMillis();
	}

	public int getIntFromDay(String day) {

		int d = 0;
		if (day == "Monday")
			return 1;
		if (day == "Tuesday")
			return 2;
		if (day == "Wednesday")
			return 3;
		if (day == "Thursday")
			return 4;
		if (day == "Friday")
			return 5;
		if (day == "Saturday")
			return 6;
		if (day == "Sunday")
			return 7;

		return d;
	}

}
