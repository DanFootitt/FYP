package com.example.menuexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addButtonListener((Button)findViewById(R.id.BusStopButton), new Intent(getApplicationContext(), BusStopActivity.class));
		addButtonListener((Button)findViewById(R.id.SearchButton), new Intent(getApplicationContext(), SearchBusStopActivity.class));
		addButtonListener((Button)findViewById(R.id.SettingsButton), new Intent(getApplicationContext(), BusStopActivity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void addButtonListener(Button button , final Intent intent)
	{
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(intent);
			}
		});
	}

}
