package com.example.menuexample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
	private static final String DATABASE_NAME = "timetable_sqlite";
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

	public void createDatabase() {

		Log.d("test", "create");
		
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

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		createDatabase();
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
				stop.setLocation(cursor.getString(2));
				stopList.add(stop);
			} while (cursor.moveToNext());
		}

		return stopList;
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
				+ "JOURNEY_TABLE.DAY "
				+ "FROM JOURNEY_TABLE "
				+ ""
				+ "INNER JOIN JOURNEY_TABLE AS B ON JOURNEY_TABLE.RUN_NO = B.RUN_NO "
				+ "LEFT JOIN ROUTE_TABLE ON JOURNEY_TABLE.ROUTE_ID = ROUTE_TABLE.ROUTE_ID "
				+ "LEFT JOIN STOP_TABLE AS DEP ON JOURNEY_TABLE.STOP_ID = DEP.STOP_ID "
				+ "LEFT JOIN STOP_TABLE AS ARR ON B.STOP_ID = ARR.STOP_ID "
				+ "WHERE B.DAY = '" + day + "' AND DEP.STOP_ID = " + departID
				+ " AND ARR.STOP_ID = " + arriveID + " AND DEP_TIME > " + time + " ORDER BY DEP_TIME";

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
}