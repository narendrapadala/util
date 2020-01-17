package org.cisco.ciscoutil.cu.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
	
	public static String getTimeDifferenceFromCurrentTimeUTCStr(Long millis, boolean excludeSeconds) {
		millis = millis - System.currentTimeMillis();
		if (millis < 0) {
			return "0 seconds";
		}
		return getHourlyTimeStr(millis, excludeSeconds);
	}
	
	public static Long getMinuteTime(Long millis) {
		return TimeUnit.MILLISECONDS.toMinutes(millis);
	}
	
	public static String getHourlyTimeStr(Long millis, boolean excludeSeconds) {
		StringBuilder timeStr = new StringBuilder();
		long days = TimeUnit.MILLISECONDS.toDays(millis);
		String daysStr = getTimeUnitStr(days, "day");
		long hours = TimeUnit.MILLISECONDS.toHours(millis) % TimeUnit.DAYS.toHours(1);
		String hoursStr = getTimeUnitStr(hours, "hour");
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1);
		String minutesStr = getTimeUnitStr(minutes, "minute");
		long seconds = excludeSeconds ? 0 : TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1);
		String secondsStr = getTimeUnitStr(seconds, "second");
		if (days != 0) {
			timeStr.append(daysStr);
		}
		if (hours != 0) {
			timeStr.append(hoursStr);
		}
		if (minutes != 0) {
			timeStr.append(minutesStr);
		}
		if (seconds != 0) {
			timeStr.append(secondsStr);
		}
		return timeStr.toString();
	}
	
	private static String getTimeUnitStr(long time, String timeStr) {
		return time + " " + (time <=1  ? timeStr : timeStr + "s") + " "; 
	}
	
	public static void main(String[] args) {
		System.out.println(getHourlyTimeStr(6000L, false));
	}
}
