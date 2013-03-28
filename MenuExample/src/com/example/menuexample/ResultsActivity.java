package com.example.menuexample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.net.NetworkInfo.DetailedState;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		Bundle extras = getIntent().getExtras();
		List<JourneyResult> jresults = new ArrayList<JourneyResult>();
		Boolean isWalking = false;
		
		if (extras!=null){
			jresults.add((JourneyResult) extras.getSerializable("searchResultOne"));
			jresults.add((JourneyResult) extras.getSerializable("searchResultTwo"));
			jresults.add((JourneyResult) extras.getSerializable("searchResultThree"));
			isWalking = extras.getBoolean("isWalking");
		}
		int hour = 3600000;
		
		if (isWalking)
		{
		
		// ROW 1
			
		TextView textview1_dep = (TextView) findViewById(R.id.TextView1_dep);
		textview1_dep.setText(jresults.get(0).departStop);
		
		
		TextView textview1_arr = (TextView) findViewById(R.id.TextView1_arr);
		textview1_arr.setText(jresults.get(0).arrivalStop);
		
		TextView textview1_t = (TextView) findViewById(R.id.TextView1_time);
		textview1_t.setText("Travel Time");
		
		TextView textview1_w = (TextView) findViewById(R.id.TextView1_walk);
		textview1_w.setText("Walking Time");
			
		
		TextView textview1_1 = (TextView) findViewById(R.id.textView1_1);
		textview1_1.setText(calToString(jresults.get(0).departTime));
		
		TextView textview1_2 = (TextView) findViewById(R.id.TextView1_2);
		textview1_2.setText(calToString(jresults.get(0).arrivalTime));
		
		TextView textview1_3 = (TextView) findViewById(R.id.TextView1_3);
		textview1_3.setText(calToString((jresults.get(0).arrivalTime-jresults.get(0).departTime) - hour));
		
		TextView textview1_4 = (TextView) findViewById(R.id.TextView1_4);
		textview1_4.setText("00:" + Integer.toString(jresults.get(0).walkingTime));
		
		
		// ROW 2
		TextView textview2_dep = (TextView) findViewById(R.id.TextView2_dep);
		textview2_dep.setText(jresults.get(1).departStop);
		
		TextView textview2_arr = (TextView) findViewById(R.id.TextView2_arr);
		textview2_arr.setText(jresults.get(1).arrivalStop);
		
		TextView textview2_t = (TextView) findViewById(R.id.TextView2_time);
		textview2_t.setText("Travel Time");
		
		TextView textview2_w = (TextView) findViewById(R.id.TextView2_walk);
		textview2_w.setText("Walking Time");
		
		TextView textview2_1 = (TextView) findViewById(R.id.TextView2_1);
		textview2_1.setText(calToString(jresults.get(1).departTime));
		
		TextView textview2_2 = (TextView) findViewById(R.id.TextView2_2);
		textview2_2.setText(calToString(jresults.get(1).arrivalTime));
		
		TextView textview2_3 = (TextView) findViewById(R.id.TextView2_3);
		textview2_3.setText(calToString((jresults.get(1).arrivalTime-jresults.get(1).departTime) - hour));
		
		TextView textview2_4 = (TextView) findViewById(R.id.TextView2_4);
		textview2_4.setText("00:" + Integer.toString(jresults.get(1).walkingTime));
		
		//ROW 3
		
		TextView textview3_dep = (TextView) findViewById(R.id.TextView3_dep);
		textview3_dep.setText(jresults.get(2).departStop);
		
		TextView textview3_arr = (TextView) findViewById(R.id.TextView3_arr);
		textview3_arr.setText(jresults.get(2).arrivalStop);
		
		TextView textview3_t = (TextView) findViewById(R.id.TextView3_time);
		textview3_t.setText("Travel Time");
		
		TextView textview3_w = (TextView) findViewById(R.id.TextView3_walk);
		textview3_w.setText("Walking Time");
		
		TextView textview3_1 = (TextView) findViewById(R.id.TextView3_1);
		textview3_1.setText(calToString(jresults.get(2).departTime));
		
		TextView textview3_2 = (TextView) findViewById(R.id.TextView3_2);
		textview3_2.setText(calToString(jresults.get(2).arrivalTime));
		
		TextView textview3_3 = (TextView) findViewById(R.id.TextView3_3);
		textview3_3.setText(calToString((jresults.get(2).arrivalTime-jresults.get(2).departTime) - hour));
	
		TextView textview3_4 = (TextView) findViewById(R.id.TextView3_4);
		textview3_4.setText("00:" + Integer.toString(jresults.get(2).walkingTime));
		}
		else{
			
			TextView textview1_dep = (TextView) findViewById(R.id.TextView3_dep);
			TextView textview1_arr = (TextView) findViewById(R.id.TextView3_arr);
			TextView textview1_t = (TextView) findViewById(R.id.TextView3_time);
			TextView textview1_w = (TextView) findViewById(R.id.TextView3_walk);
			
			TextView textview1_1 = (TextView) findViewById(R.id.textView1_1);
			textview1_1.setText(calToString(jresults.get(0).departTime));
			
			TextView textview1_2 = (TextView) findViewById(R.id.TextView1_2);
			textview1_2.setText(calToString(jresults.get(0).arrivalTime));
			
			TextView textview1_3 = (TextView) findViewById(R.id.TextView1_3);
			textview1_3.setText(calToString((jresults.get(0).arrivalTime-jresults.get(0).departTime) - hour));
			
			TextView textview2_dep = (TextView) findViewById(R.id.TextView3_dep);
			TextView textview2_arr = (TextView) findViewById(R.id.TextView3_arr);
			TextView textview2_t = (TextView) findViewById(R.id.TextView3_time);
			TextView textview2_w = (TextView) findViewById(R.id.TextView3_walk);
			
			
			TextView textview2_1 = (TextView) findViewById(R.id.TextView2_1);
			textview2_1.setText(calToString(jresults.get(1).departTime));
			
			TextView textview2_2 = (TextView) findViewById(R.id.TextView2_2);
			textview2_2.setText(calToString(jresults.get(1).arrivalTime));
			
			TextView textview2_3 = (TextView) findViewById(R.id.TextView2_3);
			textview2_3.setText(calToString((jresults.get(1).arrivalTime-jresults.get(1).departTime) - hour));
			
			TextView textview3_dep = (TextView) findViewById(R.id.TextView3_dep);
			TextView textview3_arr = (TextView) findViewById(R.id.TextView3_arr);
			TextView textview3_t = (TextView) findViewById(R.id.TextView3_time);
			TextView textview3_w = (TextView) findViewById(R.id.TextView3_walk);
			
			TextView textview3_1 = (TextView) findViewById(R.id.TextView3_1);
			textview3_1.setText(calToString(jresults.get(2).departTime));
			
			TextView textview3_2 = (TextView) findViewById(R.id.TextView3_2);
			textview3_2.setText(calToString(jresults.get(2).arrivalTime));
			
			TextView textview3_3 = (TextView) findViewById(R.id.TextView3_3);
			textview3_3.setText(calToString((jresults.get(2).arrivalTime-jresults.get(2).departTime) - hour));
		
			TextView depText = (TextView)findViewById(R.id.TextView1_dep);
			TextView arrText = (TextView)findViewById(R.id.TextView1_arr);
			
			depText.setText(jresults.get(0).departStop);
			arrText.setText(jresults.get(0).arrivalStop);
			
		}
		
		addButtonListener((Button)findViewById(R.id.more1), new Intent(getApplicationContext(), DeatiledResultActivity.class), jresults.get(0));
		addButtonListener((Button)findViewById(R.id.more2), new Intent(getApplicationContext(), DeatiledResultActivity.class), jresults.get(1));
		addButtonListener((Button)findViewById(R.id.more3), new Intent(getApplicationContext(), DeatiledResultActivity.class), jresults.get(2));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_results, menu);
		return true;
	}
	
	public String calToString(long time) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(time);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String formattedTime = df.format(cal1.getTime());

		return formattedTime;
	}
	
	public void addButtonListener(Button button , final Intent intent, final JourneyResult jr)
	{
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Calendar c  = Calendar.getInstance();
				intent.putExtra("currentTime", c.getTimeInMillis());
				intent.putExtra("journeyResult", jr);
				startActivity(intent);
			}
		});
	}

}
