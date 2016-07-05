package com.champ.core.utility;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public enum TimeUnit {

		DAY(Calendar.DATE), HOUR(Calendar.HOUR_OF_DAY), MINUTES(Calendar.MINUTE), SECONDS(Calendar.SECOND);
		private int code;

		TimeUnit(int code) {
			this.code = code;
		}
	}

	public static boolean isDatePassed(Date date) {
		Date currentDate = new Date();
		return date.before(currentDate);
	}

	public static Date addToDate(Date date, TimeUnit timeUnit, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(timeUnit.code, amount);
		return c.getTime();
	}

	public static void main(String args[]) {
		Date test = new Date();
		System.out.println(addToDate(test, TimeUnit.SECONDS, 3600));
	}
}
