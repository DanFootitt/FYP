package com.example.menuexample;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TakeMeThereActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_me_there);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.take_me_there, menu);
		return true;
	}

}
