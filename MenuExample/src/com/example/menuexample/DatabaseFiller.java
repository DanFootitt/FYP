package com.example.menuexample;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.text.GetChars;
import android.util.Log;

public class DatabaseFiller {

	public static void fillDB(DatabaseHandler db) {

		DatabaseFiller.addStops(db);
		DatabaseFiller.addRoutes(db);
		DatabaseFiller.addJourneys(db);

	}

	static void addStops(DatabaseHandler db) {

		db.addStop(new Stop("Burton Street", 52.95619,-1.15056));
		db.addStop(new Stop("Maid Marian Way", 52.95594,-1.16092));
		db.addStop(new Stop("Train Station", 52.94781,-1.1433));
		db.addStop(new Stop("Trent Bridge", 52.92947,-1.13714));
		db.addStop(new Stop("Wilford Green", 52.92557,-1.15889));
		db.addStop(new Stop("Fabis Drive", 52.91637,-1.18041));
		db.addStop(new Stop("NTU Clifton Campus", 52.91193,-1.18792));

	}

	static void addRoutes(DatabaseHandler db) {

		db.addRoute(new Route("Unilink 4", 1, 1));
		db.addRoute(new Route("N4", 1, 1));

	}

	public static int getIntFromDay(String day) {

		int d = 0;
		if (day == "Monday")
			return 1;
		if (day == "Tuesday")
			return 2;
		if (day == "Wednesday")
			return 3;
		if (day == "Thursday")
			return 4;
		if (day == "Friday")
			return 5;
		if (day == "Saturday")
			return 6;
		if (day == "Sunday")
			return 7;

		return d;
	}
	
	static void addJourneys(DatabaseHandler db) {

		List<String> dayList = new ArrayList<String>();

		dayList.add("Monday");
		dayList.add("Tuesday");
		dayList.add("Wednesday");
		dayList.add("Thursday");
		dayList.add("Friday");

		for (String day : dayList) {

			Calendar c = Calendar.getInstance();
			c.set(1, 1, 0);
			c.set(Calendar.HOUR_OF_DAY, 7);
			
			int[] min = new int[6];
			int[] min2 = new int[6];
			min = new int[]{20,24,28,33,38,43,50};
			min2 = new int[]{1,6,11,16,21,27,31};
			int r = 1;
			long hour = 3600000;
			
			for(int i = 0; i < 70; i++)
			{
				c.set(Calendar.MINUTE, min[0]);
				db.addJourney(new Journey(1, day, r, 1, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[1]);
				db.addJourney(new Journey(1, day, r, 2, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[2]);
				db.addJourney(new Journey(1, day, r, 3, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[3]);
				db.addJourney(new Journey(1, day, r, 4, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[4]);
				db.addJourney(new Journey(1, day, r, 5, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[5]);
				db.addJourney(new Journey(1, day, r, 6, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[6]);
				db.addJourney(new Journey(1, day, r, 7, c.getTimeInMillis()));
				
				r++;
				
				c.set(Calendar.MINUTE, min2[0]);
				db.addJourney(new Journey(1, day, r, 7, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[1]);
				db.addJourney(new Journey(1, day, r, 6, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[2]);
				db.addJourney(new Journey(1, day, r, 5, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[3]);
				db.addJourney(new Journey(1, day, r, 4, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[4]);
				db.addJourney(new Journey(1, day, r, 3, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[5]);
				db.addJourney(new Journey(1, day, r, 2, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[6]);
				db.addJourney(new Journey(1, day, r, 1, c.getTimeInMillis()));
				
				r++;
				
				for(int j = 0; j<7; j++){
					min[j] += 10;
					min2[j] += 10;
				}
				
			}
			
			c.set(Calendar.HOUR_OF_DAY, 19);
			min = new int[]{0,4,6,10,15,20,26};
			min2 = new int[]{46,49,54,59,64,70,73};
			
			for(int i = 0; i < 7; i++)
			{
				c.set(Calendar.MINUTE, min[0]);
				db.addJourney(new Journey(1, day, r, 1, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[1]);
				db.addJourney(new Journey(1, day, r, 2, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[2]);
				db.addJourney(new Journey(1, day, r, 3, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[3]);
				db.addJourney(new Journey(1, day, r, 4, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[4]);
				db.addJourney(new Journey(1, day, r, 5, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[5]);
				db.addJourney(new Journey(1, day, r, 6, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[6]);
				db.addJourney(new Journey(1, day, r, 7, c.getTimeInMillis()));
				
				r++;
				
				if (i < 5){
					c.set(Calendar.MINUTE, min2[0]);
					db.addJourney(new Journey(1, day, r, 7, c.getTimeInMillis() - hour));
					c.set(Calendar.MINUTE, min2[1]);
					db.addJourney(new Journey(1, day, r, 6, c.getTimeInMillis() - hour));
					c.set(Calendar.MINUTE, min2[2]);
					db.addJourney(new Journey(1, day, r, 5, c.getTimeInMillis() - hour ));
					c.set(Calendar.MINUTE, min2[3]);
					db.addJourney(new Journey(1, day, r, 4, c.getTimeInMillis() - hour));
					c.set(Calendar.MINUTE, min2[4]);
					db.addJourney(new Journey(1, day, r, 3, c.getTimeInMillis() - hour));
					c.set(Calendar.MINUTE, min2[5]);
					db.addJourney(new Journey(1, day, r, 2, c.getTimeInMillis() - hour));
					c.set(Calendar.MINUTE, min2[6]);
					db.addJourney(new Journey(1, day, r, 1, c.getTimeInMillis() - hour));
				}
				
				r++;
				
				
				for(int j = 0; j<7; j++){
					min2[j] += 15;
					min[j] += 15;
				}
				
			}
			
			c.set(Calendar.HOUR_OF_DAY, 20);
			min = new int[]{45,49,51,55,60,65,71};
			min2 = new int[]{46,49,54,59,64,70,73};
			
			for(int i = 0; i < 4; i++)
			{
				c.set(Calendar.MINUTE, min[0]);
				db.addJourney(new Journey(1, day, r, 1, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[1]);
				db.addJourney(new Journey(1, day, r, 2, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[2]);
				db.addJourney(new Journey(1, day, r, 3, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[3]);
				db.addJourney(new Journey(1, day, r, 4, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[4]);
				db.addJourney(new Journey(1, day, r, 5, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[5]);
				db.addJourney(new Journey(1, day, r, 6, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[6]);
				db.addJourney(new Journey(1, day, r, 7, c.getTimeInMillis()));
				
				r++;
				
					c.set(Calendar.MINUTE, min2[0]);
					db.addJourney(new Journey(1, day, r, 7, c.getTimeInMillis()));
					c.set(Calendar.MINUTE, min2[1]);
					db.addJourney(new Journey(1, day, r, 6, c.getTimeInMillis()));
					c.set(Calendar.MINUTE, min2[2]);
					db.addJourney(new Journey(1, day, r, 5, c.getTimeInMillis()));
					c.set(Calendar.MINUTE, min2[3]);
					db.addJourney(new Journey(1, day, r, 4, c.getTimeInMillis()));
					c.set(Calendar.MINUTE, min2[4]);
					db.addJourney(new Journey(1, day, r, 3, c.getTimeInMillis()));
					c.set(Calendar.MINUTE, min2[5]);
					db.addJourney(new Journey(1, day, r, 2, c.getTimeInMillis()));
					c.set(Calendar.MINUTE, min2[6]);
					db.addJourney(new Journey(1, day, r, 1, c.getTimeInMillis()));
				
				r++;
				
				for(int j = 0; j<7; j++){
					min[j] += 30;
					min2[j] += 30;
				}
				
			}
			
			r++;
			c.set(Calendar.HOUR_OF_DAY, 23);
			min = new int[]{15,19,21,25,30,35,41};
			c.set(Calendar.MINUTE, min[0]);
			db.addJourney(new Journey(1, day, r, 1, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min[1]);
			db.addJourney(new Journey(1, day, r, 2, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min[2]);
			db.addJourney(new Journey(1, day, r, 3, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min[3]);
			db.addJourney(new Journey(1, day, r, 4, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min[4]);
			db.addJourney(new Journey(1, day, r, 5, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min[5]);
			db.addJourney(new Journey(1, day, r, 6, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min[6]);
			db.addJourney(new Journey(1, day, r, 7, c.getTimeInMillis()));
			
			r++;
			c.set(Calendar.HOUR_OF_DAY, 22);
			min2 = new int[]{46,49,54,59,64,70,73};
			c.set(Calendar.MINUTE, min2[0]);
			db.addJourney(new Journey(1, day, r, 7, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min2[1]);
			db.addJourney(new Journey(1, day, r, 6, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min2[2]);
			db.addJourney(new Journey(1, day, r, 5, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min2[3]);
			db.addJourney(new Journey(1, day, r, 4, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min2[4]);
			db.addJourney(new Journey(1, day, r, 3, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min2[5]);
			db.addJourney(new Journey(1, day, r, 2, c.getTimeInMillis()));
			c.set(Calendar.MINUTE, min2[6]);
			db.addJourney(new Journey(1, day, r, 1, c.getTimeInMillis()));
			
			
			c.set(Calendar.HOUR_OF_DAY, 00);
			min = new int[]{16,23,26,29,34,39,45};
			min2 = new int[]{45,54,57,62,66,72,76};
			
			for(int i = 0; i < 4; i++)
			{
				c.set(Calendar.MINUTE, min[0]);
				db.addJourney(new Journey(2, day, r, 1, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[1]);
				db.addJourney(new Journey(2, day, r, 2, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[2]);
				db.addJourney(new Journey(2, day, r, 3, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[3]);
				db.addJourney(new Journey(2, day, r, 4, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[4]);
				db.addJourney(new Journey(2, day, r, 5, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[5]);
				db.addJourney(new Journey(2, day, r, 6, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min[6]);
				db.addJourney(new Journey(2, day, r, 7, c.getTimeInMillis()));
				
				r++;
				
				c.set(Calendar.MINUTE, min2[0]);
				db.addJourney(new Journey(2, day, r, 7, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[1]);
				db.addJourney(new Journey(2, day, r, 6, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[2]);
				db.addJourney(new Journey(2, day, r, 5, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[3]);
				db.addJourney(new Journey(2, day, r, 4, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[4]);
				db.addJourney(new Journey(2, day, r, 3, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[5]);
				db.addJourney(new Journey(2, day, r, 2, c.getTimeInMillis()));
				c.set(Calendar.MINUTE, min2[6]);
				db.addJourney(new Journey(2, day, r, 1, c.getTimeInMillis()));
				
				r++;
				
				for(int j = 0; j<7; j++){
					min[j] += 60;
					min2[j] += 60;
				}
				
			}

		}

	}

}