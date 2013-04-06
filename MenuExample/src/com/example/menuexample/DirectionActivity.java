package com.example.menuexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DirectionActivity extends FragmentActivity implements LocationListener{

	private GoogleMap map;
	Marker myMarker;
	Marker destMarker;
	DatabaseHandler db;
	LocationManager locMan;
	Location myLoc;
	String provider;
	String displayString;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	

 
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        Bundle extras = getIntent().getExtras();
        
        double lat = 0;
        double lng = 0;
        
		if (extras!=null){
			lat = (Double) extras.getDouble("lat");
			lng = (Double) extras.getDouble("lng");
		}
		
		LatLng destLL = new LatLng(lat, lng);
        
 
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map_dir);
        locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locMan.getBestProvider(criteria, false);
        myLoc = locMan.getLastKnownLocation(provider);
 
        map = fm.getMap();
        LatLng myLL = new LatLng(myLoc.getLatitude(),myLoc.getLongitude());

        
		myMarker = map.addMarker(new MarkerOptions()
		.position(myLL)
		.title("Your Location")
		.snippet("what?")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_default)));
		
		destMarker = map.addMarker(new MarkerOptions()
		.position(destLL)
		.title("Bus Stop")
		.snippet("what?")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_small)));
		
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myMarker.getPosition(), 15);
	    map.animateCamera(cameraUpdate);
        
        LatLng origin = myMarker.getPosition();
        LatLng dest = destMarker.getPosition();

        String url = getDirectionsUrl(origin, dest);

        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);

    }

    
	public void addButtonListener(Button button)
	{
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "THIS IS SOME INFO", Toast.LENGTH_LONG).show();
			}
		});
	}
    
    private String getDirectionsUrl(LatLng origin,LatLng dest){
    	 
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
 
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
 
        String sensor = "sensor=false";
        
        String direction_mode = "mode=walking";
 
        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+direction_mode;
 
        String output = "json";
 
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
 
        return url;
    }
    
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
 
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
 
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb  = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    
    private class DownloadTask extends AsyncTask<String, Void, String>{
 
        protected String doInBackground(String... url) {
 
            String data = "";
 
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
 
            ParserTask parserTask = new ParserTask();

            parserTask.execute(result);
        }
    }
    
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
    	 
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
 
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
 
            try{
                jObject = new JSONObject(jsonData[0]);
                JsonDirectionParser parser = new JsonDirectionParser();
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }
 
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";
 
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);
 

                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
 
                    if(j==0){ 
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){
                        duration = (String)point.get("duration");
                        continue;
                    }

                    
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
 
                    points.add(position);
                }
 
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(Color.RED);
            }

        	TextView tv_info = (TextView)findViewById(R.id.tv_direction_act);
            tv_info.setText("Distance : " + distance +  " Duration : " + duration);
            map.addPolyline(lineOptions);
            
        }
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
	
	@Override
	public void onLocationChanged(Location location) {
		myMarker.remove();
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		LatLng ll = new LatLng(lat,lng);
		myMarker = map.addMarker(new MarkerOptions()
		.position(ll)
		.title("test")
		.snippet("what?")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_default)));
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ll, 15);
	    map.animateCamera(cameraUpdate);

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
	
}
