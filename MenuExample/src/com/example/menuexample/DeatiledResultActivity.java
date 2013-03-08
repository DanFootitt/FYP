package com.example.menuexample;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DeatiledResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deatiled_result);
		Bundle extras = getIntent().getExtras();
		JourneyResult journeyResult = new JourneyResult();
		long currentTime = 0;
		
		if (extras!=null){
			journeyResult = (JourneyResult) extras.getSerializable("journeyResult");
			currentTime = extras.getLong("currentTime");
			
		}
		int hour = 3600000;
		
		TextView textviewRoute = (TextView) findViewById(R.id.routeText);
		textviewRoute.setText(journeyResult.routeName);
		
		TextView textviewDep = (TextView) findViewById(R.id.depText);
		textviewDep.setText(calToString(journeyResult.departTime));
		
		TextView textviewArr = (TextView) findViewById(R.id.arrText);
		textviewArr.setText(calToString(journeyResult.arrivalTime));
		
		TextView textviewLength = (TextView) findViewById(R.id.lengthText);
		textviewLength.setText(calToString(journeyResult.arrivalTime - journeyResult.departTime - hour));
		
		TextView textviewTime = (TextView) findViewById(R.id.timeText);
		textviewTime.setText(calToString((journeyResult.departTime - currentTime) - hour));
		
		TextView textviewPush = (TextView) findViewById(R.id.PushText);
		textviewPush.setText("Yes");
		
		TextView textviewWheel = (TextView) findViewById(R.id.wheelText);
		textviewWheel.setText("Yes");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_deatiled_result, menu);
		return true;
	}
	
	public String calToString(long time) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(time);
		
		
		if (cal1.get(Calendar.SECOND) >= 30)
		{
			cal1.add(Calendar.MINUTE, 1);
		}
		
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String formattedTime = df.format(cal1.getTime());

		return formattedTime;
	}
	
	public String boolConvert(boolean b)
	{
		if (b) return "Yes";
		else return "No";
		
	}

}
