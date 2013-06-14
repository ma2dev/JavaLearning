package utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateControl {

	public static long getDiffTime(String year1, String month1, String day1, String hour1, String min1, String year2,
			String month2, String day2, String hour2, String min2) {

		Calendar largeCal = new GregorianCalendar();
		Calendar smallCal = new GregorianCalendar();

		largeCal.clear();
		smallCal.clear();

		largeCal.set(Integer.parseInt(year1), Integer.parseInt(month1)-1, Integer.parseInt(day1), Integer.parseInt(hour1),
				Integer.parseInt(min1));

		smallCal.set(Integer.parseInt(year2), Integer.parseInt(month2)-1, Integer.parseInt(day2), Integer.parseInt(hour2),
				Integer.parseInt(min2));

		Date largeDate = largeCal.getTime();
		Date smallDate = smallCal.getTime();

		long diffTime = largeDate.getTime() - smallDate.getTime();

		return diffTime;
	}

}
