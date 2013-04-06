package com.example.menuexample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static DatabaseHandler _dbHandler;
	private static Context _context;
	private static final String DATABASE_PATH = "/data/data/com.example.menuexample/databases/";
	private static final String DATABASE_NAME = "timetable_sqlite_v3";
	private static final String TABLE_JOURNEY = "JOURNEY_TABLE";
	private static final String TABLE_ROUTE = "ROUTE_TABLE";
	private static final String TABLE_STOP = "STOP_TABLE";
	private SQLiteDatabase _db;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, 1);
		this._context = context;
	}

	public static DatabaseHandler getInstance(Context context) {
		if (_dbHandler == null) {

			_dbHandler = new DatabaseHandler(context);
		}

		return _dbHandler;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		//String CREATE_STOP_TABLE = "CREATE TABLE STOP_TABLE (STOP_ID integer primary key autoincrement, STOP_NAME text not null, GPS_LAT real not null, GPS_LNG real not null)";
		//String CREATE_ROUTE_TABLE = "CREATE TABLE ROUTE_TABLE (ROUTE_ID integer primary key autoincrement, ROUTE_NAME text not null, WHEELCHAIR_ACCESS integer, PUSHCHAIR_ACCESS integer)";
		//String CREATE_JOURNEY_TABLE = "CREATE TABLE JOURNEY_TABLE (ROUTE_ID integer, DAY text not null, RUN_NO integer, STOP_ID integer,TIME integer)";

		//db.execSQL(CREATE_STOP_TABLE);
		//db.execSQL(CREATE_ROUTE_TABLE);
		//db.execSQL(CREATE_JOURNEY_TABLE);
		
		createDatabase();
	}
	
	public void createDatabase() {

		
		boolean dbExists = checkDatabase();

		if (!dbExists) {

			this.getReadableDatabase();

			try {
				copyDatabase();
			} catch (IOException e) {
				throw new Error("Error Copying Database");
			}
		}
	}
	
	public boolean checkDatabase() {
		SQLiteDatabase checkDB = null;

		try {
			String dbPath = DATABASE_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(dbPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLException e) {
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}
	
	public void copyDatabase()throws IOException{
		
		InputStream dbInput = _context.getAssets().open(DATABASE_NAME);
		String outputFilePath = DATABASE_PATH + DATABASE_NAME;
		OutputStream dbOutput = new FileOutputStream(outputFilePath);
		
		byte[] buffer = new byte[1024];
		int length;
		
		while((length = dbInput.read(buffer)) > 0)
		{
			dbOutput.write(buffer, 0, length);
		}
		
		dbOutput.flush();
		dbOutput.close();
		dbInput.close();
		
	}
	
	public void openDatabase() throws SQLException
	{
		_db = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);		
	}


	List<Stop> getAllStops() {
		List<Stop> stopList = new ArrayList<Stop>();

		String selectQuery = "SELECT  * FROM " + TABLE_STOP;

		openDatabase();
		Cursor cursor = _db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Stop stop = new Stop();
				stop.setID(cursor.getInt(0));
				stop.setName(cursor.getString(1));
				stop.setLat(cursor.getDouble(2));
				stop.setLng(cursor.getDouble(3));
				stopList.add(stop);
			} while (cursor.moveToNext());
		}

		return stopList;
	}
	
	Stop getStop(String name)
	{
		Stop s = new Stop();
		
		String selectQuery = "SELECT  * FROM " + TABLE_STOP +
							 " WHERE STOP_NAME = '" + name + "'";

		openDatabase();
		Cursor cursor = _db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				s.setID(cursor.getInt(0));
				s.setName(cursor.getString(1));
				s.setLat(cursor.getDouble(2));
				s.setLng(cursor.getDouble(3));
			} while (cursor.moveToNext());
		}
		
		return s;
	}

	List<Route> getAllRoutes() {
		List<Route> routeList = new ArrayList<Route>();

		String selectQuery = "SELECT  * FROM " + TABLE_ROUTE;

		openDatabase();
		Cursor cursor = _db.rawQuery(selectQuery, null);

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

	List<Journey> getAllJourneys() {
		List<Journey> journeyList = new ArrayList<Journey>();

		String selectQuery = "SELECT  * FROM " + TABLE_JOURNEY;

		openDatabase();
		Cursor cursor = _db.rawQuery(selectQuery, null);

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

	public void getJourney(int dep, int dest, float dist, List<JourneyResult> jrList)
	{
		
		Calendar c = Calendar.getInstance();
		c.set(1, 1, 0);
		int time = (int) (dist / 50);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + time );
		long t =  c.getTimeInMillis();
		String day = getDayFromInt(c.get(Calendar.DAY_OF_WEEK));
		
		
		String sql = "SELECT "
				+ "ROUTE_TABLE.ROUTE_NAME, "
				+ "JOURNEY_TABLE.DAY, "
				+ "JOURNEY_TABLE.RUN_NO, "
				+ "JOURNEY_TABLE.TIME AS DEP_TIME, "
				+ "DEP.STOP_NAME AS DEP_NAME, "
				+ "B.TIME AS ARR_TIME, "
				+ "ARR.STOP_NAME AS ARR_NAME, "
				+ "JOURNEY_TABLE.DAY, "
				+ "DEP.GPS_LAT, "
				+ "DEP.GPS_LNG "
				+ "FROM JOURNEY_TABLE "
				+ ""
				+ "INNER JOIN JOURNEY_TABLE AS B ON JOURNEY_TABLE.RUN_NO = B.RUN_NO "
				+ "LEFT JOIN ROUTE_TABLE ON JOURNEY_TABLE.ROUTE_ID = ROUTE_TABLE.ROUTE_ID "
				+ "LEFT JOIN STOP_TABLE AS DEP ON JOURNEY_TABLE.STOP_ID = DEP.STOP_ID "
				+ "LEFT JOIN STOP_TABLE AS ARR ON B.STOP_ID = ARR.STOP_ID "
				+ "WHERE JOURNEY_TABLE.DAY = '" + day + "' AND B.DAY = '" + day + "' AND DEP.STOP_ID = " + dep
				+ " AND ARR.STOP_ID = " + dest + " AND DEP_TIME >= " + t + 
				" ORDER BY ARR_TIME";

		openDatabase();
		Cursor cursor = _db.rawQuery(sql, null);

		if (cursor.moveToFirst()) {
			do {
				JourneyResult j = new JourneyResult();
				j.routeName = cursor.getString(0);
				j.day = cursor.getString(1);
				j.run = cursor.getInt(2);
				j.departTime = cursor.getLong(3);
				j.departStop = cursor.getString(4);
				j.arrivalTime = cursor.getLong(5);
				j.arrivalStop = cursor.getString(6);
				j.walkingTime = time;
				j.destLat = cursor.getDouble(8);
				j.destLong = cursor.getDouble(9 );
				if (j.departTime < j.arrivalTime && j.day.equals(day)) {
					jrList.add(j);
				}
			} while (cursor.moveToNext());
		}

	}
	
	Long getNextBusFrom(int busStop)
	{
		Long nextBus = null;
		Calendar c = Calendar.getInstance();
		c.set(1, 1, 0);
		Long currentTime = c.getTimeInMillis();
		
		String sql = "SELECT " +
					 "TIME, " +
					 "JOURNEY_TABLE.STOP_ID " +
					 "FROM JOURNEY_TABLE " +
					 "INNER JOIN STOP_TABLE ON JOURNEY_TABLE.STOP_ID = STOP_TABLE.STOP_ID " +
					 "WHERE JOURNEY_TABLE.STOP_ID = " + busStop + " AND STOP_TABLE.STOP_ID = " + busStop + 
					 " AND TIME > " + currentTime + 
					 " ORDER BY TIME";
		
		openDatabase();
		Cursor cursor = _db.rawQuery(sql, null);

		if (cursor.moveToFirst()) {
			do {
				nextBus = cursor.getLong(0);
				break;
			} while (cursor.moveToNext());
		}
		
		return nextBus;
	}
	
	List<JourneyResult> getJourneyResults(int departID, int arriveID, String day, long time) {

		List<JourneyResult> jresult = new ArrayList<JourneyResult>();


		String sql = "SELECT "
				+ "ROUTE_TABLE.ROUTE_NAME, "
				+ "JOURNEY_TABLE.DAY, "
				+ "JOURNEY_TABLE.RUN_NO, "
				+ "JOURNEY_TABLE.TIME AS DEP_TIME, "
				+ "DEP.STOP_NAME AS DEP_NAME, "
				+ "B.TIME AS ARR_TIME, "
				+ "ARR.STOP_NAME AS ARR_NAME, "
				+ "JOURNEY_TABLE.DAY, "
				+ "DEP.GPS_LAT, "
				+ "DEP.GPS_LNG "
				+ "FROM JOURNEY_TABLE "
				+ ""
				+ "INNER JOIN JOURNEY_TABLE AS B ON JOURNEY_TABLE.RUN_NO = B.RUN_NO "
				+ "LEFT JOIN ROUTE_TABLE ON JOURNEY_TABLE.ROUTE_ID = ROUTE_TABLE.ROUTE_ID "
				+ "LEFT JOIN STOP_TABLE AS DEP ON JOURNEY_TABLE.STOP_ID = DEP.STOP_ID "
				+ "LEFT JOIN STOP_TABLE AS ARR ON B.STOP_ID = ARR.STOP_ID "
				+ "WHERE JOURNEY_TABLE.DAY = '" + day + "' AND B.DAY = '" + day + "' AND DEP.STOP_ID = " + departID
				+ " AND ARR.STOP_ID = " + arriveID + " AND DEP_TIME >= " + time + 
				" ORDER BY DEP_TIME";

		openDatabase();
		Cursor cursor = _db.rawQuery(sql, null);

		if (cursor.moveToFirst()) {
			do {
				JourneyResult j = new JourneyResult();
				j.routeName = cursor.getString(0);
				j.day = cursor.getString(1);
				j.run = cursor.getInt(2);
				j.departTime = cursor.getLong(3);
				j.departStop = cursor.getString(4);
				j.arrivalTime = cursor.getLong(5);
				j.arrivalStop = cursor.getString(6);
				j.destLat = cursor.getDouble(8);
				j.destLong = cursor.getDouble(9);
				if (j.departTime < j.arrivalTime && j.day.equals(day)) {
					jresult.add(j);
				}
			} while (cursor.moveToNext());
		}
		
		return jresult;
	}

	public String getDayFromInt(int day) {

		String d = "";
		switch (day) {
		case 2:
			d = "Monday";
			break;
		case 3:
			d = "Tuesday";
			break;
		case 4:
			d = "Wednesday";
			break;
		case 5:
			d = "Thursday";
			break;
		case 6:
			d = "Friday";
			break;
		case 7:
			d = "Saturday";
			break;
		case 1:
			d = "Sunday";
			break;

		}

		return d;
	}

	public String calToString(long time) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(time);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String formattedTime = df.format(cal1.getTime());

		return formattedTime;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void addStop(SQLiteDatabase db, Stop stop){
		
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
		values.put("GPS_LAT", stop.getLat());
		values.put("GPS_LNG", stop.getLng());

		db.insert(TABLE_STOP, null, values);
		db.close();
	}
	
}