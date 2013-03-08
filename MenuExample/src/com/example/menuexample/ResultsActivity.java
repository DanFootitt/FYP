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
		
		if (extras!=null){
			jresults.add((JourneyResult) extras.getSerializable("searchResultOne"));
			jresults.add((JourneyResult) extras.getSerializable("searchResultTwo"));
			jresults.add((JourneyResult) extras.getSerializable("searchResultThree"));
		}
		int hour = 3600000;
		
		TextView textview1_1 = (TextView) findViewById(R.id.textView1_1);
		textview1_1.setText(calToString(jresults.get(0).departTime));
		
		TextView textview1_2 = (TextView) findViewById(R.id.TextView1_2);
		textview1_2.setText(calToString(jresults.get(0).arrivalTime));
		
		TextView textview1_3 = (TextView) findViewById(R.id.TextView1_3);
		textview1_3.setText(calToString((jresults.get(0).arrivalTime-jresults.get(0).departTime) - hour));
		
		TextView textview2_1 = (TextView) findViewById(R.id.TextView2_1);
		textview2_1.setText(calToString(jresults.get(1).departTime));
		
		TextView textview2_2 = (TextView) findViewById(R.id.TextView2_2);
		textview2_2.setText(calToString(jresults.get(1).arrivalTime));
		
		TextView textview2_3 = (TextView) findViewById(R.id.TextView2_3);
		textview2_3.setText(calToString((jresults.get(1).arrivalTime-jresults.get(1).departTime) - hour));
		
		TextView textview3_1 = (TextView) findViewById(R.id.TextView3_1);
		textview3_1.setText(calToString(jresults.get(2).departTime));
		
		TextView textview3_2 = (TextView) findViewById(R.id.TextView3_2);
		textview3_2.setText(calToString(jresults.get(2).arrivalTime));
		
		TextView textview3_3 = (TextView) findViewById(R.id.TextView3_3);
		textview3_3.setText(calToString((jresults.get(2).arrivalTime-jresults.get(2).departTime) - hour));
		
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
