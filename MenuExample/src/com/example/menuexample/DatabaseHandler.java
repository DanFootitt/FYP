package com.example.menuexample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "test6.db";
	private static final String TABLE_JOURNEY = "JOURNEY_TABLE";
	private static final String TABLE_ROUTE = "ROUTE_TABLE";
	private static final String TABLE_STOP = "STOP_TABLE";
	private static String DB_LOC = "";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String CREATE_STOP_TABLE = "CREATE TABLE STOP_TABLE (STOP_ID integer primary key autoincrement, STOP_NAME text not null, GPS_LOC text not null)";
		String CREATE_ROUTE_TABLE = "CREATE TABLE ROUTE_TABLE (ROUTE_ID integer primary key autoincrement, ROUTE_NAME text not null, WHEELCHAIR_ACCESS integer, PUSHCHAIR_ACCESS integer)";
		String CREATE_JOURNEY_TABLE = "CREATE TABLE JOURNEY_TABLE (ROUTE_ID integer, DAY text not null, RUN_NO integer, STOP_ID integer,TIME integer)";
		
		db.execSQL(CREATE_STOP_TABLE);
		db.execSQL(CREATE_ROUTE_TABLE);
		db.execSQL(CREATE_JOURNEY_TABLE);
		DB_LOC = db.getPath();
	}
	
	public String getDatabaseLocation(){
		
		return DB_LOC;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNEY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOP);
		onCreate(db);
	}

	void addJourney(Journey journey) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("ROUTE_ID", journey.getRouteID());
		values.put("DAY", journey.getDay());
		values.put("RUN_NO", journey.getRun());
		values.put("STOP_ID", journey.getStop());
		values.put("TIME", journey.getTime());
		
		db.insert(TABLE_JOURNEY, null, values);
		db.close();
	}
	
	void addRoute(Route route) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("ROUTE_NAME", route.getName());
		values.put("WHEELCHAIR_ACCESS", route.getWheelchair());
		values.put("PUSHCHAIR_ACCESS", route.getPushchair());

		db.insert(TABLE_ROUTE, null, values);
		db.close();
	}
	
	void addStop(Stop stop) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("STOP_NAME", stop.getName());
		values.put("GPS_LOC", stop.getLocation());

		db.insert(TABLE_STOP, null, values);
		db.close();
	}
	
	List<Stop> getAllStops(){
		List<Stop> stopList = new ArrayList<Stop>();
		
		String selectQuery = "SELECT  * FROM " + TABLE_STOP;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Stop stop = new Stop();
				stop.setID(cursor.getInt(0));
				stop.setName(cursor.getString(1));
				stop.setLocation(cursor.getString(2));
				stopList.add(stop);
			} while (cursor.moveToNext());
		}
		
		
		return stopList;
	}
	
	List<Route> getAllRoutes(){
		List<Route> routeList = new ArrayList<Route>();
		
		String selectQuery = "SELECT  * FROM " + TABLE_ROUTE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Route route = new Route();
				route.setID(cursor.getInt(0));
				route.setName(cursor.getString(1));
				route.setWheelchair(cursor.getInt(2));
				route.setPushchair(cursor.getInt(3));
				routeList.add(route);
			} while (cursor.moveToNext());
		}
		
		return routeList;
	}
	
	List<Journey> getAllJourneys(){
		List<Journey> journeyList = new ArrayList<Journey>();
		
		String selectQuery = "SELECT  * FROM " + TABLE_JOURNEY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Journey j = new Journey();
				j.setRouteID(cursor.getInt(0));
				j.setDay(cursor.getString(1));
				j.setRun(cursor.getInt(2));
				j.setStop(cursor.getInt(3));
				j.setTime(cursor.getLong(4));
				journeyList.add(j);
			} while (cursor.moveToNext());
		}
		
		
		return journeyList;
	}
	
	List<JourneyResult> getJourneyResults(int departID, int arriveID, String day, long time){
		
		List<JourneyResult> jresult = new ArrayList<JourneyResult>();
		Calendar c = Calendar.getInstance();
		
		String sql = "SELECT " +
					"ROUTE_TABLE.ROUTE_NAME, " +
					 "JOURNEY_TABLE.RUN_NO, " +
					 "JOURNEY_TABLE.TIME AS DEP_TIME, " +
					 "DEP.STOP_NAME AS DEP_NAME, " +
					 "B.TIME AS ARR_TIME, " +
					 "ARR.STOP_NAME AS ARR_NAME " +
					 "FROM JOURNEY_TABLE " + "" +
					 "INNER JOIN JOURNEY_TABLE AS B ON JOURNEY_TABLE.RUN_NO = B.RUN_NO " +
					 "LEFT JOIN ROUTE_TABLE ON JOURNEY_TABLE.ROUTE_ID = ROUTE_TABLE.ROUTE_ID " +
					 "LEFT JOIN STOP_TABLE AS DEP ON JOURNEY_TABLE.STOP_ID = DEP.STOP_ID " +
					 "LEFT JOIN STOP_TABLE AS ARR ON B.STOP_ID = ARR.STOP_ID " +
					 "WHERE DEP.STOP_ID = " + departID + " AND ARR.STOP_ID = " + arriveID + 
					 " AND JOURNEY_TABLE.DAY = '" + day + "'";
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor.moveToFirst()) {
			do {
				JourneyResult j = new JourneyResult();
				j.routeName = cursor.getString(0);
				j.run = cursor.getInt(1);
				j.departTime = cursor.getLong(2);
				j.departStop = cursor.getString(3);
				j.arrivalTime = cursor.getLong(4);
				j.arrivalStop = cursor.getString(5);
				jresult.add(j);
				} while (cursor.moveToNext());
		}
		return jresult;
	}
	
	public String calToString(long time) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(time);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String formattedTime = df.format(cal1.getTime());

		return formattedTime;
	}
}