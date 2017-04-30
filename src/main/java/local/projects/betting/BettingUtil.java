package local.projects.betting;

import java.util.Calendar;
import java.util.Date;

public class BettingUtil {
	public static int getYear() {
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int year = cal.get(Calendar.YEAR);
		return year;
	}
	
	public static int getMonth() {
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}
	
	public static int getDay() {
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}
}
