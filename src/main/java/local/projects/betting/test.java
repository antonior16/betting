package local.projects.betting;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class test {

	public static void main(String[] args) {
		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		if ("27/4".equals(day + "/" + month)) {
			System.out.println("ok");
		}
	}
}
