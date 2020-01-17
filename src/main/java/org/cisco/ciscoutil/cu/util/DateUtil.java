package org.cisco.ciscoutil.cu.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

public class DateUtil {
	private static final String FORMATTER = "dd-MM-yyyy h:mm a, z";
	
	public static long toUTCTimeInMillis(long current, String timeZone) {
		long offsetSeconds = getZoneOffsetSeconds(timeZone);
		return current - offsetSeconds;
	}
	
	public static long toTimeZoneTimeInMillis(long utcTime, String timeZone) {
		long offsetSeconds = getZoneOffsetSeconds(timeZone);
		return utcTime + offsetSeconds;
	}
	
	public static Long timeInMillisInTimezone(int hourOfDay, String timezone) {
		return timeInMillisInTimezone(hourOfDay, 0, timezone);
	}
	
	public static Long timeInMillisInTimezone(int hourOfDay, int minOfDay, String timezone) {
		return timeInMillisInTimezone(hourOfDay, minOfDay, 0, timezone);
	}
	
	public static Long timeInMillisInTimezone(int hourOfDay, int minOfDay, int secOfDay, String timezone) {
		return timeInMillisInTimezone(hourOfDay, minOfDay, secOfDay, 0, timezone);
	}
	
	public static Long timeInMillisInTimezone(int hourOfDay, int minOfDay, int secOfDay, int millis, String timezone) {
		Calendar calendar = newCalendarInstance(timezone);
		return getTimeInMillis(hourOfDay, minOfDay, secOfDay, millis, calendar);
	}

	private static Long getTimeInMillis(int hourOfDay, int minOfDay, int secOfDay, int millis, Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
	    calendar.set(Calendar.MINUTE, minOfDay);
	    calendar.set(Calendar.SECOND, secOfDay);
	    calendar.set(Calendar.MILLISECOND, millis);
		return calendar.getTimeInMillis();
	}
	
	public static Calendar newCalendarInstance() {
		return newCalendarInstance(null);
	}
	
	public static Calendar newCalendarInstance(String timezone) {
	    Calendar calendar = new GregorianCalendar();
	    if (StringUtils.isEmpty(timezone)) {
	    	timezone = "GMT";
	    }
	    calendar.setTimeZone(TimeZone.getTimeZone(timezone));
	    return calendar;
	}
	
	public static String getTimeInString(Long timeInMillis, String timezone) {
		return getTimeInString(timeInMillis, timezone, FORMATTER);
	}
	
	public static String getTimeInString(Long timeInMillis, String timezone, String formatter) {
		validate(timeInMillis, timezone);
		Date currentDate = new Date(timeInMillis);
		SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.isEmpty(formatter) ? FORMATTER : formatter);
		sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		return sdf.format(currentDate).toString();
	}
	
	private static long getZoneOffsetSeconds(String timeZone) {
		ZoneOffset offset = getZoneOffsetFromUTC(timeZone);
		long offsetSeconds = offset.getTotalSeconds() * 1000;
		return offsetSeconds;
	}
	
	private static ZoneOffset getZoneOffsetFromUTC(String timeZone) {
		return LocalDateTime.now().atZone(ZoneId.of(timeZone)).getOffset();
	}
	
	private static void validate(Long timeInMillis, String timezone) {
		if (NumberUtil.isNull(timeInMillis)) {
			throw new IllegalArgumentException("Time can't be empty!");
		}
		if (StringUtils.isEmpty(timezone)) {
			throw new IllegalArgumentException("Timezone can't be empty!");
		}
	}
	
	public static void main(String[] args) {
		long utcTime = System.currentTimeMillis();
		System.out.println("UTC Time: " + utcTime);
		System.out.println("timezone millis in Kolkata: " + toTimeZoneTimeInMillis(utcTime, "Asia/Kolkata"));
		System.out.println("timezone millis in London: " + toTimeZoneTimeInMillis(utcTime, "Europe/London"));
		System.out.println("timezone millis in New York: " + toTimeZoneTimeInMillis(utcTime, "America/New_York"));
		System.out.println("timezone millis in Kolkata in 12 morning: " + (toTimeZoneTimeInMillis(utcTime, "Europe/London") - timeInMillisInTimezone(0, 0, "Asia/Kolkata")));
		System.out.println("timezone millis in London in 12 morning: " + (toTimeZoneTimeInMillis(utcTime, "Europe/London") - timeInMillisInTimezone(0, 0, "Europe/London")));
		System.out.println("timezone millis in New York in 12 morning: " + (toTimeZoneTimeInMillis(utcTime, "Europe/London") - timeInMillisInTimezone(0, 0, "America/New_York")));
		System.out.println(DateUtil.getTimeInString(utcTime, "Asia/Kolkata", "dd-MM-yyyy h:mm a, z"));
	}
}
