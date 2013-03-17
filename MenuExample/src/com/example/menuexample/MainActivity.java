package com.example.menuexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageButton imgbtn = (ImageButton)findViewById(R.id.imageButton2);
		imgbtn.setBackgroundColor(Color.TRANSPARENT);
		imgbtn = (ImageButton)findViewById(R.id.ImageButton01);
		imgbtn.setBackgroundColor(Color.TRANSPARENT);
		imgbtn = (ImageButton)findViewById(R.id.ImageButton02);
		imgbtn.setBackgroundColor(Color.TRANSPARENT);
		imgbtn = (ImageButton)findViewById(R.id.ImageButton03);
		imgbtn.setBackgroundColor(Color.TRANSPARENT);
		
		addImgButtonListener((ImageButton)findViewById(R.id.imageButton2), new Intent(getApplicationContext(), BusStopActivity.class));
		addImgButtonListener((ImageButton)findViewById(R.id.ImageButton01), new Intent(getApplicationContext(), BusStopActivity.class));
		addImgButtonListener((ImageButton)findViewById(R.id.ImageButton02), new Intent(getApplicationContext(), MapTestActivity.class));
		addImgButtonListener((ImageButton)findViewById(R.id.ImageButton03), new Intent(getApplicationContext(), BusStopActivity.class));
		
		

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
	
	public void addImgButtonListener(ImageButton button , final Intent intent)
	{
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(intent);
			}
		});
	}

}
